<%-- 
    Document   : search_training
    Created on : Jan 27, 2009, 2:44:45 PM
    Author     : bayu
--%>

<%@ page language = "java" %>

<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
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
<%
    int iCommand = FRMQueryString.requestCommand(request);    	
    long deptOID = FRMQueryString.requestLong(request, FrmSrcTraining.fieldNames[FrmSrcTraining.FRM_FIELD_DEPARTMENT]);
    long type = FRMQueryString.requestLong(request, FrmSrcTraining.fieldNames[FrmSrcTraining.FRM_FIELD_TRAIN_TYPE]);

    SrcTraining srcTraining = new SrcTraining();
    FrmSrcTraining frmSrcTraining = new FrmSrcTraining();
    
    String where = "";
    String order = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];

    if(trainType == PRIV_DEPT) {
        where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=" + departmentOid;
        deptOID = departmentOid;
    }    
    
    Vector vctDepartment = PstDepartment.list(0,0, where, order);
    Vector vctTrainType = PstTrainType.listAll();
 
    srcTraining.setDepartmentId(deptOID);
    srcTraining.setTrainingType(type);

    Vector vctTraining = new Vector(1,1); 
    vctTraining = PstTrainingDept.getTrainingByDepartment(deptOID, type);
      
    if(iCommand==Command.BACK) {
       
        try{
            srcTraining = (SrcTraining)session.getValue(SessTrainingHistory.SESS_SRC_TRAININGHISTORY+"_2");
           
            if(srcTraining == null) {
                srcTraining = new SrcTraining();
            }
            
            vctTraining = PstTrainingDept.getTrainingByDepartment(srcTraining.getDepartmentId(), srcTraining.getTrainingType());
            
            session.removeValue(SessTrainingHistory.SESS_SRC_TRAININGHISTORY+"_2");
        }
        catch (Exception e){
            System.out.println(e.toString());
            srcTraining = new SrcTraining();
        }		
     
    }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Search Training</title>
<script language="JavaScript">


    function cmdSearch(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            document.frmsrcemployee.action="list_training.jsp";
            document.frmsrcemployee.submit();
    }

  
    function fnTrapKD(){
       if (event.keyCode == 13) {
            document.all.aSearch.focus();
            cmdSearch();
       }
    }
	
    function cmdRefresh(){
            document.frmsrcemployee.command.value="0";
            document.frmsrcemployee.action="search_training.jsp";
            document.frmsrcemployee.submit();
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
<SCRIPT language="javascript">

    function MM_swapImgRestore() { //v3.0
      var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
    }

    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }

    function MM_findObj(n, d) { //v4.0
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
      if(!x && document.getElementById) x=document.getElementById(n); return x;
    }

    function MM_swapImage() { //v3.0
      var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
       if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
    }

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
      <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
      <!-- #BeginEditable "menumain" --> 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> 
                  <!-- #BeginEditable "contenttitle" --> 
                  Training &gt; Special Search For Training History
                  <!-- #EndEditable --> 
                  </strong></font> </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
                                  <!-- #BeginEditable "content" --> 
                                  <form name="frmsrcemployee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                     				  
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td valign="middle" colspan="2"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%">&nbsp;</td>
                                                <td width="0%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="72%">
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);      
                                                            
                                                            if(trainType == PRIV_GENERAL) {
                                                                dept_value.add("0");
                                                                dept_key.add("- ALL DEPARTMENTS -");    
                                                            }
                                                            
                                                            for(int i = 0; i < vctDepartment.size(); i++) {
                                                                    Department dept = (Department) vctDepartment.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcTraining.fieldNames[FrmSrcTraining.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcTraining.getDepartmentId(), dept_value, dept_key, " onChange=\"javascript:cmdRefresh()\"") %> </td>
                                                    </tr>                                                   
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Training Type</div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector trtype_value = new Vector(1,1);
                                                            Vector trtype_key = new Vector(1,1);   
                                                            
                                                            trtype_value.add("0");
                                                            trtype_key.add("- ALL TYPE -");
                                                                                                                                                                    
                                                            for (int i=0; i<vctTrainType.size(); i++) {
                                                                    TrainType typ = (TrainType)vctTrainType.get(i);
                                                                    trtype_key.add(typ.getTypeName());
                                                                    trtype_value.add(String.valueOf(typ.getOID()));
                                                            }
                                                        %> 
                                                        <%= ControlCombo.draw(frmSrcTraining.fieldNames[FrmSrcTraining.FRM_FIELD_TRAIN_TYPE],"formElemen",null, ""+srcTraining.getTrainingType(), trtype_value, trtype_key, " onChange=\"javascript:cmdRefresh()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.TRAINING) %></div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <%
                                                            Vector tr_value = new Vector(1,1);
                                                            Vector tr_key = new Vector(1,1);
                                                            tr_value.add(""+0);
                                                            tr_key.add("all");
                                                            for (int i = 0; i < vctTraining.size(); i++) {
                                                                    Training training = (Training) vctTraining.get(i);
                                                                    tr_key.add(training.getName());
                                                                    tr_value.add(String.valueOf(training.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcTraining.fieldNames[FrmSrcTraining.FRM_FIELD_TRAINING],"formElemen",null, ""+srcTraining.getTrainingId(), tr_value, tr_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top">
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Search Category </div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector type_value = new Vector(1,1);
                                                            Vector type_key = new Vector(1,1);        													                                    
                                                            
                                                            type_key.add("Have Been Trainned");
                                                            type_value.add(String.valueOf("0"));
																	
                                                            type_key.add("Not Yet Trainned");
                                                            type_value.add(String.valueOf("1"));
																	
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcTraining.fieldNames[FrmSrcTraining.FRM_FIELD_TYPE_OF_SEARCH],"formElemen",null, ""+srcTraining.getTypeOfSearch(), type_value, type_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap> 
                                                        <div align="left">Sort By</div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector or_value = new Vector(1,1);
                                                            Vector or_key = new Vector(1,1);        
                                                                                                   
                                                            for (int i = 0; i < FrmSrcTraining.orderKey.length; i++) {
                                                                    or_key.add(FrmSrcTraining.orderKey[i]);
                                                                    or_value.add(String.valueOf(i));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcTraining.fieldNames[FrmSrcTraining.FRM_FIELD_SORT_BY],"formElemen",null, ""+srcTraining.getSortBy(), or_value, or_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="83%">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr> 
                                                            <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Employee Training</a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>
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
                                    </form>
                                    <!-- #EndEditable -->
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
