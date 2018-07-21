<% 
/* 
 * Page Name  		:  cafechecklist.jsp
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
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<%@ page import = "com.dimata.harisma.form.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<%--
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
--%>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_CANTEEN_CAFE, AppObjInfo.OBJ_CANTEEN_CAFE_CHECKLIST); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    //out.print("privView=" + privView + " | privAdd=" + privAdd);
%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidCafeChecklist = FRMQueryString.requestLong(request, "hidden_cafe_checklist_id");
    long mealtimeId = FRMQueryString.requestLong(request, "mealtime");
    System.out.println("mealtimeId = " + mealtimeId);
    /*
    if (mealTimeId == 0) {
        Vector listmt = PstMealTime.list(0, 0, "", "");
        MealTime mt = (MealTime) listmt.get(0);
        mealTimeId = mt.getOID();
    }*/
    //Date checklistDate = FRMQueryString.requestDate(request, "checklist_date");
    Date checklistDate = FRMQueryString.requestDate(request, "FRM_FIELD_CHECK_DATE");

    if (iCommand == Command.NONE) {
        checklistDate = new Date();
        if (mealtimeId == 0) {
            Vector listmt = PstMealTime.list(0, 0, "", "");
            MealTime mt = (MealTime) listmt.get(0);
            mealtimeId = mt.getOID();
        }
    }

    String whereClause = "CHECK_DATE = '" + Formater.formatDate(checklistDate,"yyyy-MM-dd") + "'";
    whereClause += " AND ";
    whereClause += " MEAL_TIME_ID = " + mealtimeId;
    Vector vCCl = PstCafeChecklist.list(0,0,whereClause,"");
    System.out.println(whereClause + "-" + vCCl.size());
    CafeChecklist ccl = new CafeChecklist();
    if (vCCl.size() > 0) {
        ccl = (CafeChecklist) vCCl.get(0);
        oidCafeChecklist = ccl.getOID();
    }
    else {
        oidCafeChecklist = 0;
    }

    String msgString = "";
    int iErrCode = FRMMessage.NONE;

    CtrlCafeChecklist ctrlCafeChecklist = new CtrlCafeChecklist(request);
    ControlLine ctrLine = new ControlLine();
    Vector listCafeChecklist = new Vector(1,1);

    iErrCode = ctrlCafeChecklist.action(iCommand , oidCafeChecklist);
    FrmCafeChecklist frmCafeChecklist = ctrlCafeChecklist.getForm();
    FrmCafeEvaluation frmCafeEvaluation = new FrmCafeEvaluation();

    CafeChecklist cafeChecklist = ctrlCafeChecklist.getCafeChecklist();
    msgString = ctrlCafeChecklist.getMessage();

    System.out.println("1. cafeChecklist = " + oidCafeChecklist);
    if (iCommand == Command.SAVE) {
        String[] checklist_item = null;
        String[] checklist_mark = null;
        String[] remark = null;

        try {
            checklist_item = request.getParameterValues(frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_CHECKLIST_ITEM_ID]);
            checklist_mark = request.getParameterValues(frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_CHECKLIST_MARK_ID]);
            remark = request.getParameterValues(frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_REMARK]);
        }
        catch (Exception e) {}

        for (int i=0; i<checklist_item.length; i++) {
            String where = "";
                where += PstCafeEvaluation.fieldNames[PstCafeEvaluation.FLD_CAFE_CHECKLIST_ID];
                where += "=" + oidCafeChecklist;//cafeChecklist.getOID();

                where += " AND " + PstCafeEvaluation.fieldNames[PstCafeEvaluation.FLD_CHECKLIST_ITEM_ID];
                where += "=" + checklist_item[i];
            Vector vchecklist = PstCafeEvaluation.list(0, 0, where, "");
            if (vchecklist.size() > 0) {
                CafeEvaluation cafeEvaluation = (CafeEvaluation) vchecklist.get(0);
                cafeEvaluation.setCafeChecklistId(cafeChecklist.getOID());
                cafeEvaluation.setChecklistMarkId(Long.parseLong(checklist_mark[i]));
                cafeEvaluation.setChecklistItemId(Long.parseLong(checklist_item[i]));
                cafeEvaluation.setRemark(remark[i]);
                PstCafeEvaluation.updateExc(cafeEvaluation);
            }
            else {
                CafeEvaluation cafeEvaluation = new CafeEvaluation();
                cafeEvaluation.setCafeChecklistId(cafeChecklist.getOID());
                cafeEvaluation.setChecklistMarkId(Long.parseLong(checklist_mark[i]));
                cafeEvaluation.setChecklistItemId(Long.parseLong(checklist_item[i]));
                cafeEvaluation.setRemark(remark[i]);
                PstCafeEvaluation.insertExc(cafeEvaluation);
            }
        }
        oidCafeChecklist = cafeChecklist.getOID();
    }
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Canteen</title>
<script language="JavaScript">
    function cmdAdd(){
            document.frmcafechecklist.hidden_cafe_checklist_id.value="0";
            document.frmcafechecklist.command.value="<%=Command.ADD%>";
            document.frmcafechecklist.prev_command.value="<%=prevCommand%>";
            document.frmcafechecklist.action="cafechecklist.jsp";
            document.frmcafechecklist.submit();
    }

    function cmdSave(){
            document.frmcafechecklist.command.value="<%=Command.SAVE%>";
            document.frmcafechecklist.prev_command.value="<%=prevCommand%>";
            document.frmcafechecklist.action="cafechecklist.jsp";
            document.frmcafechecklist.submit();
            }

    function cmdBack(){
            document.frmcafechecklist.command.value="<%=Command.BACK%>";
            document.frmcafechecklist.action="cafechecklist.jsp";
            document.frmcafechecklist.submit();
            }

    function chgPeriode(){
            document.frmcafechecklist.command.value="<%=Command.LIST%>";
            document.frmcafechecklist.action="cafechecklist.jsp";
            document.frmcafechecklist.submit();
    }

    function chgMealTime(){
            document.frmcafechecklist.command.value="<%=Command.LIST%>";
            document.frmcafechecklist.action="cafechecklist.jsp";
            document.frmcafechecklist.submit();
    }

    function cmdPrint(){
            window.open("<%=approot%>/servlet/com.dimata.harisma.report.canteen.DailyPdf?yr=<%=checklistDate.getYear()%>&mn=<%=checklistDate.getMonth()%>&dt=<%=checklistDate.getDate()%>&mt=<%=mealtimeId%>");
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
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
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
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %> 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Canteen &gt; Cafe Checklist<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frmcafechecklist" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_cafe_checklist_id" value="<%=oidCafeChecklist%>">
                                      <input type="hidden" name="hidden_mealtime_id" value="<%=mealtimeId%>">
                                      <input type="hidden" name="hidden_checklist_date" value="<%=checklistDate%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="2%">&nbsp;</td>
                                                <td width="10%">Checklist Date :</td>
                                                <td width="88%"><%//=ControlDate.drawDateWithStyle("checklist_date", checklistDate, 1,-5, "formElemen", "") %> 
                                                                <%=ControlDate.drawDateWithStyle(frmCafeChecklist.fieldNames[FrmCafeChecklist.FRM_FIELD_CHECK_DATE], checklistDate, 1,-5, "formElemen", "") %> 
                                                  <a href="javascript:chgPeriode()"><i><b>change</b></i></a> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="2%">&nbsp;</td>
                                                <td width="10%">Meal Time :</td>
                                                <td width="88%">
                                                      <% 
                                                            Vector mt_value = new Vector(1,1);
                                                            Vector mt_key = new Vector(1,1);
                                                            //mt_value.add("0");
                                                            //mt_key.add("...");
                                                            Vector listmt = PstMealTime.list(0, 0, "", "");
                                                            for (int c=0; c<listmt.size(); c++) {
                                                                    MealTime mt = (MealTime) listmt.get(c);
                                                                    mt_key.add(mt.getMealTime());
                                                                    mt_value.add(String.valueOf(mt.getOID()));
                                                            }
                                                        %>
                                                      <%=ControlCombo.draw("mealtime","formElemen",null, ""+mealtimeId, mt_value, mt_key) %> 
                                                  <a href="javascript:chgMealTime()"><i><b>change</b></i></a> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="3">
                                                  <hr>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td width="100%">
                                            <table border="0" cellspacing="1" cellpadding="1" class="listgen" width="90%">
                                                <tr> 
                                                  <td class="listgensell" width="100%"> 
                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                      <tr>
                                                          <td><b>MELIA CAF&Eacute;</b></td>
                                                          <td><b>Month/Year:</b>
                                                                <%=(cafeChecklist.getCheckDate() == null) ? Formater.formatDate(new Date(), "MMMM/yyyy") : Formater.formatDate(cafeChecklist.getCheckDate(), "dd MMMM yyyy")%>
                                                          </td>
                                                          <td><b>Meal Time:</b></td>
                                                      </tr>
                                                      <tr>
                                                          <td><b>Daily Checklist</b></td>
                                                          <td><b>Day/Date:</b>
                                                            <%
                                                                String dayname[] = new String[7];
                                                                dayname[0] = "Sunday";dayname[1] = "Monday"; dayname[2] = "Tuesday"; dayname[3] = "Wednesday";
                                                                dayname[4] = "Thursday";dayname[5] = "Friday"; dayname[6] = "Saturday";
                                                                Date chkDate = (cafeChecklist.getCheckDate() == null) ? checklistDate : cafeChecklist.getCheckDate();
                                                            %><%=dayname[chkDate.getDay()]%>/<%=chkDate.getDate()%>
                                                          </td>
                                                          <td>
                                                            <%
                                                                MealTime mt2 = new MealTime();
                                                                try {
                                                                    mt2 = PstMealTime.fetchExc(mealtimeId);
                                                                    out.print(mt2.getMealTime());
                                                                    %>
                                                                    <input type="hidden" name="<%=frmCafeChecklist.fieldNames[FrmCafeChecklist.FRM_FIELD_MEAL_TIME_ID]%>" value="<%=mt2.getOID()%>">
                                                                    <%
                                                                } catch (Exception e) {
                                                                    out.print(mt_key.get(0));
                                                                    %>
                                                                    <input type="hidden" name="<%=frmCafeChecklist.fieldNames[FrmCafeChecklist.FRM_FIELD_MEAL_TIME_ID]%>" value="<%=mt_value.get(0)%>">
                                                                    <%
                                                                }
                                                            %>
                                                            
                                                          </td>
                                                      </tr>
                                                    </table>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <td class="listgensell" width="100%">
                                                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                    <tr>
                                                      <td class="listgentitle" width="4%">No.</td>
                                                      <td class="listgentitle" width="35%">Checklisted 
                                                        Items</td>
                                                      <td class="listgentitle" width="18%">Mark</td>
                                                      <td class="listgentitle" width="43%">Remark</td>
                                                    </tr>
                                                    <%
                                                        Vector vChecklistItem = PstChecklistItem.list(0, 0, "", "CHECKLIST_GROUP_ID");
                                                        long currGroupId = 0;
                                                        long prevGroupId = 0;
                                                        int cnt = 1;
                                                        for (int i=0; i<vChecklistItem.size(); i++) {
                                                            cnt++;
                                                            ChecklistItem ci = (ChecklistItem) vChecklistItem.get(i);
                                                            currGroupId = ci.getChecklistGroupId();
                                                            if (currGroupId != prevGroupId) { 
                                                                cnt=1;
                                                                ChecklistGroup cg = new ChecklistGroup();
                                                                try {
                                                                    cg = PstChecklistGroup.fetchExc(currGroupId);
                                                                }
                                                                catch(Exception e) {
                                                                }
                                                    %>
                                                        <tr>
                                                      <td class="listgensell" nowrap width="4%">&nbsp;</td>
                                                      <td class="listgensell" nowrap width="35%"><b><%=cg.getChecklistGroup()%></b></td>
                                                      <td class="listgensell" nowrap width="18%">&nbsp;</td>
                                                      <td class="listgensell" nowrap width="43%">&nbsp; 
                                                      </td>
                                                        </tr>
                                                    <% } %>
                                                    <tr>
                                                      <td class="listgensell" nowrap width="4%">
                                                        <div align="right"><%=cnt%></div>
                                                      </td>
                                                      <td class="listgensell" nowrap width="35%">
                                                          <input type="hidden" name="<%=frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_CHECKLIST_ITEM_ID]%>" value="<%=ci.getOID()%>">
                                                          <%=ci.getChecklistItem()%>
                                                      </td>
                                                      <td class="listgensell" nowrap width="18%">
                                                      <% 
                                                            Vector mark_value = new Vector(1,1);
                                                            Vector mark_key = new Vector(1,1);
                                                            mark_value.add("0");
                                                            mark_key.add("...");
                                                            Vector listmark = PstChecklistMark.list(0, 0, "", "");
                                                            for (int m= 0; m< listmark.size(); m++) {
                                                                    ChecklistMark mark = (ChecklistMark) listmark.get(m);
                                                                    mark_key.add(mark.getChecklistMark());
                                                                    mark_value.add(String.valueOf(mark.getOID()));
                                                            }
                                                            String wherelist = "";
                                                            String sOID = "";
                                                            wherelist += PstCafeEvaluation.fieldNames[PstCafeEvaluation.FLD_CAFE_CHECKLIST_ID];
                                                            wherelist += "=" + oidCafeChecklist;//cafeChecklist.getOID();
                                                            wherelist += " AND " + PstCafeEvaluation.fieldNames[PstCafeEvaluation.FLD_CHECKLIST_ITEM_ID];
                                                            wherelist += "=" + ci.getOID();
                                                            System.out.println("wherelist = " + wherelist);
                                                            Vector vchecklist = PstCafeEvaluation.list(0, 0, wherelist, "");
                                                            CafeEvaluation ce = new CafeEvaluation();
                                                            if (vchecklist.size() > 0) {
                                                                ce = (CafeEvaluation) vchecklist.get(0);
                                                                sOID = String.valueOf(ce.getChecklistMarkId());
                                                                System.out.println("sOID = "+sOID);
                                                            }
                                                        %>
                                                      <%= ControlCombo.draw(frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_CHECKLIST_MARK_ID],"formElemen",null, sOID, mark_value, mark_key) %> 
                                                      </td>
                                                      <td class="listgensell" nowrap width="43%">
                                                        <input type="text" name="<%=frmCafeEvaluation.fieldNames[FrmCafeEvaluation.FRM_FIELD_REMARK]%>" size="50" value="<%=ce.getRemark()%>">
                                                      </td>
                                                    </tr>
                                                    <% 
                                                        prevGroupId = currGroupId;
                                                        } 
                                                    %>
                                                  </table>
                                                </td>
                                              </tr><%--
                                                <tr> 
                                                  <td class="listgensell" width="100%"> 
                                                    
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td><b>Checked by:</b></td>
                                                      <td>&nbsp;</td>
                                                      <td><b>Approved by:</b></td>
                                                    </tr>
                                                  </table>
                                                  </td>
                                                </tr>--%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                        <% if (privAdd) { %>
                                                <td>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save Checklist"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td nowrap> 
                                                        <a href="javascript:cmdSave()" class="command">Save 
                                                        Checklist</a></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                        <% } %>
                                                <td width="100%">
                                                    <table border="0" cellspacing="0" cellpadding="0" align="left">
                                                      <tr> 
                                                        <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                        <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                        <td nowrap><a href="javascript:cmdPrint()" class="command">Print Menulist</a></td>
                                                      </tr>
                                                    </table>
                                                </td>
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
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
