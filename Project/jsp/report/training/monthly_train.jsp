<%-- 
    Document   : monthly_train_report
    Created on : Jan 30, 2009, 10:20:33 AM
    Author     : bayu
--%>

<%@ page language = "java" %>

<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCodeGen = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_GENERAL_TRAINING); 
    int appObjCodeDept = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_DEPARTMENTAL_TRAINING); 
    int appObjCode = 0; 
    
    // check training privilege (0 = none, 1 = general, 2 = departmental)
    int trainType = checkTrainingType(appObjCodeGen, appObjCodeDept, userSession);
    
    if(trainType == PRIV_GENERAL) {    
        appObjCode = appObjCodeGen;
    }
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../../main/checktraining.jsp" %>

<!-- Jsp Block -->
<%!
	/*public Vector drawList(Vector objectClass, Department department){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");		
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
		
		ctrlist.addHeader("Department","14%","2","0");
		ctrlist.addHeader("Staff Count","4%","2","0");		
		ctrlist.addHeader("Program","20%","2","0");
		
		ctrlist.addHeader("No. of Programs","12%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
		
		ctrlist.addHeader("Total Hours","12%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
		
		ctrlist.addHeader("No. of Trainees","15%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
				
		ctrlist.addHeader("Trainer","10%","2","0");
		ctrlist.addHeader("Remark","16%","2","0");

		ctrlist.setLinkRow(-1);
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
		double sumPlanHour = 0;
		double sumActHour = 0;
		int sumPlanTrain = 0;
		int sumActTrain = 0;
		int sumTotalEmp = 0;
		double procent = 0;
		String strProcent = "";
                
		if(objectClass != null && objectClass.size()>0){
			for (int i = 0; i < objectClass.size(); i++) {
				TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)objectClass.get(i);
				Vector actuals = PstTrainingActivityPlan.getActual(trainingActivityPlan.getOID());
				
                                int actPrg = 0;
				double actHour = 0;
				int actTrainees = 0;
                                
				if(actuals != null && actuals.size()>0){
					actPrg = Integer.parseInt(""+actuals.get(0));
					actTrainees = Integer.parseInt(""+actuals.get(1));
					actHour = Double.parseDouble(""+actuals.get(2));
				}
                                
				rowx = new Vector();
                                
				if(i==0){
					rowx.add(department.getDepartment()); //0
				}else{
					rowx.add("");//0
				}

				rowx.add(""); //1
				
				Training trn = new Training();
				try{
					trn = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
				}
				catch(Exception e){
					trn = new Training();
				}
				rowx.add(trn.getName()); //2
				
				rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan())); //3
				rowx.add(String.valueOf(actPrg)); //4
                                
				sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
				sumActPrg = sumActPrg + actPrg;
				procent = ( (new Integer(actPrg)).doubleValue()/(new Integer(trainingActivityPlan.getProgramsPlan())).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent+"%"); //5

				rowx.add(String.valueOf(trainingActivityPlan.getTotHoursPlan())); //6
				rowx.add(String.valueOf(actHour)); //7
                                
				sumPlanHour = sumPlanHour + trainingActivityPlan.getTotHoursPlan();
				sumActHour = sumActHour + actHour;
				procent = ((new Double(actHour)).doubleValue()/(new Double(trainingActivityPlan.getTotHoursPlan())).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent+"%");   //8

				rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //9
				rowx.add(String.valueOf(actTrainees));  //10
                                
				sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
				sumActTrain = sumActTrain + actTrainees;
				procent = ((new Integer(actTrainees)).doubleValue()/(new Integer(trainingActivityPlan.getTraineesPlan())).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent+"%");   //11

				rowx.add(trainingActivityPlan.getTrainer());    //12
				rowx.add(trainingActivityPlan.getRemark());     //13

				lstData.add(rowx);
				lstLinkData.add(String.valueOf(trainingActivityPlan.getOID()));
			}
		}
                else{
			rowx = new Vector();
                        
			rowx.add("&nbsp;"+department.getDepartment());
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
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
		
		rowx.add("&nbsp;<b>Total</b>");
		whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+department.getOID()+
			" AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;
		int staffCount = PstEmployee.getCount(whereClause);
		
		sumTotalEmp = sumTotalEmp + staffCount;
		
		rowx.add(staffCount==0?"":""+staffCount);
		rowx.add("");
		
		rowx.add(String.valueOf(sumPlanPrg));
		rowx.add(String.valueOf(sumActPrg));
		procent = ((new Integer(sumActPrg)).doubleValue()/(new Integer(sumPlanPrg)).doubleValue())*100;
		if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent+"%");
		
		rowx.add(String.valueOf(sumPlanHour));
		rowx.add(String.valueOf(sumActHour));
		procent = ((new Double(sumActHour)).doubleValue()/(new Double(sumPlanHour)).doubleValue())*100;
		if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent+"%");
		
		rowx.add(String.valueOf(sumPlanTrain));
		rowx.add(String.valueOf(sumActTrain));
		procent = ((new Integer(sumActTrain)).doubleValue()/(new Integer(sumPlanTrain)).doubleValue())*100;
		if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent+"%");
		
		rowx.add("");
		rowx.add("");
		
		lstData.add(rowx);
		
			
		Vector vctTmp = new Vector(1,1);
		Vector count = new Vector(1,1);
		count.add(""+sumPlanPrg);
		count.add(""+sumActPrg);
		count.add(""+sumPlanHour);
		count.add(""+sumActHour);
		count.add(""+sumPlanTrain);
		count.add(""+sumActTrain);
		count.add(""+sumTotalEmp);
				
		vctTmp.add(ctrlist.drawList());
		vctTmp.add(count);
		
		return vctTmp;
        } */         

        public Vector drawList(Vector objectClass, Vector objectReport, Department department){
		Vector deptList = new Vector();      // handle all dept's training data
                Vector list = new Vector();          // list of this department's training programs
                deptList.add(department);       // elm 0
            
                ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");		
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
		
		ctrlist.addHeader("Department","14%","2","0");
		ctrlist.addHeader("Staff Count","4%","2","0");		
		ctrlist.addHeader("Program","20%","2","0");
		ctrlist.addHeader("Schedule","16%","2","0");
                
		ctrlist.addHeader("No. of Programs","12%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
		
		ctrlist.addHeader("Total Hours","12%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
		
		ctrlist.addHeader("No. of Trainees","15%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
				
		ctrlist.addHeader("Trainer","10%","2","0");		
                
              
		ctrlist.setLinkRow(-1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();	
		ctrlist.reset();
		String whereClause = "";
		Vector rowx = new Vector();

		int sumPlanPrg = 0;
		int sumActPrg = 0;
		int sumPlanHour = 0;
		int sumActHour = 0;
		int sumPlanTrain = 0;
		int sumActTrain = 0;
		int sumTotalEmp = 0;
		double procent = 0;
		String strProcent = "";
                
		if(objectClass != null && objectClass.size()>0){
                        long prevTrainId = 0;
                    
			for (int i = 0; i < objectClass.size(); i++) {
                                Vector vctTemp = (Vector)objectClass.get(i);
                                
                                TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)vctTemp.get(0);
                                TrainingSchedule schedule = (TrainingSchedule)vctTemp.get(1);
                                TrainingActivityActual trainingActivityActual = (TrainingActivityActual)vctTemp.get(2);
                                                                
				
                                
                                
                                int actPrg = 0;
				int actHour = 0;
				int actTrainees = 0;
                                
                                if(trainingActivityActual.getOID() != 0) {
                                    actPrg = trainingActivityPlan.getProgramsPlan();
                                    actHour = SessTraining.getTrainingDuration(trainingActivityActual.getStartTime(), trainingActivityActual.getEndTime());
                                    actTrainees = trainingActivityActual.getAtendees();
                                }
                                
				rowx = new Vector();
                                
				if(i==0){
					rowx.add(department.getDepartment()); //0
				}else{
					rowx.add("");//0
				}

				rowx.add(""); //1
				
                                if(prevTrainId != trainingActivityPlan.getTrainingId()) {
                                    Training trn = new Training();
                                    try{
                                            trn = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
                                    }
                                    catch(Exception e){
                                            trn = new Training();
                                    }
                                    rowx.add(trn.getName()); //2
                                }
                                else {
                                    rowx.add(""); //2
                                }                                
                                
                                rowx.add(Formater.formatDate(schedule.getTrainDate(), "MMM dd, yyyy"));    //3                                
				
				rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan())); //4
				rowx.add(String.valueOf(actPrg)); //5
                                
				sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
				sumActPrg = sumActPrg + actPrg;
				procent = ( (new Integer(actPrg)).doubleValue()/(new Integer(trainingActivityPlan.getProgramsPlan())).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent); //6
                                
                                int trainDuration = SessTraining.getTrainingDuration(schedule.getStartTime(), schedule.getEndTime());

				rowx.add(SessTraining.getDurationString(trainDuration)); //7
				rowx.add(SessTraining.getDurationString(actHour)); //8
                                
				sumPlanHour = sumPlanHour + trainDuration;
				sumActHour = sumActHour + actHour;
				procent = ((new Double(actHour)).doubleValue()/(new Double(trainDuration)).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent);   //9

				rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //10
				rowx.add(String.valueOf(actTrainees));  //11
                                
				sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
				sumActTrain = sumActTrain + actTrainees;
				procent = ((new Integer(actTrainees)).doubleValue()/(new Integer(trainingActivityPlan.getTraineesPlan())).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent);   //12

                                if(prevTrainId != trainingActivityPlan.getTrainingId()) 
                                    rowx.add(trainingActivityPlan.getTrainer());    //13
                                else
                                    rowx.add("");
				

				lstData.add(rowx);	
                                
                                list.add(rowx);	
			}
                        
                        deptList.add(list); // elm 1
		}
                else{   // objectClass == null && objectClass.size() < 0
			rowx = new Vector();
                        
			rowx.add("&nbsp;"+department.getDepartment());
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
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
                        
                        list.add(rowx);
                        deptList.add(list); // elm 1
		}
		rowx = new Vector();
		
		rowx.add("&nbsp;<b>Total</b>");
                
		whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+department.getOID()+
                              " AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;
		int staffCount = PstEmployee.getCount(whereClause);
		
		sumTotalEmp = sumTotalEmp + staffCount;
		
		rowx.add(staffCount==0?"":""+staffCount);
		rowx.add("");
                rowx.add("");
		
		rowx.add(String.valueOf(sumPlanPrg));
		rowx.add(String.valueOf(sumActPrg));
		procent = ((new Integer(sumActPrg)).doubleValue()/(new Integer(sumPlanPrg)).doubleValue())*100;
		
                if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent);
		
		rowx.add(SessTraining.getDurationString(sumPlanHour));
		rowx.add(SessTraining.getDurationString(sumActHour));
		procent = ((new Double(sumActHour)).doubleValue()/(new Double(sumPlanHour)).doubleValue())*100;
		
                if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent);
		
		rowx.add(String.valueOf(sumPlanTrain));
		rowx.add(String.valueOf(sumActTrain));
		procent = ((new Integer(sumActTrain)).doubleValue()/(new Integer(sumPlanTrain)).doubleValue())*100;
		
                if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent);		
		
		rowx.add("");
		
		lstData.add(rowx);
		
                
                deptList.add(rowx); // elm 3
                objectReport.add(deptList);
                
			
		Vector vctTmp = new Vector(1,1);
		Vector count = new Vector(1,1);
		count.add(""+sumPlanPrg);
		count.add(""+sumActPrg);
		count.add(""+sumPlanHour);
		count.add(""+sumActHour);
		count.add(""+sumPlanTrain);
		count.add(""+sumActTrain);
		count.add(""+sumTotalEmp);
				
		vctTmp.add(ctrlist.drawList());
		vctTmp.add(count);
		
		return vctTmp;
	}	
        
        public Vector drawListGen(Vector objectClass, Vector objectReport){
		Vector deptList = new Vector();      // handle all dept's training data
                Vector list = new Vector();          // list of this department's training programs
                deptList.add(new Department()) ;
                
                ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");		
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
				
		ctrlist.addHeader("Program","20%","2","0");
		ctrlist.addHeader("Schedule","16%","2","0");
                
		ctrlist.addHeader("No. of Programs","12%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
		
		ctrlist.addHeader("Total Hours","12%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
		
		ctrlist.addHeader("No. of Trainees","15%","0","3");
		ctrlist.addHeader("Plan","4%","0","0");
		ctrlist.addHeader("Actual","4%","0","0");
		ctrlist.addHeader("%","4%","0","0");
				
		ctrlist.addHeader("Trainer","10%","2","0");		
                
              
		ctrlist.setLinkRow(-1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();	
		ctrlist.reset();
		String whereClause = "";
		Vector rowx = new Vector();

		int sumPlanPrg = 0;
		int sumActPrg = 0;
		int sumPlanHour = 0;
		int sumActHour = 0;
		int sumPlanTrain = 0;
		int sumActTrain = 0;
		int sumTotalEmp = 0;
		double procent = 0;
		String strProcent = "";
                
		if(objectClass != null && objectClass.size()>0){
                        long prevTrainId = 0;
                    
			for (int i = 0; i < objectClass.size(); i++) {
                                Vector vctTemp = (Vector)objectClass.get(i);
                                
                                TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)vctTemp.get(0);
                                TrainingSchedule schedule = (TrainingSchedule)vctTemp.get(1);
                                TrainingActivityActual trainingActivityActual = (TrainingActivityActual)vctTemp.get(2);
                                 
                                int actPrg = 0;
				int actHour = 0;
				int actTrainees = 0;
                                
                                if(trainingActivityActual.getOID() != 0) {
                                    actPrg = trainingActivityPlan.getProgramsPlan();
                                    actHour = SessTraining.getTrainingDuration(trainingActivityActual.getStartTime(), trainingActivityActual.getEndTime());
                                    actTrainees = trainingActivityActual.getAtendees();
                                }
                                
				rowx = new Vector();
                                
				
                                if(prevTrainId != trainingActivityPlan.getTrainingId()) {
                                    Training trn = new Training();
                                    try{
                                            trn = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
                                    }
                                    catch(Exception e){
                                            trn = new Training();
                                    }
                                    rowx.add(trn.getName()); //2
                                }
                                else {
                                    rowx.add(""); //2
                                }                                
                                
                                rowx.add(Formater.formatDate(schedule.getTrainDate(), "MMM dd, yyyy"));    //3                                
				
				rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan())); //4
				rowx.add(String.valueOf(actPrg)); //5
                                
				sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
				sumActPrg = sumActPrg + actPrg;
				procent = ( (new Integer(actPrg)).doubleValue()/(new Integer(trainingActivityPlan.getProgramsPlan())).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent); //6
                                
                                int trainDuration = SessTraining.getTrainingDuration(schedule.getStartTime(), schedule.getEndTime());

				rowx.add(SessTraining.getDurationString(trainDuration)); //7
				rowx.add(SessTraining.getDurationString(actHour)); //8
                                
				sumPlanHour = sumPlanHour + trainDuration;
				sumActHour = sumActHour + actHour;
				procent = ((new Double(actHour)).doubleValue()/(new Double(trainDuration)).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent);   //9

				rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //10
				rowx.add(String.valueOf(actTrainees));  //11
                                
				sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
				sumActTrain = sumActTrain + actTrainees;
				procent = ((new Integer(actTrainees)).doubleValue()/(new Integer(trainingActivityPlan.getTraineesPlan())).doubleValue())*100;
				
                                if((new Double(procent)).isNaN()){
					procent = 0;
				}

				if(procent%1 > 0)
					strProcent = Formater.formatNumber(procent,"##,###.00");
				else
					strProcent = ""+((new Double(procent)).intValue());

				rowx.add(strProcent);   //12

                                if(prevTrainId != trainingActivityPlan.getTrainingId()) 
                                    rowx.add(trainingActivityPlan.getTrainer());    //13
                                else
                                    rowx.add("");
				

				lstData.add(rowx);	
                                
                                list.add(rowx);	
			}
                        
                        deptList.add(list); // elm 1
		}
                else{   // objectClass == null && objectClass.size() < 0
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
			rowx.add("");
			rowx.add("");
			rowx.add("");
                        
			lstData.add(rowx);	
                        
                        list.add(rowx);
                        deptList.add(list); // elm 1
		}
		rowx = new Vector();
		
		rowx.add("&nbsp;<b>Total</b>");
                
		
                rowx.add("");
		
		rowx.add(String.valueOf(sumPlanPrg));
		rowx.add(String.valueOf(sumActPrg));
		procent = ((new Integer(sumActPrg)).doubleValue()/(new Integer(sumPlanPrg)).doubleValue())*100;
		
                if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent);
		
		rowx.add(SessTraining.getDurationString(sumPlanHour));
		rowx.add(SessTraining.getDurationString(sumActHour));
		procent = ((new Double(sumActHour)).doubleValue()/(new Double(sumPlanHour)).doubleValue())*100;
		
                if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent);
		
		rowx.add(String.valueOf(sumPlanTrain));
		rowx.add(String.valueOf(sumActTrain));
		procent = ((new Integer(sumActTrain)).doubleValue()/(new Integer(sumPlanTrain)).doubleValue())*100;
		
                if((new Double(procent)).isNaN()){
			procent = 0;
		}
		
		if(procent%1 > 0)
			strProcent = Formater.formatNumber(procent,"##,###.00");
		else
			strProcent = ""+((new Double(procent)).intValue());
			
		rowx.add(strProcent);		
		
		rowx.add("");
		
		lstData.add(rowx);
		
                
                deptList.add(rowx); // elm 3
                objectReport.add(deptList);
                
			
		Vector vctTmp = new Vector(1,1);
		Vector count = new Vector(1,1);
		count.add(""+sumPlanPrg);
		count.add(""+sumActPrg);
		count.add(""+sumPlanHour);
		count.add(""+sumActHour);
		count.add(""+sumPlanTrain);
		count.add(""+sumActTrain);
		count.add(""+sumTotalEmp);
				
		vctTmp.add(ctrlist.drawList());
		vctTmp.add(count);
		
		return vctTmp;
	}

