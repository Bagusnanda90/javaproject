<%-- 
    Document   : src_trainer
    Created on : Jan 29, 2009, 11:36:50 AM
    Author     : bayu
--%>

<%@ page language = "java" %>

<%-- package java --%>
<%@ page import = "java.util.*" %>

<%-- package dimata --%>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%-- package harisma --%>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>

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

<%-- JSP Block --%>
<%!
    public String drawList(Vector objectClass, int index) {        
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("70%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");

        ctrlist.addHeader("No","5%");
        ctrlist.addHeader("Trainer Name","20%");
       
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();

        ctrlist.setLinkRow(1);
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
               

        for(int i = 0; i < objectClass.size(); i++) {
            Vector rowx = new Vector();
            String name = (String)objectClass.get(i);
            index++;
            
            rowx.add(String.valueOf(index));
            rowx.add(name);

            lstData.add(rowx);
            lstLinkData.add(name);
        }

        return ctrlist.draw();
    } 
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int vectSize = FRMQueryString.requestInt(request, "vect_size");
    String trainer = FRMQueryString.requestString(request, "trainer");
    String formName = FRMQueryString.requestString(request, "formName");
    String name = FRMQueryString.requestString(request, "name");
 
    String whereClause = "";
    String orderClause = "";    
    int recordToGet = 10;  
    
    CtrlTrainingHistory ctrl = new CtrlTrainingHistory(request);
    ControlLine ctrLine = new ControlLine();
    Vector listTrainer = new Vector(1,1);
        
    
    if(iCommand == Command.LIST) {
        listTrainer = PstTrainingActivityPlan.getTrainer(0, recordToGet, name);
        vectSize = PstTrainingActivityPlan.countTrainer(name);
    }

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
       (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        
        start = ctrl.actionList(iCommand, start, vectSize, recordToGet);
        listTrainer = PstTrainingActivityPlan.getTrainer(start, recordToGet, name);
        
    } 
  
   
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Choose Trainer</title>
<script language="JavaScript">    
      
    function cmdEdit(name){
            self.opener.document.<%=formName%>.<%=trainer%>.value=name;
            self.opener.document.<%=formName%>.command.value="0";
            self.opener.document.<%=formName%>.submit();
            self.close();
    }
    
    function cmdList() {
            document.frm_trainer.command.value="<%=String.valueOf(Command.LIST)%>";
            document.frm_trainer.action="src_trainer.jsp";
            document.frm_trainer.submit();
    }
    
    function cmdListFirst(){
            document.frm_trainer.command.value="<%=String.valueOf(Command.FIRST)%>";
            document.frm_trainer.action="src_trainer.jsp";
            document.frm_trainer.submit();
    }

    function cmdListPrev(){
            document.frm_trainer.command.value="<%=String.valueOf(Command.PREV)%>";
            document.frm_trainer.action="src_trainer.jsp";
            document.frm_trainer.submit();
    }

    function cmdListNext(){
            document.frm_trainer.command.value="<%=String.valueOf(Command.NEXT)%>";
            document.frm_trainer.action="src_trainer.jsp";
            document.frm_trainer.submit();
    }

    function cmdListLast(){
            document.frm_trainer.command.value="<%=String.valueOf(Command.LAST)%>";
            document.frm_trainer.action="src_trainer.jsp";
            document.frm_trainer.submit();
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
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
    <!-- #EndEditable --> 
    </td> 
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
            <td height="20">
            <font color="#FF6600" face="Arial"><strong>
            <!-- #BeginEditable "contenttitle" --> 
            Trainer Search
            <!-- #EndEditable --> 
            </strong></font>
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
                            
                        <!-- #BeginEditable "content" --> 
                        <form name="frm_trainer" method ="post" action="">
                            <input type="hidden" name="command" value="<%= iCommand %>">                           
                            <input type="hidden" name="start" value="<%= start %>">     
                            <input type="hidden" name="vect_size" value="<%= vectSize %>">
                            <input type="hidden" name="formName" value="<%= formName %>">
                            <input type="hidden" name="trainer" value="<%= trainer %>">
                           
                            <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
                            
                            <tr>
                                <td width="20%">Trainer Name</td>
                                <td>:</td>
                                <td><input type="text" name="name" value="<%= name %>"></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td><input type="button" value="List Trainer" onclick="javascript:cmdList()"></td>
                            </tr>
                            
                            <% if(iCommand == Command.LIST || iCommand == Command.FIRST || iCommand == Command.PREV ||
                                  iCommand == Command.NEXT || iCommand == Command.LAST) { %>
                                <tr align="left" valign="top"> 
                                    <td height="8"  colspan="3"> 
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr align="left" valign="top"> 
                                        <td height="14" valign="middle" colspan="3" class="listtitle">
                                            &nbsp;Trainer List 
                                        </td>
                                    </tr>

                                    <% if (listTrainer.size()>0) { %>
                                        <tr align="left" valign="top"> 
                                            <td height="22" valign="middle" colspan="3"> 
                                                <%= drawList(listTrainer, start)%> 
                                            </td>
                                        </tr>
                                    <%  } else {  %>
                                        <tr align="left" valign="top"> 
                                            <td height="22" valign="middle" colspan="3"> 
                                                <p>&nbsp;&nbsp; No trainer data found!</p> 
                                            </td>
                                        </tr>
                                    <%  } %>                                
                                       <tr>
                                            <td>
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                            %>
                                                <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
                                        </td>
                                       </tr>                 
                                    </table>
                                    </td>
                                </tr>   
                            <% } %>
                            
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
<tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> 
    <!-- #BeginEditable "footer" --> 
    <%@ include file = "../../main/footer.jsp" %>
    <!-- #EndEditable --> </td>
</tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate -->
</html>