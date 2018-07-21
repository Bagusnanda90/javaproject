<% 
/* 
 * Page Name  		:  trainingactivityactual_list.jsp
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
	public String drawList(Vector objectClass, int start){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
		ctrlist.addHeader("No","3%");	
		ctrlist.addHeader("Date","10%");
		ctrlist.addHeader("Time","10%");
		ctrlist.addHeader("Topic","20%");
		ctrlist.addHeader("Department<BR>Team","10%");
		ctrlist.addHeader("Trainer","12%");
		ctrlist.addHeader("Attd(pax)","5%");
		ctrlist.addHeader("Venue","10%");
		ctrlist.addHeader("Remark","20%");

		ctrlist.setLinkRow(1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp = (Vector)objectClass.get(i);
			TrainingActivityActual trainingActivityActual = (TrainingActivityActual)temp.get(0);					
			TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)temp.get(1);
			Department department = (Department)temp.get(2);
			
			Vector rowx = new Vector();
			rowx.add("<div align=\"center\">"+(start+(i+1))+"</div>");
			rowx.add(Formater.formatDate(trainingActivityActual.getDate(),"dd-MMM-yy"));
			rowx.add(Formater.formatDate(trainingActivityActual.getStartTime(),"HH.mm") + " - " +
			
			Formater.formatDate(trainingActivityActual.getEndTime(),"HH.mm"));
			
			String topic = "";
			try{
				//System.out.println("trainingActivityPlan.getTrainingId() : "+trainingActivityPlan.getTrainingId());
				//System.out.println("trainingActivityActual.setTrainingActivityPlanId() : "+trainingActivityActual.getTrainingActivityPlanId());
				trainingActivityPlan = PstTrainingActivityPlan.fetchExc(trainingActivityActual.getTrainingActivityPlanId());
				//System.out.println("trainingActivityPlan.getTrainingId() : "+trainingActivityPlan.getTrainingId());
				Training tr = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
				topic = tr.getName();
			}
			catch(Exception e){
				System.out.println("Error : "+e.toString());
			}
			
			rowx.add(topic);
			rowx.add(department.getDepartment());
			rowx.add(trainingActivityPlan.getTrainer());
			rowx.add(String.valueOf(trainingActivityActual.getAtendees()));
			rowx.add(trainingActivityActual.getVenue());
			rowx.add(trainingActivityActual.getRemark());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(trainingActivityActual.getOID()) + "','" +
                                        String.valueOf(department.getOID()) + "','" +
                                        String.valueOf(trainingActivityPlan.getTrainingId()) + "','" +
                                        String.valueOf(trainingActivityPlan.getOID()));
                       
		}
		return ctrlist.draw();
	}
%>
<%
	int iCommand = FRMQueryString.requestCommand(request);
	Date date = FRMQueryString.requestDate(request,"date");
	int start = FRMQueryString.requestInt(request,"start");

	ControlLine ctrLine = new ControlLine();
	CtrlTrainingActivityActual ctrlTrainingActivityActual = new CtrlTrainingActivityActual(request);

	int iErrCode = FRMMessage.ERR_NONE;
	String msgStr = "";
	int recordToGet = 10;
	int vectSize = 0;
	String whereClause = "";

	//vectSize = PstTrainingActivityActual.getCount(whereClause);
        vectSize = PstTrainingActivityActual.getCount(date,start,recordToGet);

	Vector records = new Vector(1,1);
	if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
		start = ctrlTrainingActivityActual.actionList(iCommand, start, vectSize, recordToGet);
		             
                if(trainType == PRIV_DEPT) {
                    records = PstTrainingActivityActual.listDeptActivity(date,start,recordToGet,departmentOid);
                }
                else {
                    records = PstTrainingActivityActual.listActivity(date,start,recordToGet); 
                }
	}

	if(iCommand == Command.NONE || iCommand == Command.BACK )
		date = new Date();
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head> 
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training Activity Actual</title>
<script language="JavaScript">
	function cmdAdd(){
		document.fractual.command.value="<%=Command.ADD%>";
		document.fractual.action="training_act_actual_edit.jsp";
		document.fractual.submit();
	}

	function cmdEdit(oidAct, oidDept, oidTrain, oidPlan){
		document.fractual.hidden_training_activity_id.value=oidAct;
                document.fractual.oidDepartment.value=oidDept;
                document.fractual.oidTraining.value=oidTrain;
                document.fractual.oidTrainingPlan.value=oidPlan;
		document.fractual.command.value="<%=Command.EDIT%>";
		document.fractual.action="training_act_actual_edit.jsp";
		document.fractual.submit();
	}

	function cmdListFirst(){
		document.fractual.command.value="<%=Command.FIRST%>";
		document.fractual.action="training_act_actual_list.jsp";
		document.fractual.submit();
	}

	function cmdListPrev(){
		document.fractual.command.value="<%=Command.PREV%>";
		document.fractual.action="training_act_actual_list.jsp";
		document.fractual.submit();
	}

	function cmdListNext(){
		document.fractual.command.value="<%=Command.NEXT%>";
		document.fractual.action="training_act_actual_list.jsp";
		document.fractual.submit();
	}

	function cmdListLast(){
		document.fractual.command.value="<%=Command.LAST%>";
		document.fractual.action="training_act_actual_list.jsp";
		document.fractual.submit();
	}
	
	function cmdPrintAll(){
		var dtYear  = document.fractual.date_yr.value;								
		var dtMonth = document.fractual.date_mn.value;
		var dtDay   = document.fractual.date_dy.value;
		
		var linkPage   = "training_act_actual_buffer.jsp?" +
						 "date_yr="+ dtYear +"&"+
						 "date_mn="+ dtMonth +"&"+
						 "date_dy="+ dtDay;
						 
		window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
	}
        
        function cmdPrintDept(){
		var dtYear  = document.fractual.date_yr.value;								
		var dtMonth = document.fractual.date_mn.value;
		var dtDay   = document.fractual.date_dy.value;

		var linkPage   = "training_act_actual_buffer_dept.jsp?" +
                                                 "dept="+ <%= departmentOid %> +"&"+
						 "date_yr="+ dtYear +"&"+
						 "date_mn="+ dtMonth +"&"+
						 "date_dy="+ dtDay;                                                
  
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg','<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Training 
                  &gt; Monthly Training Activities<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="fractual" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_training_activity_id">
                                      <input type="hidden" name="oidDepartment">
                                      <input type="hidden" name="oidTraining">
                                      <input type="hidden" name="oidTrainingPlan">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8"> 
                                            <table width="60%" border="0" cellspacing="2" cellpadding="0">
                                              <tr> 
                                                <td width="10%" align="right">Month 
                                                  : </td>
                                                <td width="90%"><%=ControlDate.drawDateMY("date",iCommand == Command.NONE?new Date():date,"MMMM","formElemen",+4,-8)%></td>
                                              </tr>
                                              <tr> 
                                                <td width="10%">&nbsp;</td>
                                                <td width="90%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="10%">&nbsp;</td>
                                                <td width="90%"> 
                                                  <table width="50%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td width="9%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee"></a></td>
                                                      <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="78%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                        New Training Activity 
                                                        Actual </a></td>
                                                      <td width="10%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="9%">&nbsp;</td>
                                                      <td width="3%">&nbsp;</td>
                                                      <td width="78%" class="command" nowrap>&nbsp;</td>
                                                      <td width="10%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="9%"><a href="javascript:cmdListFirst()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                      <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                      <td width="78%" class="command" nowrap><a href="javascript:cmdListFirst()">View 
                                                        Training Activity Actual</a></td>                                                     
                                                    </tr>
                                                    <tr> 
                                                      <td width="9%">&nbsp;</td>
                                                      <td width="3%">&nbsp;</td>
                                                      <td width="78%" class="command" nowrap>&nbsp;</td>
                                                      <td width="10%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="9%">&nbsp;</td>
                                                      <td width="3%">&nbsp;</td>
                                                      <td width="78%" class="command" nowrap>&nbsp;</td>
                                                      <td width="10%">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="8" width="100%" class="comment">
                                            <hr>
                                          </td>
                                        </tr>
                                        <%if((records!=null)&&(records.size()>0)){%>
                                        <tr> 
                                          <td height="8" width="100%" class="command"> 
                                            <div align="center"><font size="3">TRAINING 
                                              ACTIVITIES REPORT</font></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="8" width="100%" class="command"> 
                                            <div align="center">MONTH : <%=Formater.formatDate(date,"MMMM yyyy")%></div>
                                          </td>
                                        </tr>
                                        <%try{%>
                                        <tr> 
                                          <td height="8" width="100%" class="command"> 
                                            <%=drawList(records, start)%> </td>
                                        </tr>
                                        <%}catch(Exception exc){
                                                System.out.println(exc);
                                        }%>
                                        <tr> 
                                          <td height="8" width="100%" class="command">&nbsp;</td>
                                        </tr>
                                        <tr>
                                          <td height="8" width="100%" class="command">
                                            <% ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                            %>
                                            <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                        </tr>
                                        <%}
                                            else{
                                                if(iCommand == Command.FIRST){
                                            %>
                                        <tr> 
                                          <td> 
                                            <div align="left"><span class="comment"><br>
                                              &nbsp;&nbsp;&nbsp;No Training Activities 
                                              available</span></div>
                                          </td>
                                        </tr>
                                        <%}
                                        }%>
                                      </table>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">                                        
                                        <tr> 
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <%if((records!=null)&&(records.size()>0)){%>
                                        <tr> 
                                          <td width="46%" nowrap align="left" class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <%-- if(trainType == PRIV_GENERAL) { %>
                                                  <tr> 
                                                    <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                    <td width="24"><a href="javascript:cmdPrintAll()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td nowrap><a href="javascript:cmdPrintAll()" class="command">Print 
                                                      Training Activity</a></td>
                                                  </tr>
                                              <%
                                               } else if(trainType == PRIV_DEPT) {
                                              %>
                                                  <tr> 
                                                    <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                    <td width="24"><a href="javascript:cmdPrintDept()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td nowrap><a href="javascript:cmdPrintDept()" class="command">Print 
                                                      Departemental Training Activity</a></td>
                                                  </tr>
                                              <% } --%>
                                            </table>
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
