
<%-- 
    Document   : input_plan
    Created on : Jan 22, 2009, 1:39:43 PM
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
	
        public String drawList(Vector objectClass, Department department){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");		
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
	
		ctrlist.addHeader("Department","14%","0","0");
		ctrlist.addHeader("Staff Count","4%","0","0");		
		ctrlist.addHeader("Program","20%","0","0");	
		ctrlist.addHeader("Programs","5%","0","0");		
		ctrlist.addHeader("Total Hours","10%","0","0");		
		ctrlist.addHeader("Trainees","5%","0","0");		
		ctrlist.addHeader("Trainer","10%","0","0");
		ctrlist.addHeader("Schedule","25%","0","0");

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
		int sumPlanHour = 0;
		int sumPlanTrain = 0;	
		
		if(objectClass != null && objectClass.size()>0){
			for (int i = 0; i < objectClass.size(); i++) {
                            
                            // get vector object
                            Vector tmp = (Vector)objectClass.get(i);                            
                            
                            // get training plan and list of schedules
                            TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)tmp.get(0);
                            Vector schedules = (Vector)tmp.get(1);
                            
                            rowx = new Vector();
                            
                            // display dept on first row only
                            if(i==0) 
                                rowx.add(department.getDepartment()); //0
                            else 
                                rowx.add("");                            

                            rowx.add(""); //1
				
                            Training trn = new Training();
                            
                            try{
                                    trn = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
                            }
                            catch(Exception e){
                                    trn = new Training();
                            }
                            rowx.add(trn.getName()); //2

                            rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan()));
                            sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
                            
                            rowx.add(SessTraining.getDurationString(trainingActivityPlan.getTotHoursPlan()));
                            sumPlanHour = sumPlanHour + trainingActivityPlan.getTotHoursPlan();
                            
                            rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));
                            sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
                            
                            rowx.add(trainingActivityPlan.getTrainer());
                            
                            // ----------------------------------
                            //rowx.add(trainingActivityPlan.getRemark());
                            String text = "";
                            String remark = "";
                            
                            if(schedules != null && schedules.size() > 0) {
                                for(int j=0; j<schedules.size(); j++) {
                                    TrainingSchedule schedule = (TrainingSchedule)schedules.get(j);
                                    
                                    remark += "<a href=\"javascript:cmdEdit('"+trainingActivityPlan.getOID()+"','"+schedule.getOID()+"','"+trn.getOID()+"','"+department.getOID()+"')\">";
                                    remark += Formater.formatDate(schedule.getTrainDate(), "d-MMM-yy");
                                    remark += " / " + Formater.formatDate(schedule.getStartTime(), "HH:mm");
                                    remark += " - " + Formater.formatDate(schedule.getEndTime(), "HH:mm");
                                    remark += "</a>";
                                    
                                    if(j < schedules.size() - 1)
                                        remark += "<br>";
                                    
                                }
                            }
                            
                            rowx.add(remark);
                            // ----------------------------------
                            
                            lstData.add(rowx);                           
                          
			}
                        
                /* if no training program, display dept only */
		} else {
			rowx = new Vector();
                        
			rowx.add("&nbsp;"+department.getDepartment());
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");	
                        
			lstData.add(rowx);	
		}
                
                // display summary row
		rowx = new Vector();
		
		rowx.add("&nbsp;<b>Total</b>");
                
		whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+department.getOID();
		int staffCount = PstEmployee.getCount(whereClause);
		rowx.add(staffCount==0?"":""+staffCount);
		
                rowx.add("");		
		rowx.add(String.valueOf(sumPlanPrg));		
		rowx.add(SessTraining.getDurationString(sumPlanHour));				
		rowx.add(String.valueOf(sumPlanTrain));	
		rowx.add("");
		rowx.add("");
		
		lstData.add(rowx);
		
		return ctrlist.drawList();
	}
        
         public String drawListGen(Vector objectClass, int start){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");		
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
			
                ctrlist.addHeader("No","5%","0","0");			
                ctrlist.addHeader("Department","18%","0","0");	
                ctrlist.addHeader("Program","20%","0","0");
		ctrlist.addHeader("Programs","8%","0","0");		
		ctrlist.addHeader("Total Hours","8%","0","0");		
		ctrlist.addHeader("Trainees","10%","0","0");
		ctrlist.addHeader("Trainer","10%","0","0");
                ctrlist.addHeader("Schedule","25%","0","0");
               
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
		int sumPlanHour = 0;
		int sumActHour = 0;
		int sumPlanTrain = 0;
		int sumActTrain = 0;
		int procent = 0;
		String strProcent = "";
                
		if(objectClass != null && objectClass.size()>0){
                        int totalDept = PstDepartment.getCount("");
                    
			for (int i = 0; i < objectClass.size(); i++) {
				// get vector object
                                Vector tmp = (Vector)objectClass.get(i);                            
                            
                                // get training plan and list of schedules
                                TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)tmp.get(0);
                                Vector schedules = (Vector)tmp.get(1);   
                                Training trn = new Training();
                                
                                Vector vctDept = new Vector();
                                
				try{
					trn = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
				}
				catch(Exception e){
					trn = new Training();
				}
                                
                                String where = PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID] + "=" + trn.getOID();                              
                                vctDept = PstTrainingDept.list(0, 0, where, "");
                                
				rowx = new Vector();
				
                                rowx.add("" + (++start));                                
                                
                                if(vctDept != null) {
                                    if(totalDept == vctDept.size()) {
                                        rowx.add("All Departments"); // 1
                                    }
                                    else {
                                        String dept = "";
                                        
                                        for(int j=0; j<vctDept.size(); j++) {
                                            TrainingDept td = (TrainingDept)vctDept.get(j);
                                            
                                            try {
                                                Department d = PstDepartment.fetchExc(td.getDepartmentId());

                                                dept += d.getDepartment();

                                                if(j<vctDept.size()-1)
                                                    dept += ", ";
                                                }
                                            catch(Exception e) {}
                                        }
                                        
                                        rowx.add(dept); //1
                                    }
                                }
                                else {
                                    rowx.add(""); //1
                                }
                                
                                rowx.add(trn.getName()); //0
                                
				rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan()));   //2		
				sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
                                			
				rowx.add(SessTraining.getDurationString(trainingActivityPlan.getTotHoursPlan()));   //3				
				sumPlanHour = sumPlanHour + trainingActivityPlan.getTotHoursPlan();
				
				rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //4				
				sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
				
				rowx.add(trainingActivityPlan.getTrainer());    //5
				
                                 String text = "";
                                String remark = "";

                                if(schedules != null && schedules.size() > 0) {
                                    for(int j=0; j<schedules.size(); j++) {
                                        TrainingSchedule schedule = (TrainingSchedule)schedules.get(j);

                                        remark += "<a href=\"javascript:cmdEdit('"+trainingActivityPlan.getOID()+"','"+schedule.getOID()+"','"+trn.getOID()+"','"+0+"')\">";
                                        remark += Formater.formatDate(schedule.getTrainDate(), "d-MMM-yy");
                                        remark += " / " + Formater.formatDate(schedule.getStartTime(), "HH:mm");
                                        remark += " - " + Formater.formatDate(schedule.getEndTime(), "HH:mm");
                                        remark += "</a>";

                                        if(j < schedules.size() - 1)
                                            remark += "<br>";

                                    }
                                }

                                rowx.add(remark);

				lstData.add(rowx);
				lstLinkData.add(String.valueOf(trainingActivityPlan.getOID()));
			}
		}else{
			rowx = new Vector();
                        
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
		lstData.add(rowx);
		
		return ctrlist.drawList();
	}
	
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request,"start");
    Date date = FRMQueryString.requestDate(request,"date");  
    int typ = FRMQueryString.requestInt(request, "type");     
    
    
    CtrlTrainingActivityPlan ctrlTrainingActivityPlan = new CtrlTrainingActivityPlan(request);

    int recordToGet = 20;
    int limit = 20;
    int vectSize = 1;
    String whereClause = "";
    
    if(trainType == PRIV_GENERAL)
        vectSize = PstDepartment.getCount("");
      
    
    Vector listDepartment = new Vector(1,1);
    Vector listPlanning = new Vector(1,1);
    Department department = new Department();
    department.setDepartment("Generic Training");
    
    /*String whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = 0"+
                         " AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(date,"yyyy-MM-dd")+"'"; 
      Vector listPlanning = PstTrainingActivityPlan.list(0,0,whereClause,"");*/
    
    /*listPlanning = (Vector)PstTrainingActivityPlan.listTrainingPlan(department.getOID(), date);
    
    if(listPlanning != null && listPlanning.size()>0){		
        vectSize = vectSize + 1;
        if(iCommand == Command.LIST || iCommand == Command.FIRST){
            limit = 4;
        }
    }*/

    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||
       (iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
        
        start = ctrlTrainingActivityPlan.actionList(iCommand, start, vectSize, limit);
        
        String filter = "";        
        String order = "";
        
        if(trainType == PRIV_DEPT)
            filter = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"="+departmentOid;
           
        order = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
        
        listDepartment = PstDepartment.list(start,limit,filter,order); 
    }