%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    Date date = FRMQueryString.requestDate(request,"date");
    int start = FRMQueryString.requestInt(request,"start");    
    int typ = FRMQueryString.requestInt(request, "type");
    CtrlTrainingActivityPlan ctrlTrainingActivityPlan = new CtrlTrainingActivityPlan(request);

    int recordToGet = 20;
    //int limit = 5;
    int vectSize = PstDepartment.getCount("") + 1;
    String whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = 0"+
         " AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(date,"yyyy-MM-dd")+"'";

    Vector listDepartment = new Vector(1,1); 
    Vector vctReport = new Vector(1,1);
    
    // !
    Vector listPlanning = PstTrainingActivityPlan.list(0,0,whereClause,"");

   
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
        start = ctrlTrainingActivityPlan.actionList(iCommand, start, vectSize, recordToGet);      
        listDepartment = PstDepartment.list((start-1)<0?0:(start-1),(start-1)<0?recordToGet-1:recordToGet,"",PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
    }

    if(iCommand == Command.BACK)
            date = new Date();
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Monthly Training Report</title>
<script language="JavaScript">


	function cmdListFirst(){
		document.fract.command.value="<%=Command.FIRST%>";
		document.fract.action="monthly_train.jsp";
		document.fract.submit();
	}
         
        function cmdListFirst(typ){
		document.fract.command.value="<%=Command.FIRST%>";
                document.fract.type.value=typ;
		document.fract.action="monthly_train.jsp";
		document.fract.submit();
	}

	function cmdListPrev(){
		document.fract.command.value="<%=Command.PREV%>";
		document.fract.action="monthly_train.jsp";
		document.fract.submit();
	}

	function cmdListNext(){
		document.fract.command.value="<%=Command.NEXT%>";
		document.fract.action="monthly_train.jsp";
		document.fract.submit();
	}

	function cmdListLast(){
		document.fract.command.value="<%=Command.LAST%>";
		document.fract.action="monthly_train.jsp";
		document.fract.submit();
	}
	
	function cmdPrint(){
		var dtYear  = document.fract.date_yr.value;								
		var dtMonth = document.fract.date_mn.value;
		var dtDay   = document.fract.date_dy.value;
		
		var linkPage   = "training_act_plan_buffer.jsp?" +
                                 "date_yr="+ dtYear +"&"+
                                 "date_mn="+ dtMonth +"&"+
                                 "date_dy="+dtDay;
						 
		window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
	}
       
        function cmdPrintHR() {
                var dtYear  = document.fract.date_yr.value;								
		var dtMonth = document.fract.date_mn.value;
            
                var linkPage = "<%=printroot%>.report.employee.TrainingMonthlyPdf?" +
                               "year="+ dtYear +"&"+
                               "month="+ dtMonth;
            
                window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
        }
        
        function cmdPrintHRGen() {
                var dtYear  = document.fract.date_yr.value;								
		var dtMonth = document.fract.date_mn.value;
            
                var linkPage = "<%=printroot%>.report.employee.TrainingMonthlyGenPdf?" +
                               "year="+ dtYear +"&"+
                               "month="+ dtMonth;
            
                window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
        }
	
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
<!--

    function MM_swapImgRestore() { //v3.0
      var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
    }

    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }

    function MM_findObj(n, d) { //v4.0
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
      if(!x && document.getElementById) x=document.getElementById(n); return x;
    }

    function MM_swapImage() { //v3.0
      var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
       if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
    }
