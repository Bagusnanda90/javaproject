<%-- 
    Document   : input_actual
    Created on : Feb 4, 2009, 3:55:52 PM
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
	
        public String drawList(Vector objectClass, int start){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
		ctrlist.addHeader("No","5%");	
		ctrlist.addHeader("Date","20%");
		ctrlist.addHeader("Time","20%");
		ctrlist.addHeader("Topic","30%");
		ctrlist.addHeader("Department","25%");
		
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
                            trainingActivityPlan = PstTrainingActivityPlan.fetchExc(trainingActivityActual.getTrainingActivityPlanId());
			    Training tr = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
                            topic = tr.getName();
			}
			catch(Exception e){
                            System.out.println("Error : "+e.toString());
			}
			
			rowx.add(topic);
			rowx.add(department.getDepartment());			
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(trainingActivityActual.getOID()) + "','" +
                                        String.valueOf(department.getOID()) + "','" +
                                        String.valueOf(trainingActivityPlan.getTrainingId()) + "','" +
                                        String.valueOf(trainingActivityPlan.getOID()) + "','" +
                                        String.valueOf(trainingActivityActual.getScheduleId()));
                       
		}
		return ctrlist.draw();
	}
	
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request,"start");
    Date date = FRMQueryString.requestDate(request,"date");   
    int prevCommand = FRMQueryString.requestInt(request, "prev");
    
    
    ControlLine ctrLine = new ControlLine();
    CtrlTrainingActivityActual ctrlTrainingActivityActual = new CtrlTrainingActivityActual(request);

    int iErrCode = FRMMessage.ERR_NONE;
    String msgStr = "";
    int recordToGet = 10;
    int vectSize = 0;
    String whereClause = "";

          
          
    Vector records = new Vector(1,1);
    
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||
       (iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
            long oidEmployeee=0;
        
        HttpSession sess = request.getSession(true);
        
        try {               
            
            oidEmployeee = (Long)sess.getValue("oidEmployee");
           

        }catch(Exception e){
            System.out.println("[exception] "+e.toString());
        } 
        
        vectSize = PstTrainingActivityActual.getCount(date,start,recordToGet,oidEmployeee);
         
        start = ctrlTrainingActivityActual.actionList(iCommand, start, vectSize, recordToGet);
		             
        if(trainType == PRIV_DEPT) {
            records = PstTrainingActivityActual.listDeptActivity(date,start,recordToGet,departmentOid);
        }
        else {
            records = PstTrainingActivityActual.listActivity(date,start,recordToGet,oidEmployeee); 
        }
    }
    
    if(iCommand == Command.NONE || iCommand == Command.BACK )
		date = new Date();
%>
<!-- End of Jsp Block -->
<html>
<head><title>HARISMA - Training</title>
<script language="javascript">
<!--
  
    function cmdEdit(oidAct, oidDept, oidTrain, oidPlan, oidSchedule){
            self.opener.document.fredit.training_actual_id.value=oidAct;
            self.opener.document.fredit.department_id.value=oidDept;
            self.opener.document.fredit.training_id.value=oidTrain;
            self.opener.document.fredit.training_plan_id.value=oidPlan;
            self.opener.document.fredit.training_schedule_id.value=oidSchedule;
            self.opener.document.fredit.command.value="<%=Command.GOTO%>";
            self.opener.document.fredit.prev_command.value="<%=prevCommand%>";
            self.opener.document.fredit.action="training.jsp";
            self.opener.document.fredit.submit();
            self.close();
    }

    function cmdListFirst(){
            document.frlist.command.value="<%=Command.FIRST%>";
            document.frlist.action="input_actual.jsp";
            document.frlist.submit();
    }

    function cmdListPrev(){
            document.frlist.command.value="<%=Command.PREV%>";
            document.frlist.action="input_actual.jsp";
            document.frlist.submit();
    }

    function cmdListNext(){
            document.frlist.command.value="<%=Command.NEXT%>";
            document.frlist.action="input_actual.jsp";
            document.frlist.submit();
    }

    function cmdListLast(){
            document.frlist.command.value="<%=Command.LAST%>";
            document.frlist.action="input_actual.jsp";
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
    <input type="hidden" name="prev" value="<%=prevCommand%>"> 
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FCFDEC" >
    <tr> 
        <td width="88%" valign="top" align="left"> 
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr> 
            <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td height="20"> 
                    <font color="#FF6600" face="Arial"><strong>Training Activities Actual</strong></font>
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
                                                  <td width="6%"><a href="javascript:cmdListFirst()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                  <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                  <td width="80%" class="command" nowrap align="left"><a href="javascript:cmdListFirst()">View Training Actual</a></td>
                                            </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    </table>
                                    </td>
                                </tr>
                                
                                <% if((records!=null) && (records.size()>0)) { %>
                                    <tr> 
                                        <td width="100%" class="command"> 
                                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                        <tr>
                                            <td align="center" class="command"><hr></td>
                                        </tr>
                                        <tr> 
                                            <td align="center" class="command"> 
                                                <div align="center"><font size="3">TRAINING ACTIVITIES</font></div>
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
                                        <% try{ %>
                                        
                                        <tr> 
                                          <td height="8" width="100%" class="command"> 
                                            <%=drawList(records, start)%> </td>
                                        </tr>
                                        
                                        <% }catch(Exception exc){ }%>
                                        
                                        <tr>
                                          <td height="8" width="100%" class="command">
                                            <% ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                            %>
                                            <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                        </tr>
                                    </table>
                                    </td>
                                </tr>
                                <% } else if(iCommand == Command.LIST){ %>
                                    <tr> 
                                      <td height="8" width="100%">&nbsp;</td>
                                    </tr>
                                    <tr> 
                                      <td height="8" width="100%" class="comment">No training actual available</td>
                                    </tr>
                                <% } %>
                                
                                <tr> 
                                    <td height="8" width="100%">&nbsp;</td>
                                </tr>
                               
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

