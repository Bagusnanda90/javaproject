
<% 
/* 
 * Page Name  		:  menulist.jsp
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

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_CANTEEN, AppObjInfo.OBJ_MENU_ITEM); %>
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
<%!
	public String drawList(Vector objectClass, long menuListId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("tableheader");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("tableheader");
		ctrlist.addHeader("Menu Item","33%");
		ctrlist.addHeader("Menu Date","33%");
		ctrlist.addHeader("Menu Time","33%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			MenuList menuList = (MenuList)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(menuListId == menuList.getOID())
				 index = i;

			//rowx.add(String.valueOf(menuList.getMenuItemId()));
                        MenuItem mi = new MenuItem();
                        try {
                            mi = PstMenuItem.fetchExc(menuList.getMenuItemId());
                        }
                        catch (Exception e) {
                        }
                        rowx.add(String.valueOf(mi.getItemName()));

			String str_dt_MenuDate = ""; 
			try{
				Date dt_MenuDate = menuList.getMenuDate();
				if(dt_MenuDate==null){
					dt_MenuDate = new Date();
				}
				str_dt_MenuDate = Formater.formatDate(dt_MenuDate, "dd MMMM yyyy");
			}catch(Exception e){ str_dt_MenuDate = ""; }
			rowx.add(str_dt_MenuDate);
			rowx.add(String.valueOf(menuList.getMenuTime()));
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(menuList.getOID()));
		}
		return ctrlist.draw(index);
	}

%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidMenuList = FRMQueryString.requestLong(request, "hidden_menu_list_id");
    Date dtPeriod = FRMQueryString.requestDate(request,"date_periode");
    int dt = FRMQueryString.requestInt(request, "dt");
    int tm = FRMQueryString.requestInt(request, "tm");

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlMenuList ctrlMenuList = new CtrlMenuList(request);
    ControlLine ctrLine = new ControlLine();
    Vector listMenuList = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlMenuList.action(iCommand , oidMenuList);
    /* end switch*/
    FrmMenuList frmMenuList = ctrlMenuList.getForm();

    /*count list All MenuList*/
    int vectSize = PstMenuList.getCount(whereClause);

    MenuList menuList = ctrlMenuList.getMenuList();
    msgString =  ctrlMenuList.getMessage();

    /*switch list MenuList*/
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidMenuList == 0))
            start = PstMenuList.findLimitStart(menuList.getOID(),recordToGet, whereClause, orderClause);

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlMenuList.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listMenuList = PstMenuList.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listMenuList.size() < 1 && start > 0)
    {
        if (vectSize - recordToGet > recordToGet)
            start = start - recordToGet;   //go to Command.PREV
        else{
            start = 0 ;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listMenuList = PstMenuList.list(start,recordToGet, whereClause , orderClause);
    }

    if(iCommand == Command.NONE) {
        dtPeriod = new Date();
        dtPeriod.setDate(1);
    }

    Calendar calMenu = new GregorianCalendar(dtPeriod.getYear() + 1900, dtPeriod.getMonth(), dtPeriod.getDate());
    //out.print(calMenu.get(Calendar.YEAR));
    Date addDate = new Date(dtPeriod.getYear(),dtPeriod.getMonth(),dt);
    //addDate = dtPeriod;
    //addDate.setDate(dt);
    //out.print(dtPeriod);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Canteen</title>
<script language="JavaScript">
    function cmdAdd(){
            document.frmmenulist.hidden_menu_list_id.value="0";
            document.frmmenulist.command.value="<%=Command.ADD%>";
            document.frmmenulist.prev_command.value="<%=prevCommand%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }

    function cmdAddDaily(dt,tm){
            document.frmmenulist.hidden_menu_list_id.value="0";
            document.frmmenulist.dt.value=dt;
            document.frmmenulist.tm.value=tm;
            document.frmmenulist.command.value="<%=Command.ADD%>";
            document.frmmenulist.prev_command.value="<%=prevCommand%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }

    function cmdAsk(oidMenuList){
            document.frmmenulist.hidden_menu_list_id.value=oidMenuList;
            document.frmmenulist.command.value="<%=Command.ASK%>";
            document.frmmenulist.prev_command.value="<%=prevCommand%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }

    function cmdConfirmDelete(oidMenuList){
            document.frmmenulist.hidden_menu_list_id.value=oidMenuList;
            document.frmmenulist.command.value="<%=Command.DELETE%>";
            document.frmmenulist.prev_command.value="<%=prevCommand%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }
    function cmdSave(){
            document.frmmenulist.command.value="<%=Command.SAVE%>";
            document.frmmenulist.prev_command.value="<%=prevCommand%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
            }

    function cmdEdit(oidMenuList){
            document.frmmenulist.hidden_menu_list_id.value=oidMenuList;
            document.frmmenulist.command.value="<%=Command.EDIT%>";
            document.frmmenulist.prev_command.value="<%=prevCommand%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
            }

    function cmdCancel(oidMenuList){
            document.frmmenulist.hidden_menu_list_id.value=oidMenuList;
            document.frmmenulist.command.value="<%=Command.EDIT%>";
            document.frmmenulist.prev_command.value="<%=prevCommand%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }

    function cmdBack(){
            document.frmmenulist.command.value="<%=Command.BACK%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
            }

    function cmdListFirst(){
            document.frmmenulist.command.value="<%=Command.FIRST%>";
            document.frmmenulist.prev_command.value="<%=Command.FIRST%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }

    function cmdListPrev(){
            document.frmmenulist.command.value="<%=Command.PREV%>";
            document.frmmenulist.prev_command.value="<%=Command.PREV%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
            }

    function cmdListNext(){
            document.frmmenulist.command.value="<%=Command.NEXT%>";
            document.frmmenulist.prev_command.value="<%=Command.NEXT%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }

    function cmdListLast(){
            document.frmmenulist.command.value="<%=Command.LAST%>";
            document.frmmenulist.prev_command.value="<%=Command.LAST%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }

    function chgPeriode(){
            document.frmmenulist.command.value="<%=Command.LIST%>";
            document.frmmenulist.action="menulist.jsp";
            document.frmmenulist.submit();
    }

    function cmdPrint(){
            window.open("<%=approot%>/servlet/com.dimata.harisma.report.canteen.MenulistPdf?yr=<%=dtPeriod.getYear()%>&mn=<%=dtPeriod.getMonth()%>");
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
                  Canteen &gt; Menu List<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frmmenulist" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_menu_list_id" value="<%=oidMenuList%>">
                                      <input type="hidden" name="dt" value="<%=dt%>">
                                      <input type="hidden" name="tm" value="<%=tm%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="5%">Month</td>
                                                <td width="92%"><%=ControlDate.drawDateMY("date_periode", dtPeriod,"MMMM","formElemen", 0,-1) %> 
                                                  <a href="javascript:chgPeriode()"><i><b>change</b></i></a> 
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
                                                   <div align="center"></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <td class="listgensell" width="100%">
                                                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                    <tr> 
                                                      <td class="listgentitle" rowspan="2" width="5%">Date</td>
                                                      <td class="listgentitle" colspan="4"> 
                                                        <div align="center">Menu</div>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td class="listgentitle" colspan="2" height="18"> 
                                                        <div align="center">Breakfast</div>
                                                      </td>
                                                      <td class="listgentitle" colspan="2" height="18"> 
                                                        <div align="center">Lunch</div>
                                                      </td>
                                                    </tr>
                                                    <%
                                                        int maxDate = calMenu.getActualMaximum(Calendar.DAY_OF_MONTH);
                                                        //out.print(maxDate);
                                                        for (int i=1; i<=maxDate; i++) {
                                                            /* list semua item per hari */
                                                            String whereListBreakfast = "MENU_DATE = '" + Formater.formatDate(dtPeriod,"yyyy-MM") + "-" + String.valueOf(i) + "'";
                                                            whereListBreakfast += " AND MENU_TIME=" + 1;
                                                            //out.print(whereListBreakfast);
                                                            Vector vListBreakfast = PstMenuList.list(0,0,whereListBreakfast,"");
                                                            //out.print(vListBreakfast.size());
                                                            String whereListLunch = "MENU_DATE = '" + Formater.formatDate(dtPeriod,"yyyy-MM") + "-" + String.valueOf(i) + "'";
                                                            whereListLunch += " AND MENU_TIME=" + 2;
                                                            //out.print(whereListBreakfast);
                                                            Vector vListLunch = PstMenuList.list(0,0,whereListLunch,"");
                                                    %>
                                                    <tr valign="top"> 
                                                      <td class="listgensell" nowrap width="5%"> 
                                                        <div align="left"><%=i%> 
                                                          <%=Formater.formatDate(dtPeriod,"MMMM yyyy")%></div>
                                                      </td>
                                                      <td class="listgensell" width="44%"> 
                                                        <%
                                                                for (int j=0; j<vListBreakfast.size(); j++) {
                                                                    MenuList ml = (MenuList) vListBreakfast.get(j);
                                                                    MenuItem mi = PstMenuItem.fetchExc(ml.getMenuItemId());
                                                                    out.print("&nbsp;&nbsp;<a style=\"text-decoration='none'\" href=\"javascript:cmdEdit('" + ml.getOID() + "')\">" + mi.getItemName() + "</a><br>");
                                                                }
                                                          %>
                                                      </td>
                                                      <td class="listgensell" width="4%"> 
                                                      <% if (privAdd) { %>
                                                        <div align="center"><a style="text-decoration:none" href="javascript:cmdAddDaily('<%=i%>','1')">add</a></div>
                                                      <% } %>
                                                      </td>
                                                      <td class="listgensell" width="44%"> 
                                                        <%
                                                                for (int j=0; j<vListLunch.size(); j++) {
                                                                    MenuList ml = (MenuList) vListLunch.get(j);
                                                                    MenuItem mi = PstMenuItem.fetchExc(ml.getMenuItemId());
                                                                    out.print("&nbsp;&nbsp;<a style=\"text-decoration='none'\" href=\"javascript:cmdEdit('" + ml.getOID() + "')\">" + mi.getItemName() + "</a><br>");
                                                                }
                                                          %>
                                                      </td>
                                                      <td class="listgensell" width="3%"> 
                                                      <% if (privAdd) { %>
                                                        <div align="center"><a style="text-decoration:none" href="javascript:cmdAddDaily('<%=i%>','2')">add</a></div>
                                                      <% } %>
                                                      </td>
                                                    </tr>
                                                    <% } %>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
										
                                        <tr align="left" valign="top"> 
                                          <td> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <%-- <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">&nbsp;Menu 
                                                  List</td>
                                              </tr> --%>
                                              <%
                                                try{
                                                        if (listMenuList.size()>0){
                                                %>
                                              <%-- <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%//= drawList(listMenuList,oidMenuList)%> 
                                                </td>
                                              </tr> --%>
                                              <%  } 
						  }catch(Exception exc){ 
						  }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
                                                       int cmd = 0;
                                                           if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
                                                                (iCommand == Command.NEXT || iCommand == Command.LAST))
                                                                    cmd =iCommand; 
                                                       else{
                                                          if(iCommand == Command.NONE || prevCommand == Command.NONE)
                                                                cmd = Command.FIRST;
                                                          else
                                                          { 
                                                            if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidMenuList == 0))
                                                                cmd = PstMenuList.findLimitCommand(start,recordToGet,vectSize);
                                                            else
                                                                cmd = prevCommand;
                                                          } 
                                                       } 
                                                    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                     %>
                                                  <%//=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                              <%-- <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add 
                                                  New</a></td>
												  <td colspan="3">
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add 
                                                        New Item</a> </td>
                                                    </tr>
                                                  </table>
                                                </td>												  
                                              </tr> --%>
                                            </table>
                                          </td>
                                        </tr>
										
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmMenuList.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" width="2%">&nbsp;</td>
                                                <td height="21" valign="middle" width="7%">&nbsp;</td>
                                                <td height="21" colspan="2" width="91%" class="comment">*)= 
                                                  required</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="2%">&nbsp;</td>
                                                <td height="21" valign="top" width="7%" nowrap>Menu 
                                                  Item</td>
                                                <td height="21" colspan="2" width="91%"> 
                                                  <%-- <input type="text" name="<%=frmMenuList.fieldNames[FrmMenuList.FRM_FIELD_MENU_ITEM_ID] %>"  value="<%= menuList.getMenuItemId() %>" class="formElemen"> --%>
                                                  <% 
                                                        Vector item_value = new Vector(1,1);
                                                        Vector item_key = new Vector(1,1);
                                                        //dept_value.add("0");
                                                        //dept_key.add("select ...");
                                                        Vector listItem = PstMenuItem.list(0, 0, "", " ITEM_NAME ");
                                                        for (int i = 0; i < listItem.size(); i++) {
                                                                MenuItem item = (MenuItem) listItem.get(i);
                                                                item_key.add(item.getItemName());
                                                                item_value.add(String.valueOf(item.getOID()));
                                                        }
                                                    %>
                                                  <%= ControlCombo.draw(frmMenuList.fieldNames[FrmMenuList.FRM_FIELD_MENU_ITEM_ID],"formElemen",null, ""+menuList.getMenuItemId(), item_value, item_key) %> 
                                                  * <%= frmMenuList.getErrorMsg(FrmMenuList.FRM_FIELD_MENU_ITEM_ID) %> 
                                                </td>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="2%">&nbsp;</td>
                                                <td height="21" valign="top" width="7%" nowrap>Menu 
                                                  Date</td>
                                                <td height="21" colspan="2" width="91%"> 
                                                  <%=	ControlDate.drawDateWithStyle(frmMenuList.fieldNames[FrmMenuList.FRM_FIELD_MENU_DATE], menuList.getMenuDate() == null ? addDate : menuList.getMenuDate(), 1,-5, "formElemen", "") %> 
                                                  * <%= frmMenuList.getErrorMsg(FrmMenuList.FRM_FIELD_MENU_DATE) %> 
                                                </td>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="2%">&nbsp;</td>
                                                <td height="21" valign="top" width="7%" nowrap>Menu 
                                                  Time</td>
                                                <td height="21" colspan="2" width="91%"> 
                                                  <%-- <input type="text" name="<%=frmMenuList.fieldNames[FrmMenuList.FRM_FIELD_MENU_TIME] %>"  value="<%= menuList.getMenuTime() %>" class="formElemen"> --%>
                                                  <% 
                                                        Vector time_value = new Vector(1,1);
                                                        Vector time_key = new Vector(1,1);
                                                        time_key.add("Breakfast");
                                                        time_value.add("1");
                                                        time_key.add("Lunch");
                                                        time_value.add("2");
                                                    %>
                                                  <%= ControlCombo.draw(frmMenuList.fieldNames[FrmMenuList.FRM_FIELD_MENU_TIME],"formElemen",null, menuList.getMenuTime() == 0 ? ""+tm : ""+menuList.getMenuTime(), time_value, time_key) %> 
                                                  * <%= frmMenuList.getErrorMsg(FrmMenuList.FRM_FIELD_MENU_TIME) %> 
                                                </td>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="2%">&nbsp;</td>
                                                <td height="8" valign="middle" width="7%">&nbsp;</td>
                                                <td height="8" colspan="2" width="91%">&nbsp; 
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="4" class="command"> 
                                                  <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80");
                                                    String scomDel = "javascript:cmdAsk('"+oidMenuList+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidMenuList+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidMenuList+"')";
                                                    ctrLine.setBackCaption("Back to List");
                                                    ctrLine.setCommandStyle("buttonlink");
                                                            ctrLine.setDeleteCaption("Delete");
                                                            ctrLine.setSaveCaption("Save");
                                                            ctrLine.setAddCaption("");

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
                                                    %>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>
                                            </table>
                                            <%
                                            }
                                            else {
                                            %>
                                            <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <tr> 
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrint()" class="command">Print Menulist</a></td>
                                              </tr>
                                            </table>
                                            <%
                                            }
                                            %>
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