//-->
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg','<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table>
    </td>
  </tr>
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Training 
                  &gt; Monthly Training Activities Report<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="fract" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>"> 
                                      <input type="hidden" name="start" value="<%=start%>">     
                                      <input type="hidden" name="hidden_training_activity_plan_id">
                                      <input type="hidden" name="type" value="<%=typ%>">  
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td> 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                              <tr>
                                                <td colspan="2" align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2" align="right"> 
                                                  <div align="center"><b><font size="3">MONTHLY 
                                                    TRAINING REPORT</font></b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" align="right">Month 
                                                  : </td>
                                                <td width="91%"><%=ControlDate.drawDateMY("date",iCommand == Command.NONE?new Date():date,"MMMM","formElemen",1,-2)%></td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" align="right">&nbsp;</td>
                                                <td width="91%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" align="right">&nbsp;</td>
                                                <td width="91%"> 
                                                  <table width="29%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td width="6%"><a href="javascript:cmdListFirst(<%=PRIV_DEPT%>)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                      <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                      <td width="70%" class="command" nowrap><a href="javascript:cmdListFirst(<%=PRIV_DEPT%>)"> 
                                                        Monthly Training Report (Departmental)</a></td>                                                    
                                                    </tr>
                                                    <% if(trainType == PRIV_GENERAL) { %>
                                                        <tr>
                                                          <td>&nbsp;</td>
                                                        </tr>
                                                        <tr> 
                                                          <td width="6%"><a href="javascript:cmdListFirst(<%=PRIV_GENERAL%>)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                          <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                          <td width="70%" class="command" nowrap><a href="javascript:cmdListFirst(<%=PRIV_GENERAL%>)">
                                                            Monthly Training Report (General)</a></td>                                                    
                                                        </tr>
                                                    <% } %>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <% if(listDepartment != null && listDepartment.size()>0){%>
                                                <tr> 
                                          <td width="100%" class="command"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                              <tr> 
                                                <td align="center" class="command"> 
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td class="command"> 
                                                  <div align="center">Month : 
                                                    <%=Formater.formatDate(date,"MMMM yyyy")%></div>
                                                </td>
                                              </tr>                                              
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <% if(typ == PRIV_DEPT) { 
                                                                                 
                                                      int sumPlanPrgTotal = 0;
                                                      int sumActPrgTotal = 0;
                                                      int sumPlanHourTotal = 0;
                                                      int sumActHourTotal = 0;
                                                      int sumPlanTrainTotal = 0;
                                                      int sumActTrainTotal = 0;
                                                      int sumTotalEmpAll = 0;
                                                      
                                                      for(int d=0;d<listDepartment.size();d++){ 
                                                            Department department = (Department)listDepartment.get(d);                                                            
                                                                                                                 
                                                            listPlanning = SessTraining.summaryTraining(date, department.getOID());
                                                      %>
                                                      <tr> 
                                                      <td>
                                                          <%
                                                                Vector vctTemp = drawList(listPlanning, vctReport, department);     									
                                                                Vector vc = (Vector)vctTemp.get(1);    // summary row

                                                                sumPlanPrgTotal =  sumPlanPrgTotal +  Integer.parseInt((String)vc.get(0));
                                                                sumActPrgTotal =  sumActPrgTotal +  Integer.parseInt((String)vc.get(1));
                                                                sumPlanHourTotal =  sumPlanHourTotal +  Integer.parseInt((String)vc.get(2));
                                                                sumActHourTotal =  sumActHourTotal +  Integer.parseInt((String)vc.get(3));
                                                                sumPlanTrainTotal =  sumPlanTrainTotal +  Integer.parseInt((String)vc.get(4));
                                                                sumActTrainTotal =  sumActTrainTotal +  Integer.parseInt((String)vc.get(5));
                                                                sumTotalEmpAll =  sumTotalEmpAll +  Integer.parseInt((String)vc.get(6));

                                                          %>
                                                          <% out.println(vctTemp.get(0)); // table of data %>   
                                                       </td>
                                                     </tr>
                                                      <tr> 
                                                    <td height="8" width="100%">&nbsp;</td>
                                                  </tr>
                                                      <% } // end for %>
                                              
                                              <%-- PRINT SUMMARY REPORT --%>
                                             <tr> 
                                                <td> 
                                                <table width="98%" class="listarea">
                                                <tr> 
                                                    <td>													  
                                                      <% 
                                                      
                                                          Vector vctX = new Vector(1,1);
                                                          vctX.add("GRAND TOTAL");
                                                          vctX.add(""+sumTotalEmpAll);
                                                          vctX.add("");
                                                          vctX.add("");
                                                          vctX.add(""+sumPlanPrgTotal);
                                                          vctX.add(""+sumActPrgTotal);													  
                                                          vctX.add(""+(Formater.formatNumber(((sumActPrgTotal*100.0)/sumPlanPrgTotal), "##.##")));
                                                          vctX.add(""+SessTraining.getDurationString(sumPlanHourTotal));
                                                          vctX.add(""+SessTraining.getDurationString(sumActHourTotal));
                                                          vctX.add(""+(Formater.formatNumber(((sumActHourTotal*100.0)/sumPlanHourTotal), "##.##")));
                                                          vctX.add(""+sumPlanTrainTotal);
                                                          vctX.add(""+sumActTrainTotal);
                                                          vctX.add(""+(Formater.formatNumber(((sumActTrainTotal*100.0)/sumPlanTrainTotal), "##.##")));
                                                          vctX.add("");
                                                          

                                                          session.putValue("SESS_TOTAL_ALL_TRAINING", vctX);
                                                          
                                                          // for report
                                                          Vector deptList = new Vector();      // handle all dept's training data
                                                          Vector list = new Vector();          // list of this department's training programs
                                                          
                                                          deptList.add(new Department());       // elm 0
                                                          deptList.add(vctX);                   // elm 1
                                                          
                                                          vctReport.add(deptList);      // report data
                                                          
                                                          session.putValue("SESS_REPORT_MONTHLY_TRAINING", vctReport);

                                                      %>
													   
                                                      <table width="100%" class="listgen" cellspacing="1">
                                                      <tr bgcolor="#003399"> 
                                                        <td width="14%"  colspan="0" height="26" > 
                                                          <div align="center"><b><font color="#FFFF00"></font></b></div>
                                                        </td>
                                                        <td width="4%"  colspan="0" height="26" > 
                                                          <div align="center"><b><font color="#FFFF00"></font></b></div>
                                                        </td>
                                                        <td width="20%"  colspan="0" height="26" > 
                                                          <div align="center"><b><font color="#FFFF00"></font></b></div>
                                                        </td>
                                                        <td width="16%"  colspan="0" height="26" > 
                                                          <div align="center"><b><font color="#FFFF00"></font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">Plan</font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">Actual</font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">%</font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">Plan</font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">Actual</font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">%</font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">Plan</font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">Actual</font></b></div>
                                                        </td>
                                                        <td width="4%"  height="26" > 
                                                          <div align="center"><b><font color="#FFFF00">%</font></b></div>
                                                        </td>
                                                        <td width="10%"  colspan="0" height="26" > 
                                                          <div align="center"><b><font color="#FFFF00"></font></b></div>
                                                        </td>                                                        
                                                      </tr>
                                                      
                                                      <tr bgcolor="#003399"> 
                                                        <td height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b>GRAND TOTAL</b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><%=vctX.get(1)%></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"></font></div>
                                                        </td>
                                                        <td  height="29" ><font color="#FFFF00"></font></td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(4)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(5)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(6)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(7)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(8)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(9)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(10)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(11)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" > 
                                                          <div align="center"><font color="#FFFF00"><b><font size="2"><%=vctX.get(12)%></font></b></font></div>
                                                        </td>
                                                        <td  height="29" ><font color="#FFFF00"></font></td>                                                        
                                                    </tr>
                                                    </table>
                                                    </td>
                                                </tr>
                                                </table>
                                                </td>
                                              </tr>
                                              <%-- END SUMMARY REPORT --%>
                                              
                                        
                                              <% } 
                                                 else if(typ == PRIV_GENERAL)
                                                 { 
                                                      int sumPlanPrgTotal = 0;
                                                      int sumActPrgTotal = 0;
                                                      int sumPlanHourTotal = 0;
                                                      int sumActHourTotal = 0;
                                                      int sumPlanTrainTotal = 0;
                                                      int sumActTrainTotal = 0;
                                                      int sumTotalEmpAll = 0;
                                                      
                                                     
                                                      listPlanning = SessTraining.summaryTraining(date, 0);
                                                      vectSize = (listPlanning == null) ? 0 : listPlanning.size();
                                              %>
                                              
                                                     <tr> 
                                                      <td>
                                                          <%
                                                                Vector vctTemp = drawListGen(listPlanning, vctReport);     									
                                                                Vector vc = (Vector)vctTemp.get(1);    // summary row

                                                                sumPlanPrgTotal =  sumPlanPrgTotal +  Integer.parseInt((String)vc.get(0));
                                                                sumActPrgTotal =  sumActPrgTotal +  Integer.parseInt((String)vc.get(1));
                                                                sumPlanHourTotal =  sumPlanHourTotal +  Integer.parseInt((String)vc.get(2));
                                                                sumActHourTotal =  sumActHourTotal +  Integer.parseInt((String)vc.get(3));
                                                                sumPlanTrainTotal =  sumPlanTrainTotal +  Integer.parseInt((String)vc.get(4));
                                                                sumActTrainTotal =  sumActTrainTotal +  Integer.parseInt((String)vc.get(5));
                                                                sumTotalEmpAll =  sumTotalEmpAll +  Integer.parseInt((String)vc.get(6));

                                                          %>
                                                          <% out.println(vctTemp.get(0)); // table of data %>   
                                                          <%
                                                            // for report
                                                          session.putValue("SESS_REPORT_MONTHLY_TRAINING", vctReport);
                                                          %>
                                                       </td>
                                                     </tr>
                                                     <tr> 
                                                        <td height="8" width="100%">&nbsp;</td>
                                                      </tr>
                                                    
                                              <% } %>
                                              
                                               <tr> 
                                                <td> 
                                                  <table width="100%" cellspacing="0" cellpadding="3">
                                                    <tr> 
                                                      <td> 
                                                        <% 
                                                           ControlLine ctrLine = new ControlLine();
                                                           ctrLine.setLocationImg(approot+"/images");
                                                           ctrLine.initDefault();
                                                        %>
                                                        <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%} else {  // listDept == null || listDept.size() < 0
                                            if(iCommand == Command.LIST){%>
                                                <tr> 
                                              <td height="8" width="100%">&nbsp;</td>
                                            </tr>
                                                <tr> 
                                              <td height="8" width="100%" class="comment">No training activity available</td>
                                            </tr>
                                            <% } %>
                                        <% } %>
                                        
                                        <tr> 
                                          <td height="8" width="100%">&nbsp;</td>
                                        </tr>
                                        
                                        <% if(listDepartment != null && listDepartment.size()>0){ %>
                                        <tr>
                                            <td height="8" width="100%" class="comment">
                                                <table border="0" cellspacing="0" cellpadding="0" align="left">
                                                    <% if(typ == PRIV_DEPT) { %>
                                                    <tr>                                                                                                 
                                                        <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                        <td width="24"><a href="javascript:cmdPrintHR()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                        <td nowrap><a href="javascript:cmdPrintHR()" class="command">Print                                                         Out Report</a></td>                                                
                                                    </tr>
                                                    <%} else if(typ == PRIV_GENERAL) {%>
                                                     <tr>                                                                                                 
                                                        <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                        <td width="24"><a href="javascript:cmdPrintHRGen()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                        <td nowrap><a href="javascript:cmdPrintHRGen()" class="command">Print                                                         Out Report</a></td>                                                
                                                    </tr>
                                                    <% } %>
                                                </table>
                                            </td>
                                        </tr>
                                        <% } %>
                                        
                                      </table>                                     
                                      </form>
                                    <!-- #EndEditable --> </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp; </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>