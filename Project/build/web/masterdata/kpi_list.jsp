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
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!    public String drawList(Vector objectClass, long kpi_listId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No ","5%");
        ctrlist.addHeader("Company","20%");
        ctrlist.addHeader("KPI Title","20%");
        ctrlist.addHeader("Description","30%");
        ctrlist.addHeader("Valid from","10%");
        ctrlist.addHeader("Valid to","10%");
        ctrlist.addHeader("Value Type","5%");
        // ctrlist.addHeader("List Group","30%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        //kpi_list
                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                   
                    Hashtable<String, String> compNames = new Hashtable();
                    for (int c = 0; c < listComp.size(); c++) {
                        Company comp = (Company) listComp.get(c);
                        compNames.put(""+comp.getOID(), comp.getCompany());                       
                    }
        
        for (int i = 0; i < objectClass.size(); i++) {
            KPI_List kpi_list = (KPI_List) objectClass.get(i);
            Vector rowx = new Vector();
            if (kpi_listId == kpi_list.getOID()) {
                index = i;
            }
            rowx.add(""+(i+1));
            rowx.add(""+compNames.get(""+kpi_list.getCompany_id()));
            rowx.add(""+kpi_list.getKpi_title());
            rowx.add(""+kpi_list.getDescription());
            rowx.add(""+kpi_list.getValid_from());
            rowx.add(""+kpi_list.getValid_to());
            rowx.add(""+kpi_list.getValue_type());

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(kpi_list.getOID()));
        }
        return ctrlist.draw(index);
    }

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidKPI_List = FRMQueryString.requestLong(request, "hidden_kpi_list_id");
            long companyId = FRMQueryString.requestLong(request, "companyId");
            //long CompanyId = FRMQueryString.requestLong(request, "companyId");
            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            if (companyId > 0 ){
                whereClause = " COMPANY_ID = " + companyId;
            }
            String orderClause = "";

            CtrlKPI_List ctrlKPI_List = new CtrlKPI_List(request);
            ControlLine ctrLine = new ControlLine();
            Vector listKPI_List = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlKPI_List.action(iCommand, oidKPI_List);
            /* end switch*/
            FrmKPI_List frmKPI_List = ctrlKPI_List.getForm();

            /*count list All Position*/
            int vectSize = PstKPI_List.getCount(whereClause);

            KPI_List kpi_list = ctrlKPI_List.getdKPI_List();
            msgString = ctrlKPI_List.getMessage();

            /*switch list KPI_List*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstKPI_List.findLimitStart(kpi_list.getOID(),recordToGet, whereClause);
                oidKPI_List = kpi_list.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlKPI_List.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listKPI_List = PstKPI_List.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listKPI_List.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listKPI_List = PstKPI_List.list(start, recordToGet, whereClause, orderClause);
            }


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data KPI_List</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmkpi_list.hidden_kpi_list_id.value="0";
                document.frmkpi_list.command.value="<%=Command.ADD%>";
                document.frmkpi_list.prev_command.value="<%=prevCommand%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdAsk(oidKPI_List){
                document.frmkpi_list.hidden_kpi_list_id.value=oidKPI_List;
                document.frmkpi_list.command.value="<%=Command.ASK%>";
                document.frmkpi_list.prev_command.value="<%=prevCommand%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdConfirmDelete(oidKPI_List){
                document.frmkpi_list.hidden_kpi_list_id.value=oidKPI_List;
                document.frmkpi_list.command.value="<%=Command.DELETE%>";
                document.frmkpi_list.prev_command.value="<%=prevCommand%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }
            function cmdSave(){
                document.frmkpi_list.command.value="<%=Command.SAVE%>";
                document.frmkpi_list.prev_command.value="<%=prevCommand%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdEdit(oidKPI_List){
                document.frmkpi_list.hidden_kpi_list_id.value=oidKPI_List;
                document.frmkpi_list.command.value="<%=Command.EDIT%>";
                document.frmkpi_list.prev_command.value="<%=prevCommand%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdCancel(oidKPI_List){
                document.frmkpi_list.hidden_kpi_list_id.value=oidKPI_List;
                document.frmkpi_list.command.value="<%=Command.EDIT%>";
                document.frmkpi_list.prev_command.value="<%=prevCommand%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdBack(){
                document.frmkpi_list.command.value="<%=Command.BACK%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdListFirst(){
                document.frmkpi_list.command.value="<%=Command.FIRST%>";
                document.frmkpi_list.prev_command.value="<%=Command.FIRST%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdListPrev(){
                document.frmkpi_list.command.value="<%=Command.PREV%>";
                document.frmkpi_list.prev_command.value="<%=Command.PREV%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdListNext(){
                document.frmkpi_list.command.value="<%=Command.NEXT%>";
                document.frmkpi_list.prev_command.value="<%=Command.NEXT%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function cmdListLast(){
                document.frmkpi_list.command.value="<%=Command.LAST%>";
                document.frmkpi_list.prev_command.value="<%=Command.LAST%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

 function fnTrapKD1(){
       if (event.keyCode == 13) {
            document.all.aSearch.focus();
            cmdSearch();
       }
    }


function cmdUpdateSec(){
                document.frmkpi_list.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmkpi_list.action="kpi_list.jsp";
                document.frmkpi_list.submit();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
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
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
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

        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable -->
                </td>
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
            <%}%>
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
                                                    Master Data &gt; KPI_List<!-- #EndEditable -->
                                                </strong></font>
                                        </td>
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
                                                                                <form name="frmkpi_list" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_kpi_list_id" value="<%=oidKPI_List%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;KPI_List List </td>
                                                                                                        <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        KPI_List</td>
                                                                                                                    <td width="83%">
                                                                                                                        <%
                                                                                                                                    Vector comp_value2 = new Vector(1, 1);
                                                                                                                                    Vector comp_key2 = new Vector(1, 1);
                                                                                                                                    Vector listComp2 = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                                                    comp_value2.add(""+0);
                                                                                                                                    comp_key2.add("select");
                                                                                                                                    for (int i = 0; i < listComp2.size(); i++) {
                                                                                                                                        Company div2 = (Company) listComp2.get(i);
                                                                                                                                        comp_key2.add(div2.getCompany());
                                                                                                                                        comp_value2.add(String.valueOf(div2.getOID()));
                                                                                                                                    }
                                                                                                                                    
                                                                                                                                    %>
                                                                                                                       <%=ControlCombo.draw("companyId", null, "" + companyId, comp_value2, comp_key2, "onChange=\"javascript:cmdUpdateSec()\"")%>    
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                                try {
                                                                                                                    if (listKPI_List.size() > 0) {
                                                                                                    %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <%= drawList(listKPI_List, oidKPI_List)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%  }
                                                          } catch (Exception exc) {
                                                          }%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="8" align="left" colspan="3" class="command">
                                                                                                            <span class="command">
                                                                                                                <%
                                                                                                                            int cmd = 0;
                                                                                                                            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                                                                                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                                                                                                cmd = iCommand;
                                                                                                                            } else {
                                                                                                                                if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                                                                                    cmd = Command.FIRST;
                                                                                                                                } else {
                                                                                                                                    cmd = prevCommand;
                                                                                                                                }
                                                                                                                            }
                                                                                                                %>
                                                                                                                <% ctrLine.setLocationImg(approot + "/images");
                                                                                                                            ctrLine.initDefault();
                                                                                                                %>
                                                                                                                <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>
                                                                                                            </span> </td>
                                                                                                    </tr>
                                                                                                    <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmKPI_List.errorSize()<1)){
                                                                                                    if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmKPI_List.errorSize() < 1)) {
                                                                                                        if (privAdd) {%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td>
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
                                                                                                                            New KPI_List</a> </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%}
                                                                                                      }%>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmKPI_List.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr>
                                                                                                        <td class="listtitle"><%=oidKPI_List == 0 ? "Add" : "Edit"%> KPI_List</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td height="100%">
                                                                                                            <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">&nbsp;</td>
                                                                                                                    <td width="83%" class="comment">*)entry required </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        KPI_List</td>
                                                                                                                    <td width="83%">
                                                                                                                        <%
                                                                                                                                    Vector comp_value1 = new Vector(1, 1);
                                                                                                                                    Vector comp_key1 = new Vector(1, 1);
                                                                                                                                    Vector listComp1 = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                                                    comp_value1.add(""+0);
                                                                                                                                    comp_key1.add("select");
                                                                                                                                    for (int i = 0; i < listComp1.size(); i++) {
                                                                                                                                        Company div1 = (Company) listComp1.get(i);
                                                                                                                                        comp_key1.add(div1.getCompany());
                                                                                                                                        comp_value1.add(String.valueOf(div1.getOID()));
                                                                                                                                    }
                                                                                                                                    
                                                                                                                                    %>
                                                                                                                       <%= ControlCombo.draw(FrmKPI_List.fieldNames[FrmKPI_List.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + (kpi_list.getCompany_id()), comp_value1, comp_key1,"")%> 
                                                                                                                              
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        KPI Title</td>
                                                                                                                    <td width="83%">
                                                                                                                        <input type="text" name="<%=frmKPI_List.fieldNames[FrmKPI_List.FRM_FIELD_KPI_TITLE]%>"  value="<%=kpi_list.getKpi_title()%>" class="elemenForm" size="30">
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        Description</td>
                                                                                                                    <td width="83%">
                                                                                                                        <input type="text" name="<%=frmKPI_List.fieldNames[FrmKPI_List.FRM_FIELD_DESCRIPTION]%>"  value="<%=kpi_list.getDescription()%>" class="elemenForm" size="30">
                                                                                                                        *<%=frmKPI_List.getErrorMsg(FrmKPI_List.FRM_FIELD_DESCRIPTION)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        Valid From</td>
                                                                                                                    <td width="83%">
                                                                                                                        <%=ControlDate.drawDateWithStyle(frmKPI_List.fieldNames[frmKPI_List.FRM_FIELD_VALID_FROM], kpi_list.getValid_from() == null ? new Date() : kpi_list.getValid_from(), 0, -40, "formElemen")%> * <%=frmKPI_List.getErrorMsg(frmKPI_List.FRM_FIELD_VALID_FROM)%>                                
                                                                                                                   
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        Valid To</td>
                                                                                                                    <td width="83%">
                                                                                                                        <%=ControlDate.drawDateWithStyle(frmKPI_List.fieldNames[frmKPI_List.FRM_FIELD_VALID_TO], kpi_list.getValid_to() == null ? new Date() : kpi_list.getValid_to(), 0, -40, "formElemen")%> * <%=frmKPI_List.getErrorMsg(frmKPI_List.FRM_FIELD_VALID_TO)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        Value Type </td>
                                                                                                                    <td width="83%">
                                                                                                                        <textarea name="<%=frmKPI_List.fieldNames[FrmKPI_List.FRM_FIELD_VALUE_TYPE]%>" class="elemenForm" cols="30" rows="3"><%= kpi_list.getValue_type()%></textarea>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">
                                                                                                                        Value Type </td>
                                                                                                                    <td width="83%">
                                                                                                                        <% 
                                                                                                                            Vector kpigroup_value = new Vector(1,1);
                                                                                                                            Vector kpigroup_key = new Vector(1,1); 
                                                                                                                            kpigroup_value.add("0");
                                                                                                                            kpigroup_key.add("...ALL...");
                                                                                                                            Vector listkpigroup= PstKPI_Group.list(0, 0, "", PstKPI_Group.fieldNames[PstKPI_Group.FLD_KPI_TYPE_ID]);
                                                                                                                            for (int i = 0; i < listkpigroup.size(); i++) {
                                                                                                                                    KPI_Group kPI_Group = (KPI_Group) listkpigroup.get(i);
                                                                                                                                    kpigroup_key.add(kPI_Group.getGroup_title());
                                                                                                                                    kpigroup_value.add(String.valueOf(kPI_Group.getOID()));
                                                                                                                            }
                                                                                                                      %>
                                                                                                                      <%= ControlCombo.draw(frmKPI_List.fieldNames[frmKPI_List.FRM_FIELD_KPI_GROUP],"formElemen",null, "" + "", kpigroup_value, kpigroup_key, " size= 8 /*+  (listkpigroup!=null?listkpigroup.size()+1:0+1) +8*/ multiple onkeydown=\"javascript:fnTrapKD1()\"") %>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                            </table >
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td colspan="2">
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidKPI_List + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidKPI_List + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidKPI_List + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List KPI_List");
                                                                                                                ctrLine.setSaveCaption("Save KPI_List");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete KPI_List");
                                                                                                                ctrLine.setDeleteCaption("Delete KPI_List");

                                                                                                                if (privDelete) {
                                                                                                                    ctrLine.setConfirmDelCommand(sconDelCom);
                                                                                                                    ctrLine.setDeleteCommand(scomDel);
                                                                                                                    ctrLine.setEditCommand(scancel);
                                                                                                                } else {
                                                                                                                    ctrLine.setConfirmDelCaption("");
                                                                                                                    ctrLine.setDeleteCaption("");
                                                                                                                    ctrLine.setEditCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false && privUpdate == false) {
                                                                                                                    ctrLine.setSaveCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false) {
                                                                                                                    ctrLine.setAddCaption("");
                                                                                                                }

                                                                                                                if (iCommand == Command.ASK) {
                                                                                                                    ctrLine.setDeleteQuestion(msgString);
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

