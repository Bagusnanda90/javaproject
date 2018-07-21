<%-- 
    Document   : list_training
    Created on : Jan 27, 2009, 2:55:51 PM
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 0;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DB_EMPLOYEE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privPrint=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

%>
<!-- Jsp Block -->
<%!
    public String drawList(Vector objectClass, int orderNum){
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
           
            ctrlist.addHeader("No.","4%");
            ctrlist.addHeader("Payroll","8%");
            ctrlist.addHeader("Name","30%");
            ctrlist.addHeader("Department","25%");
            ctrlist.addHeader("Position","25%");
            ctrlist.addHeader("Level","8%");

            ctrlist.setLinkRow(-1);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();

            for (int i = 0; i < objectClass.size(); i++) {
                Vector temp = (Vector)objectClass.get(i);

                Employee employee = (Employee)temp.get(0);	
                Position position = (Position)temp.get(1);
                Level level = (Level)temp.get(2);
                Department dept = (Department)temp.get(3);

                Vector rowx = new Vector();
                rowx.add(String.valueOf(orderNum + i + 1));
                rowx.add(employee.getEmployeeNum());
                rowx.add(employee.getFullName());
                rowx.add(dept.getDepartment());
                rowx.add(position.getPosition());
                rowx.add(level.getLevel());

                lstData.add(rowx);
            }
           
            return ctrlist.draw();
    }
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int start = FRMQueryString.requestInt(request, "start");
    long oidEmployee = FRMQueryString.requestLong(request, "employee_id");
   
    
    int iErrCode = FRMMessage.ERR_NONE;	
    String msgStr = "";
        
    int recordToGet = 10;
    int vectSize = 0;
    String orderClause = "";
    String whereClause = "";

    ControlLine ctrLine = new ControlLine();
    
    SrcTraining srcTraining = new SrcTraining();
    FrmSrcTraining frmSrcTraining = new FrmSrcTraining(request, srcTraining);
    CtrlTraining ctrlTraining = new CtrlTraining(request);
    
    if(iCommand == Command.LIST){
            frmSrcTraining.requestEntityObject(srcTraining);			
    }

    if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand == Command.BACK)){
             try{ 
                    srcTraining = (SrcTraining)session.getValue(SessTrainingHistory.SESS_SRC_TRAININGHISTORY+"_2"); 
                    
                    if (srcTraining == null) {
                            srcTraining = new SrcTraining();
                    }
             }
             catch(Exception e){
                    srcTraining = new SrcTraining();
             }
     }

    SessTrainingHistory sessTrainingHistory = new SessTrainingHistory();
    session.putValue(SessTrainingHistory.SESS_SRC_TRAININGHISTORY+"_2", srcTraining);
	
    Department department = new Department();
    department.setDepartment("ALL DEPARTMENTS");
    
    try{ 
            department = PstDepartment.fetchExc(srcTraining.getDepartmentId());
    }
    catch(Exception e){
    }

    Training training = new Training();
    try{ 
            training = PstTraining.fetchExc(srcTraining.getTrainingId());
    }
    catch(Exception e){
    }
		
    // !
    vectSize = sessTrainingHistory.countTrainingHistory(srcTraining);


    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
	(iCommand==Command.LAST)||(iCommand==Command.LIST))
            start = ctrlTraining.actionList(iCommand, start, vectSize, recordToGet);

    // !
    Vector listEmpTraining  = sessTrainingHistory.getTrainingHistoryList(srcTraining, start, recordToGet);	
   
    
    Vector vct = new Vector(1,1);
    vct.add(srcTraining);
    vct.add(department);
    vct.add(training);

    if(vectSize==listEmpTraining.size()){
            vct.add(listEmpTraining);
    }
    else{
        //update by devin 2014-04-10
            //Vector v = sessTrainingHistory.getTrainingHistoryList(srcTraining, 0, vectSize);
        Vector v = sessTrainingHistory.getTrainingHistoryList(srcTraining, 0, recordToGet);
            vct.add(v);
    }

    session.putValue("SESS_PRINT_SPECIAL_TRAINING", vct);	
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Employee List</title>
<script language="JavaScript">

	function cmdPrint(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.employee.TrainingSearchPdf?approot=<%=approot%>");
	}

	function cmdPrintXLS(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListXLS");
	}
	
	function cmdEdit(oid){
		document.frm_employee.employee_id.value=oid;
		document.frm_employee.command.value="<%=Command.LIST%>";
		document.frm_employee.prev_command.value="<%=Command.LIST%>";
		document.frm_employee.action="training_hist_edit.jsp";
		document.frm_employee.submit();
	}


	function cmdListFirst(){
		document.frm_employee.command.value="<%=Command.FIRST%>";
		document.frm_employee.action="list_training.jsp";
		document.frm_employee.submit();
	}

	function cmdListPrev(){
		document.frm_employee.command.value="<%=Command.PREV%>";
		document.frm_employee.action="list_training.jsp";
		document.frm_employee.submit();
	}

	function cmdListNext(){
		document.frm_employee.command.value="<%=Command.NEXT%>";
		document.frm_employee.action="list_training.jsp";
		document.frm_employee.submit();
	}

	function cmdListLast(){
		document.frm_employee.command.value="<%=Command.LAST%>";
		document.frm_employee.action="list_training.jsp";
		document.frm_employee.submit();
	}

	function cmdBack(){
		document.frm_employee.command.value="<%=Command.BACK%>";
		document.frm_employee.action="search_training.jsp";
		document.frm_employee.submit();
	}

        function fnTrapKD(){
	
		switch(event.keyCode) {
			case <%=LIST_PREV%>:
				cmdListPrev();
				break;
			case <%=LIST_NEXT%>:
				cmdListNext();
				break;
			case <%=LIST_FIRST%>:
				cmdListFirst();
				break;
			case <%=LIST_LAST%>:
				cmdListLast();
				break;
			default:
				break;
		}
	}    
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
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

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/list_f2.jpg')">
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
                  &gt; Training Search &gt; Search Result<!-- #EndEditable --> 
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
                              <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_employee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="employee_id" value="<%=oidEmployee%>">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <div align="center"><b><font size="3">TRAINING 
                                              SEARCH RESULT</font></b></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="15%"><b>&nbsp;DEPARTMENT</b></td>
                                          <td width="2%"><b>:</b></td>
                                          <td width="83%"><b><%=department.getDepartment().toUpperCase()%></b></td>
                                        </tr>
                                        <tr> 
                                          <td width="15%"><b>&nbsp;TRAINING PROGRAM</b></td>
                                          <td width="2%"><b>:</b></td>
                                          <td width="83%"><b><%=training.getName().toUpperCase()%></b></td>
                                        </tr>
                                        <tr> 
                                          <td width="15%"><b>&nbsp;LIST CATEGORY</b></td>
                                          <td width="2%"><b>:</b></td>
                                          <td width="83%"> <b> 
                                            <%if(srcTraining.getTypeOfSearch()==0){%>
                                                OK (Have Been Trained)
                                            <%}else{%>
                                                NO (Not Yet Trained)
                                            <%}%>
                                            </b></td>
                                        </tr>
                                      </table>
                                      <table border="0" width="100%">
                                        <%if((listEmpTraining!=null)&&(listEmpTraining.size()>0)){%>
                                        <tr> 
                                          <td height="8" width="100%"><%=drawList(listEmpTraining, start)%></td>
                                        </tr>
                                        <%}else{%>
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><br>
                                            &nbsp;No Trainned Employee available</span> 
                                          </td>
                                        </tr>
                                        <%}%>
                                      </table>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <% ctrLine.setLocationImg(approot+"/images");
                                               ctrLine.initDefault();						
                                            %>
                                            <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%> </td>
                                        </tr>
                                        <tr> 
                                          <td nowrap align="left" class="command">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td nowrap align="left" class="command"> 
                                            <table border="0" cellspacing="0" cellpadding="0" align="left" width="309">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3001','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image3001" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap width="150"> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Employee</a></td>
                                                 
                                                <% if(privPrint) { %>
                                                    <td width="6"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/list_f2.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/list.jpg" width="24" height="24" alt="Print Out"></a></td>
                                                    <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                    <td width="78"><a href="javascript:cmdPrint()" class="command">Print 
                                                      Out</a></td>
                                                <% } %>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
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
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>

