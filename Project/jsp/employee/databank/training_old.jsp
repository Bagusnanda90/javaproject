
<% 
/* 
 * Page Name  		:  training_hist_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
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
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = 0;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_HISTORY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate =true;// userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<!-- Jsp Block -->
<%
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");	
	int iCommand = FRMQueryString.requestCommand(request);
	int recordToGet = 10;
	long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
	int start = FRMQueryString.requestInt(request, "start");
	Employee employee = new Employee();
	long oidPosition = 0;
	Position position = new Position();
	long oidDepartment = 0;
	Department department = new Department();
	//if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {
		try{
			employee = PstEmployee.fetchExc(oidEmployee);
			oidPosition = employee.getPositionId();
			position = PstPosition.fetchExc(oidPosition);
			oidDepartment = employee.getDepartmentId();
			department = PstDepartment.fetchExc(oidDepartment);
		}catch(Exception exc){
			employee = new Employee();
			position = new Position();
			department = new Department();
		}
	//}
	
	
	if(iCommand==Command.LIST){
		Vector vctHeaderPrint = new Vector(1,1);
		vctHeaderPrint.add(employee);
		vctHeaderPrint.add(position);
		vctHeaderPrint.add(department);
		session.putValue("HEADER_BUF", vctHeaderPrint);
	}
	
	
	String whereClause = PstTrainingDept.fieldNames[PstTrainingDept.FLD_DEPARTMENT_ID]+"="+department.getOID();
	String orderClause = "";
	Vector vctTrProgram = PstTrainingDept.list(0,0, whereClause, null);
	
	//------------------ end of employee process -----------------------
	
	CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
	long oidTrainingHistory = FRMQueryString.requestLong(request, "hidden_training_history_id");
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	whereClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+"="+oidEmployee;
	orderClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE];
	
	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();
	iErrCode = ctrlTrainingHistory.action(iCommand , oidTrainingHistory, request);
	errMsg = ctrlTrainingHistory.getMessage();
	FrmTrainingHistory frmTrainingHistory = ctrlTrainingHistory.getForm();
	TrainingHistory trainingHistory = ctrlTrainingHistory.getTrainingHistory();
	oidTrainingHistory = trainingHistory.getOID();
	
	//out.println(frmTrainingHistory.getErrors());
	
	//out.println("prevCommand "+prevCommand);
    //out.println("oidTrainingHistory "+oidTrainingHistory);
	
	int vectSize = 0;
	
	vectSize = PstTrainingHistory.getCount(whereClause);
	
    if(iCommand == Command.SAVE && prevCommand == Command.ADD){
		start = PstTrainingHistory.findLimitStart(oidTrainingHistory, recordToGet, whereClause, orderClause);		
    }

    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
	(iCommand==Command.LAST)||(iCommand==Command.LIST))
            start = ctrlTrainingHistory.actionList(iCommand, start, vectSize, recordToGet);
	
	Vector vctTrHistory = PstTrainingHistory.list(start, recordToGet, whereClause, orderClause);	
		//out.println("vctTrHistory "+vctTrHistory);
	
	/*out.println("start : "+start);
	out.println("iCommand : "+iCommand);
	out.println("vectSize : "+vectSize);
	*/
	/*if(((iCommand==Command.DELETE))&&(frmTrainingHistory.errorSize()<1)){
	%>
	<jsp:forward page="training_hist_list.jsp"> 
	<jsp:param name="start" value="<%=start%>" />
	<jsp:param name="hidden_training_history_id" value="<%=trainingHistory.getOID()%>" />
	</jsp:forward>
	<%
	}*/
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training</title>
<script language="JavaScript">
	<%if(iCommand==Command.ADD || iCommand==Command.EDIT || iCommand==Command.ASK || iCommand==Command.SAVE || iErrCode!=FRMMessage.NONE){%>
		window.location="#go";
	<%}%>

	function cmdBackToEmployee(){
		document.fredit.command.value="<%=Command.BACK%>";
		document.fredit.start.value="0";
		document.fredit.action="employee_list.jsp";		
		document.fredit.submit();
	}


	function cmdCancel(){
		document.fredit.command.value="<%=Command.CANCEL%>";
		document.fredit.action="training.jsp";
		document.fredit.submit();
	} 

	function cmdEdit(oid){
		document.fredit.hidden_training_history_id .value=oid;
		document.fredit.command.value="<%=Command.EDIT%>";
		document.fredit.prev_command.value="<%=Command.LIST%>";
		document.fredit.action="training.jsp";
		document.fredit.submit(); 
	} 

	function cmdAdd(){		
		document.fredit.command.value="<%=Command.ADD%>";
		document.fredit.prev_command.value="<%=Command.ADD%>";		
		document.fredit.action="training.jsp";
		document.fredit.submit(); 
	} 


	function cmdSave(){
		document.fredit.command.value="<%=Command.SAVE%>"; 
		document.fredit.action="training.jsp";
		document.fredit.submit();
	}

	function cmdAsk(oid){
		document.fredit.command.value="<%=Command.ASK%>"; 
		document.fredit.action="training.jsp";
		document.fredit.submit();
	} 

	function cmdConfirmDelete(oid){
		document.fredit.command.value="<%=Command.DELETE%>";
		document.fredit.action="training.jsp"; 
		document.fredit.submit();
	}  

	function cmdBack(){
		document.fredit.command.value="<%=Command.BACK%>"; 
		document.fredit.action="training.jsp";
		document.fredit.submit();
	}
	
	function cmdBackEmp(empOID){
	document.fredit.employee_oid.value=empOID;
	document.fredit.command.value="<%=Command.EDIT%>";	
	document.fredit.action="employee_edit.jsp";
	document.fredit.submit();
	}
	
	
	
	function cmdListFirst(){
		document.fredit.command.value="<%=Command.FIRST%>";
		document.fredit.action="training.jsp";
		document.fredit.submit();
	}

	function cmdListPrev(){
		document.fredit.command.value="<%=Command.PREV%>";
		document.fredit.action="training.jsp";
		document.fredit.submit();
	}

	function cmdListNext(){
		document.fredit.command.value="<%=Command.NEXT%>";
		document.fredit.action="training.jsp";
		document.fredit.submit();
	}

	function cmdListLast(){
		document.fredit.command.value="<%=Command.LAST%>";
		document.fredit.action="training.jsp";
		document.fredit.submit();
	}
	
	/*function cmdAdd(){
            window.open("empdopsearch.jsp?emp_number=" + document.fredit.EMP_NUMBER.value + "&emp_fullname=" + document.fredit.EMP_FULLNAME.value + "&emp_department=" + document.fredit.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}*/
	
	function cmdPrint(){
		window.open("training_hist_buffer.jsp?employee_oid=<%=oidEmployee%>&approot=<%=approot%>","reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
	}
	
	function cmdView(trainingId){
	window.open("../training/list_training_material.jsp?hidden_training_id=" + trainingId,"trainingmaterial" , "height=600,width=800,status=no,toolbar=no,menubar=no,location=no");

}



//-------------- script control line -------------------
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

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
    	//document.fredit.EMP_DEPARTMENT.style.visibility = "hidden";
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    	//document.fredit.EMP_DEPARTMENT.style.visibility = "hidden";
    }
	
    function showObjectForMenu(){
        //document.fredit.EMP_DEPARTMENT.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnCancelOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Training &gt; Training History<!-- #EndEditable --> </strong></font> 
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr ><td  height="26">
                          <br/>
                          <table width="98%" align="center" height="26" border="0" bgcolor="#FFFFFF"  cellspacing="2" cellpadding="2" >
                              <tr>
                              
                               <%-- TAB MENU --%>
                                  <td width="11%" nowrap bgcolor="#0066CC"> 
                                      <div align="center" class="tablink">
                                      <a href="javascript:cmdBackEmp('<%=oidEmployee%>')" class="tablink">Personal Data</a>
                                      </div>
                                  </td>
                                  <td width="11%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="familymember.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.FAMILY_MEMBER) %></a>
                                      </div>
                                  </td>
                                  <td width="7%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="emplanguage.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Language</a>
                                      </div>
                                  </td>
                                  <td width="8%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="empeducation.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></a>
                                      </div>
                                  </td>                                          
                                  <td width="8%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="experience.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></a>
                                      </div>
                                  </td>                                  
                                  <td width="9%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="careerpath.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></a>
                                      </div>
                                  </td>    
                                  <%-- active tab --%>
                                  <td width="7%" nowrap bgcolor="#66CCFF"> 
                                      <div align="center"  class="tablink">
                                      <span class="tablink"><%=dictionaryD.getWord(I_Dictionary.TRAINING) %></span>
                                      </div>
                                  </td>           
                                  <td width="7%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="warning.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.WARNING) %></a>
                                      </div>
                                  </td>
                                  <td width="8%" nowrap bgcolor="#0066CC"> 
                                      <div align="center">
                                      <a href="reprimand.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.REPRIMAND) %></a>
                                      </div>
                                  </td>
                                  <td width="6%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="award.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.AWARD) %></a>
                                      </div>
                                  </td>     
                                  <td width="7%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="picture.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PICTURE) %></a>
                                      </div>
                                  </td>                               										  
                                  <td width="11%" nowrap bgcolor="#0066CC">
                                      <div align="center">
                                      <a href="doc_relevant.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.RELEVANT_DOCS) %></a>
                                      </div>
                                  </td>
                                  <%-- END TAB MENU --%>
                              
                              </tr>
                            </table>
                          
                      </td></tr>
                    <tr> 
                      <td class="tablecolor"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="fredit" method="post" action="">
                                      <input type="hidden" name="command" value="">
									  <input type="hidden" name="start" value="<%=start%>">
									  <input type="hidden" name="prev_command" value="<%=prevCommand%>">
									  <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_training_history_id" value="<%=oidTrainingHistory%>">
                                      <input type="hidden" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>">
                                         
                                      <table width="100%" cellspacing="2" cellpadding="1" >
                                        <tr> 
											
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2"  valign="top" align="left"  > 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="5" nowrap>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td colspan="5" nowrap> 
                                                  <div align="center"><font size="3"><b>TRAINING 
                                                    HISTORY</b></font></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap>&nbsp;</td>
                                                <td width="18%" nowrap>&nbsp;</td>
                                                <td width="4%" nowrap>&nbsp;</td>
                                                <td width="46%" nowrap>&nbsp;</td>
                                                <td width="13%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap>&nbsp;</td>
                                                <td width="18%" nowrap> 
                                                  <div align="left">Employee 
                                                              Number</div>
                                                </td>
												<td width="4%">:</td>
                                                <td width="46%" nowrap> <%= employee.getEmployeeNum() %> </td>
                                                
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap>&nbsp;</td>
                                                <td width="18%" nowrap> 
                                                  <div align="left">Name</div>
                                                </td>
												<td width="4%">:</td>
                                                <td width="46%" nowrap> <%= employee.getFullName() %> </td>
                                                
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap>&nbsp;</td>
                                                <td width="18%" nowrap> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                </td>
												 <td width="4%">:</td>
                                                <td width="46%" nowrap>  <%=department.getDepartment()%> </td>
                                                <td width="13%" nowrap> 
                                                  <div align="left"></div>
                                                </td>
                                                <td width="18%">&nbsp; </td>
                                              </tr>
											  <tr> 
											  <td width="1%" nowrap>&nbsp;</td>
                                                 <td width="18%">Address</td>
                                                 <td width="4%">:</td>
                                                 <td width="46%"><%=employee.getAddress()%></td>
                                                  </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"  valign="top" align="left"  > 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2" align="left" nowrap valign="top"  > 
                                            <table width="98%" border="0" cellpadding="1" cellspacing="1" class="listgen">
                                              <tr class="listgentitle"> 
                                                <td width="3%"> 
                                                  <div align="center"><b>No</b></div>
                                                </td>
                                                <td width="10%"> 
                                                  <div align="center"><b>Training 
                                                    Program</b></div>
                                                </td>
                                                <td width="14%"> 
                                                  <div align="center"><b>Period</b></div>
                                                </td>
                                                <td width="8%"> 
                                                  <div align="center"><b>Duration</b></div>
                                                </td>
                                                <td width="12%"> 
                                                  <div align="center"><b>Trainer</b></div>
                                                </td>
                                                <td width="16%"> 
                                                  <div align="center"><b>Remark</b></div>
                                                </td>
												 <td width="28%"> 
                                                  <div align="center"><b>Description</b></div>
                                                </td>
												<td width="9%"> 
                                                  <div align="center"><b>Training Material</b></div>
                                                </td>
                                              </tr>
                                              <%if(vctTrHistory!=null && vctTrHistory.size()>0){
											  		for(int i=0; i<vctTrHistory.size(); i++){
														TrainingHistory trHis = (TrainingHistory)vctTrHistory.get(i);											  
											  %>
                                              <tr class="listgensell"> 
                                                <td width="3%"> 
                                                  <div align="center"><%=start + (i+1)%></div>
                                                </td>
                                                <td width="10%"> 
                                                  <%
												 
												Training trn1 = new Training();
												try{
													trn1 = PstTraining.fetchExc(trHis.getTrainingId());
												}
												catch(Exception e){
													trn1 = new Training();
												}
												%>
                                                  <a href="javascript:cmdEdit('<%=trHis.getOID()%>')"><%=trn1.getName()%></a></td>
                                                <td width="14%"><%=Formater.formatDate(trHis.getStartDate(), "dd-MM-yy")%> &nbsp;//&nbsp; <%=Formater.formatDate(trHis.getEndDate(), "dd-MM-yy")%></td>
                                                <td width="8%"> 
                                                  <div align="center"><%=trHis.getDuration()%></div>
                                                </td>
                                                <td width="12%"><%=trHis.getTrainer()%></td>
                                                <td width="16%"><%=trHis.getRemark()%></td>
												<td width="28%"><%=trn1.getDescription()%></td>
												<td width="9%" align="center"><a href="javascript:cmdView('<%=trn1.getOID()%>')">View</a></td>
                                              </tr>
                                              <%	}
											  }else{%>
                                              <tr class="listgensell"> 
                                                <td colspan="8">no training history 
                                                  available ....</td>
                                              </tr>
                                              <%}%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2" align="left" nowrap valign="top"  >
										  <%
										  //ontrolLine ctrLine = new ControlLine();
										  ctrLine.setLocationImg(approot+"/images");
										  ctrLine.initDefault();
										  %>
										  <%=ctrLine.drawImageListLimit(iCommand, vectSize, start, recordToGet)%>
										  &nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2" align="left" nowrap valign="top"  > 
										  
										  <%
										  
										  /*if(
										  iCommand==Command.LIST || iCommand==Command.DELETE || iCommand==Command.FIRST || iCommand==Command.NEXT ||
										  iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.BACK){*/%>
                                            <table cellpadding="0" cellspacing="0" border="0" width="54%">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap width="189"> 
                                                  <div align="left"><a href="javascript:cmdAdd()">Add New 
                                                    Training History</a></div>
                                                </td>
                                               
                                              </tr>
                                            </table>
											
											<%//}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >&nbsp;</td>
                                          <td  width="88%"  valign="top" align="left" class="comment">&nbsp;</td>
                                        </tr>
                                        <%if(iCommand==Command.ADD || iCommand==Command.EDIT || iCommand==Command.ASK || iCommand==Command.SAVE || iErrCode!=FRMMessage.NONE){%>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2" align="left" nowrap valign="top"  ><b>TRAINING 
                                            HISTORY EDITOR<a name="go"></a></b></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >&nbsp;</td>
                                          <td  width="88%"  valign="top" align="left" class="comment">*) 
                                            entry required</td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Training 
                                            Program</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
															dept_value.add("0");
                                                            dept_key.add("select ...");                                                          
                                                            
                                                            for (int i = 0; i < vctTrProgram.size(); i++) {
                                                                    TrainingDept dept = (TrainingDept) vctTrProgram.get(i);
																	Training trn = new Training();
																	try{
																		trn = PstTraining.fetchExc(dept.getTrainingId());
																	}
																	catch(Exception e){
																		trn = new Training();
																	}
                                                                    dept_key.add(trn.getName());
                                                                    dept_value.add(String.valueOf(trn.getOID()));
                                                            }
                                                        %>
                                            <%= ControlCombo.draw(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_PROGRAM],"formElemen",null, ""+trainingHistory.getTrainingId(), dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"") %> * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_TRAINING_PROGRAM)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Date 
                                            From </td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <%=ControlDate.drawDateWithStyle(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_DATE], (trainingHistory.getStartDate()==null) ? new Date() : trainingHistory.getStartDate(), 2, -30, "formElemen", "")%> * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_START_DATE)%> &nbsp;&nbsp;to &nbsp;&nbsp;<%=ControlDate.drawDateWithStyle(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_DATE], (trainingHistory.getEndDate()==null) ? new Date() : trainingHistory.getEndDate(), 2, -30, "formElemen", "")%> * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_END_DATE)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Duration</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <input type="text" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_DURATION]%>" value="<%=trainingHistory.getDuration()%>" class="formElemen" size="5" maxlength="5">
                                            * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_DURATION)%> hour(s)</td>
                                        </tr>
                                        <!--tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Start 
                                            Time </td>
                                          <td  width="88%"  valign="top" align="left"><%=ControlDate.drawTime(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_TIME], (trainingHistory.getStartTime()==null) ? new Date() : trainingHistory.getStartTime(), "formElemen")%> * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_START_TIME)%> , End Time <%=ControlDate.drawTime(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_TIME], (trainingHistory.getStartTime()==null) ? new Date() : trainingHistory.getStartTime(), "formElemen")%> * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_START_TIME)%> per day</td>
                                        </tr-->
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Trainer</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <input type="text" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINER]%>" value="<%=trainingHistory.getTrainer()%>" class="formElemen" size="40">
                                            * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_TRAINER)%></td>
                                        </tr>
                                        <!--tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Presence</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <input type="text" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_PRESENCE]%>" value="<%=trainingHistory.getPresence()%>" class="formElemen" size="6" maxlength="5">
                                            * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_PRESENCE)%></td>
                                        </tr-->
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Remark</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <textarea name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_REMARK]%>" cols="30" class="formElemen" rows="3"><%=trainingHistory.getRemark()%></textarea>
                                            <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_REMARK)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%"  valign="top" align="left"  >&nbsp;</td>
                                          <td  width="88%"  valign="top" align="left">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("90");
							String scomDel = "javascript:cmdAsk('"+oidTrainingHistory+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidTrainingHistory+"')";
							String scancel = "javascript:cmdEdit('"+oidTrainingHistory+"')";
							ctrLine.setBackCaption("Back to List Training History");
							ctrLine.setDeleteCaption("Delete Training History");
							ctrLine.setSaveCaption("Save Training History");
							ctrLine.setConfirmDelCaption("Yes Delete Training History");
							ctrLine.setAddCaption("Add New Data");
							ctrLine.setCommandStyle("buttonlink");

							if (privDelete){
								ctrLine.setConfirmDelCommand(sconDelCom);
								ctrLine.setDeleteCommand(scomDel);
								ctrLine.setEditCommand(scancel);
							}else{ 
								ctrLine.setConfirmDelCaption("");
								ctrLine.setDeleteCaption("");
								ctrLine.setEditCaption("");
							}

							if(privAdd == false  && privUpdate == false){
								ctrLine.setSaveCaption("");
							}

							if (privAdd == false){
								ctrLine.setAddCaption("");
							}
							
							/*if(iCommand==Command.SAVE){
								iCommand = Command.EDIT;
							}*/
							%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> </td>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>