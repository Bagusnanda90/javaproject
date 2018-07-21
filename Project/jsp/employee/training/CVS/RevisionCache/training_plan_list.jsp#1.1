<%-- 
    Document   : training_plan_list
    Created on : Jan 16, 2009, 9:49:52 AM
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
		ctrlist.addHeader("No. of Programs","8%","0","0");		
		ctrlist.addHeader("Total Hours","8%","0","0");		
		ctrlist.addHeader("No. of Trainees","10%","0","0");	
		ctrlist.addHeader("Trainer","10%","0","0");
		ctrlist.addHeader("Remark","16%","0","0");
		ctrlist.addHeader("Training Material","25%","0","0");

		ctrlist.setLinkRow(2);
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
			for (int i = 0; i < objectClass.size(); i++) {
				TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)objectClass.get(i);
				
				rowx = new Vector();
				if(i==0){
					rowx.add(department.getDepartment()); //0
				}else{
					rowx.add("");
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

				rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan()));   //3		
				sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
                                			
				rowx.add(SessTraining.getDurationString(trainingActivityPlan.getTotHoursPlan()));   //4				
				sumPlanHour = sumPlanHour + trainingActivityPlan.getTotHoursPlan();
				
				rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //5				
				sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
				
				rowx.add(trainingActivityPlan.getTrainer());    //6 
				rowx.add(trainingActivityPlan.getRemark());     //7
                                
				// get training material
                                rowx.add("<a style=\"text-decoration:none\" href =\"javascript:cmdView('"+trainingActivityPlan.getTrainingId()+"')\"><font color=\"#30009D\">View</font></a>");     //8

				lstData.add(rowx);
				lstLinkData.add(String.valueOf(trainingActivityPlan.getOID()));
			}
		}else{
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
			
			lstData.add(rowx);	
		}
		rowx = new Vector();
		
		rowx.add("&nbsp;<b>Total</b>");
		whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+department.getOID()+
		" AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;
		int staffCount = PstEmployee.getCount(whereClause);
		rowx.add(staffCount==0?"":""+staffCount);
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

        public String drawListGen(Vector objectClass, int start){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");		
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
			
                ctrlist.addHeader("No","5%","0","0");
		ctrlist.addHeader("Program","20%","0","0");	
                ctrlist.addHeader("Department","18%","0","0");	
		ctrlist.addHeader("No. of Programs","8%","0","0");		
		ctrlist.addHeader("Total Hours","8%","0","0");		
		ctrlist.addHeader("No. of Trainees","10%","0","0");
		ctrlist.addHeader("Trainer","10%","0","0");
		ctrlist.addHeader("Remark","16%","0","0");
		ctrlist.addHeader("Training Material","25%","0","0");

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
                
		if(objectClass != null && objectClass.size()>0){
                        int totalDept = PstDepartment.getCount("");
                    
			for (int i = 0; i < objectClass.size(); i++) {
				TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)objectClass.get(i);
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
                                rowx.add(trn.getName()); //0
                                
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
                                
				rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan()));   //2		
				sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
                                			
				rowx.add(SessTraining.getDurationString(trainingActivityPlan.getTotHoursPlan()));   //3				
				sumPlanHour = sumPlanHour + trainingActivityPlan.getTotHoursPlan();
				
				rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //4				
				sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
				
				rowx.add(trainingActivityPlan.getTrainer());    //5
				rowx.add(trainingActivityPlan.getRemark());     //6
                                
				// get training material
                                rowx.add("<a style=\"text-decoration:none\" href =\"javascript:cmdView('"+trainingActivityPlan.getTrainingId()+"')\"><font color=\"#30009D\">View</font></a>");     //7

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
    Date date = FRMQueryString.requestDate(request,"date");	
    int start = FRMQueryString.requestInt(request,"start");
    int typ = FRMQueryString.requestInt(request, "type");
    CtrlTrainingActivityPlan ctrlTrainingActivityPlan = new CtrlTrainingActivityPlan(request);

    int recordToGet = 20;
    int vectSize = PstDepartment.getCount("") + 1;
    String whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = 0"+
         " AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(date,"yyyy-MM-dd")+"'";

    Vector listDepartment = new Vector(1,1);
   
    Vector listPlanning = PstTrainingActivityPlan.list(0,0,whereClause,"");

    
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
        start = ctrlTrainingActivityPlan.actionList(iCommand, start, vectSize, recordToGet);
        
        if(trainType == PRIV_GENERAL) {        
            listDepartment = PstDepartment.list((start-1)<0?0:(start-1),(start-1)<0?recordToGet-1:recordToGet,"",PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
        }
        else if(trainType == PRIV_DEPT) {
            String filter = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=" + departmentOid;
            listDepartment = PstDepartment.list((start-1)<0?0:(start-1),(start-1)<0?recordToGet-1:recordToGet,filter,PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
        }
    }

    if(iCommand == Command.BACK)
          date = new Date();
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training Activity Plan</title>
<script language="JavaScript">

	function cmdAdd(){
		document.fract.command.value="<%=Command.ADD%>";
                document.fract.prev_command.value="<%=Command.ADD%>";
		document.fract.action="training_plan_edit.jsp";
		document.fract.submit();
	}

	function cmdEdit(oid){
		document.fract.command.value="<%=Command.EDIT%>";
                document.fract.prev_command.value="<%=Command.EDIT%>";
                document.fract.training_plan_id.value = oid;
		document.fract.action="training_plan_edit.jsp";
		document.fract.submit();
	}

	function cmdListFirst(){
		document.fract.command.value="<%=Command.FIRST%>";
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}
         
        function cmdListFirst(typ){
		document.fract.command.value="<%=Command.FIRST%>";
                document.fract.type.value=typ;
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}

	function cmdListPrev(){
		document.fract.command.value="<%=Command.PREV%>";
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}

	function cmdListNext(){
		document.fract.command.value="<%=Command.NEXT%>";
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}

	function cmdListLast(){
		document.fract.command.value="<%=Command.LAST%>";
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}
	
	function cmdView(trainingId){
                window.open("../../employee/training/list_training_material.jsp?hidden_training_id=" + trainingId,"trainingmaterial" , "height=600,width=800,status=no,toolbar=no,menubar=no,location=no");

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
function hideObjectForEmployee(){
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }
	
	function showObjectForMenu(){
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
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg','<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnSearchOn.jpg')">
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
                  &gt; Monthly Training Activities &gt; Planning<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>;" > 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="fract" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>"> 
                                      <input type="hidden" name="prev_command" value=""> 
                                      <input type="hidden" name="start" value="<%=start%>">     
                                      <input type="hidden" name="training_plan_id">
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
                                                  <div align="center"><b><font size="3">TRAINING 
                                                    ACTIVITIES PLAN</font></b></div>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td width="9%" align="right">&nbsp;</td>
                                                <td width="91%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" align="right">Month 
                                                  : </td>
                                                <td width="91%"><%=ControlDate.drawDateMY("date",iCommand == Command.NONE?new Date():date,"MMMM","formElemen",+4,-8)%></td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" align="right">&nbsp;</td>
                                                <td width="91%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" align="right">&nbsp;</td>
                                                <td width="91%"> 
                                                  <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td width="6%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee"></a></td>
                                                      <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="35%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                        New Training Plan </a></td>
                                                      <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="6%">&nbsp;</td>
                                                      <td width="1%">&nbsp;</td>
                                                      <td width="35%" class="command" nowrap>&nbsp;</td>
                                                      <td width="2%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="6%"><a href="javascript:cmdListFirst(<%=PRIV_DEPT%>)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                      <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="35%" class="command" nowrap><a href="javascript:cmdListFirst(<%=PRIV_DEPT%>)">View 
                                                       Plan (Departmental)</a></td>                                                      
                                                    </tr>
                                                    <% if(trainType == PRIV_GENERAL) { %>
                                                        <tr> 
                                                          <td width="6%">&nbsp;</td>
                                                          <td width="1%">&nbsp;</td>
                                                          <td width="35%" class="command" nowrap>&nbsp;</td>
                                                          <td width="2%">&nbsp;</td>
                                                        </tr>
                                                        <tr> 
                                                          <td width="6%"><a href="javascript:cmdListFirst(<%=PRIV_GENERAL%>)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                          <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                          <td width="35%" class="command" nowrap><a href="javascript:cmdListFirst(<%=PRIV_GENERAL%>)">View 
                                                            Plan (General)</a></td>                                                      
                                                        </tr>
                                                    <% } %>
                                                    <tr> 
                                                      <td width="6%">&nbsp;</td>
                                                      <td width="1%">&nbsp;</td>
                                                      <td width="35%" class="command" nowrap>&nbsp;</td>
                                                      <td width="2%">&nbsp;</td>
                                                    </tr>
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
                                                                                                
                                                    for(int d=0;d<listDepartment.size();d++){ 
                                                        Department department = (Department)listDepartment.get(d);
                                                        listPlanning = (Vector)PstTrainingActivityPlan.listTrainingPlanByDept(department.getOID(), date);
                                                        
                                                        //whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = "+department.getOID()+
                                                        //                                         " AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(date,"yyyy-MM-dd")+"'";
                                                        //listPlanning = PstTrainingActivityPlan.list(0,0,whereClause,"");                                            
                                                        

                                                  %>
                                                      <tr> 
                                                        <td><%=drawList(listPlanning, department)%></td>
                                                      </tr>
                                                      <tr> 
                                                        <td height="8" width="100%">&nbsp;</td>
                                                      </tr>
                                                  <%
                                                    } // end for
                                                 } 
                                                 else if(typ == PRIV_GENERAL)
                                                 {
                                                     listPlanning = (Vector)PstTrainingActivityPlan.listTrainingPlanByDept(0, date);
                                                     
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
                                                        <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}else{
                                            if(iCommand == Command.LIST){%>
                                                <tr> 
                                                  <td height="8" width="100%">&nbsp;</td>
                                                </tr>
                                                <tr> 
                                                  <td height="8" width="100%" class="comment">No 
                                                    Training Activity available</td>
                                                </tr>
                                            <%}%>
                                        <%}%>
                                        <tr> 
                                          <td height="8" width="100%">&nbsp;</td>
                                        </tr>
                                        <% if(listDepartment != null && listDepartment.size()>0){%>
                                         <tr>
                                          <td height="8" width="100%" class="comment">                                            
                                          </td>
                                        </tr>
                                        <%}%>
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
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>