%>
<!-- End of Jsp Block -->
<html>
<head><title>HARISMA - Training</title>
<script language="javascript">
<!--

    window.focus();

    function cmdEdit(oid, title,oidDep,oidTraining,trainer, remark) {        
            self.opener.document.frm_trainingplan.<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID]%>.value = oid;
            self.opener.document.frm_trainingplan.title.value = title; 
            self.opener.document.frm_trainingplan.department_id.value = oidDep; 
            self.opener.document.frm_trainingplan.training_id.value = oidTraining; 
            self.opener.document.frm_trainingplan.<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINNER]%>.value = trainer; 
            self.opener.document.frm_trainingplan.training_plan_id.value = oid; 
            self.opener.document.frm_trainingplan.<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_REMARK]%>.value = remark; 
          
            self.close();
    }
    
    function cmdEdit(oidPlan, oidSchedule, oidTraining, oidDept) {
            self.opener.document.frm_trainingplan.department_id.value = oidDept; 
            self.opener.document.frm_trainingplan.training_id.value = oidTraining;
            self.opener.document.frm_trainingplan.training_plan_id.value = oidPlan;        
            self.opener.document.frm_trainingplan.training_schedule_id.value = oidSchedule; 
            self.opener.document.frm_trainingplan.command.value="<%=Command.LIST%>"; 
            self.opener.document.frm_trainingplan.submit();
            self.close();
    }

    function cmdListFirst(){
            document.frlist.command.value="<%=Command.FIRST%>";
            document.frlist.action="input_plan.jsp";
            document.frlist.submit();
    }
    
    function cmdListFirst(typ){
            document.frlist.command.value="<%=Command.FIRST%>";
            document.frlist.type.value=typ;
            document.frlist.action="input_plan.jsp";
            document.frlist.submit();
    }

    function cmdListPrev(){
            document.frlist.command.value="<%=Command.PREV%>";
            document.frlist.action="input_plan.jsp";
            document.frlist.submit();
    }

    function cmdListNext(){
            document.frlist.command.value="<%=Command.NEXT%>";
            document.frlist.action="input_plan.jsp";
            document.frlist.submit();
    }

    function cmdListLast(){
            document.frlist.command.value="<%=Command.LAST%>";
            document.frlist.action="input_plan.jsp";
            document.frlist.submit();
    }

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
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<form name="frlist" method="post" action="">
    <input type="hidden" name="command" value="<%=iCommand%>"> 
    <input type="hidden" name="start" value="<%=start%>"> 
    <input type="hidden" name="type" value="<%=typ%>">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FCFDEC" >
    <tr> 
        <td width="100%" valign="top" align="left"> 
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr> 
            <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td height="20"> 
                    <font color="#FF6600" face="Arial"><strong>Training Activities Plan</strong></font>
                </td>
            </tr>
            <tr> 
                <td> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                    <td class="tablecolor"> 
                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                        <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                        <tr> 
                            <td valign="top">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr align="left" valign="top"> 
                                <td height="8"  colspan="3"> 
                                <table border="0" width="100%">
                                <tr> 
                                    <td> 
                                    <table width="80%" border="0" cellspacing="2" cellpadding="0">
                                    <tr> 
                                        <td width="10%" align="right">Month : </td>
                                        <td width="90%"><%=ControlDate.drawDateMY("date",iCommand == Command.NONE?new Date():date,"MMMM","formElemen",1,-2)%></td>
                                    </tr>
                                    <tr> 
                                        <td width="10%">&nbsp;</td>
                                        <td width="90%">&nbsp;</td>
                                    </tr>
                                    <tr> 
                                        <td align="center" width="10%">&nbsp;</td>
                                        <td width="90%"> 
                                            <table width="80%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                                  <td width="6%"><a href="javascript:cmdListFirst(<%=PRIV_DEPT%>)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                  <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                  <td width="80%" class="command" nowrap align="left"><a href="javascript:cmdListFirst(<%=PRIV_DEPT%>)">View Training Plan (Departmental)</a></td>
                                            </tr>
                                            <% if(trainType == PRIV_GENERAL) { %>
                                                <tr> 
                                                  <td width="6%"><a href="javascript:cmdListFirst(<%=PRIV_GENERAL%>)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                  <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                  <td width="80%" class="command" nowrap align="left"><a href="javascript:cmdListFirst(<%=PRIV_GENERAL%>)">View Training Plan (General)</a></td>
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
                                            <td align="center" class="command"><hr></td>
                                        </tr>
                                        <tr> 
                                            <td align="center" class="command"> 
                                                <div align="center"><font size="3">PLAN OF TRAINING ACTIVITIES</font></div>
                                            </td>
                                        </tr>
                                        <tr> 
                                            <td class="command"> 
                                                <div align="center">Month : <%=Formater.formatDate(date,"MMMM yyyy")%></div>
                                            </td>
                                        </tr>                                   
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <% if(typ == PRIV_DEPT) { 

                                             for(int d=0; d<listDepartment.size(); d++){                                       
                                                department = (Department)listDepartment.get(d);      
                                                listPlanning = (Vector)PstTrainingActivityPlan.listTrainingByDept(department.getOID(), date);                                                                                        
                                                //listPlanning = (Vector)PstTrainingActivityPlan.listTraining(department.getOID(), date);
                                                
                                        %>
                                            <tr> 
                                              <td><%=drawList(listPlanning,department)%></td>
                                            </tr>
                                            <tr> 
                                              <td height="8" width="100%">&nbsp;</td>
                                            </tr>
                                        <%
                                            } // end for
                                         } 
                                         else if(typ == PRIV_GENERAL)
                                         {
                                             listPlanning = (Vector)PstTrainingActivityPlan.listTrainingByDept(0, date);

                                             if(listPlanning != null)
                                                 vectSize = listPlanning.size();
                                             else
                                                 vectSize = 0;

                                             %>
                                              <tr> 
                                                <td><%=drawListGen(listPlanning, start)%></td>
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
                                                 <% ControlLine ctrLine = new ControlLine();
                                                     ctrLine.setLocationImg(approot+"/images");
                                                     ctrLine.initDefault();
                                                  %>
                                                  <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
                                                 </td>
                                            </tr>
                                            </table>
                                            </td>
                                        </tr>
                                    </table>
                                    </td>
                                </tr>
                                <% } else if(iCommand == Command.LIST){ %>
                                    <tr> 
                                      <td height="8" width="100%">&nbsp;</td>
                                    </tr>
                                    <tr> 
                                      <td height="8" width="100%" class="comment">No Training Activity available</td>
                                    </tr>
                                <% } %>
                                
                                <tr> 
                                    <td height="8" width="100%">&nbsp;</td>
                                </tr>
                                
                                <% if(listDepartment != null && listDepartment.size()>0){ %>
                                     <tr>
                                        <td height="8" width="100%" class="comment">&nbsp; </td>
                                    </tr>
                                <% } %>
                                      </table></td>
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
  <tr> 
    <td colspan="2" height="20" bgcolor="#15A9F5">
      <%@ include file = "../../main/footer.jsp" %>
    </td>
  </tr>
</table>
</form>
</body>
</html>

