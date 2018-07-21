
<%
            /*
             * Page Name  		:  position.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: gadnyana
             * @version  		: 01
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!    public String drawList(Vector objectClass, long divisionId, I_Dictionary dictionaryD ) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader(dictionaryD.getWord(I_Dictionary.COMPANY), "");
        ctrlist.addHeader(dictionaryD.getWord(I_Dictionary.DIVISION), "");
        ctrlist.addHeader(dictionaryD.getWord(I_Dictionary.DESCRIPTION), "");
        ctrlist.addHeader(dictionaryD.getWord(I_Dictionary.TYPE), "");
        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        for (int i = 0; i < objectClass.size(); i++) {
            Division division = (Division) objectClass.get(i);
            Vector rowx = new Vector();
            if (divisionId == division.getOID()) {
                index = i;
            }
            Company company = new Company();
            try {
                company = PstCompany.fetchExc(division.getCompanyId());
            } catch (Exception e) {
            }
            rowx.add(company.getCompany());
            rowx.add(division.getDivision());
            rowx.add(division.getDescription());
            DivisionType divType = new DivisionType();
            String divisionTypeName = "-";
            try {
                divType = PstDivisionType.fetchExc(division.getDivisionTypeId());
                divisionTypeName = divType.getTypeName();
            } catch (Exception e){
                //System.out.print("getDivisionType=>"+e.toString());//
            }
            rowx.add(divisionTypeName);
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(division.getOID()));
        }
        return ctrlist.draw(index);
    }

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidDivision = FRMQueryString.requestLong(request, "hidden_division_id");

            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = " COMPANY_ID, DIVISION ";

            CtrlDivision ctrlDivision = new CtrlDivision(request);
            ControlLine ctrLine = new ControlLine();
            Vector listDivision = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlDivision.action(iCommand, oidDivision);
            /* end switch*/
            FrmDivision frmDivision = ctrlDivision.getForm();

            /*count list All Position*/
            int vectSize = PstDivision.getCount(whereClause);

            Division division = ctrlDivision.getDivision();
            msgString = ctrlDivision.getMessage();

            /*switch list Division*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstDivision.findLimitStart(division.getOID(),recordToGet, whereClause);
                oidDivision = division.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlDivision.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listDivision = PstDivision.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listDivision.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listDivision = PstDivision.list(start, recordToGet, whereClause, orderClause);
            }

            I_Dictionary dictionaryD = userSession.getUserDictionary();
                                        dictionaryD.loadWord();
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data Division</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmdivision.hidden_division_id.value="0";
                document.frmdivision.command.value="<%=Command.ADD%>";
                document.frmdivision.prev_command.value="<%=prevCommand%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdAsk(oidDivision){
                document.frmdivision.hidden_division_id.value=oidDivision;
                document.frmdivision.command.value="<%=Command.ASK%>";
                document.frmdivision.prev_command.value="<%=prevCommand%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdConfirmDelete(oidDivision){
                document.frmdivision.hidden_division_id.value=oidDivision;
                document.frmdivision.command.value="<%=Command.DELETE%>";
                document.frmdivision.prev_command.value="<%=prevCommand%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }
            function cmdSave(){
                document.frmdivision.command.value="<%=Command.SAVE%>";
                document.frmdivision.prev_command.value="<%=prevCommand%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdEdit(oidDivision){
                document.frmdivision.hidden_division_id.value=oidDivision;
                document.frmdivision.command.value="<%=Command.EDIT%>";
                document.frmdivision.prev_command.value="<%=prevCommand%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdCancel(oidDivision){
                document.frmdivision.hidden_division_id.value=oidDivision;
                document.frmdivision.command.value="<%=Command.EDIT%>";
                document.frmdivision.prev_command.value="<%=prevCommand%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdBack(){
                document.frmdivision.command.value="<%=Command.BACK%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdListFirst(){
                document.frmdivision.command.value="<%=Command.FIRST%>";
                document.frmdivision.prev_command.value="<%=Command.FIRST%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdListPrev(){
                document.frmdivision.command.value="<%=Command.PREV%>";
                document.frmdivision.prev_command.value="<%=Command.PREV%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdListNext(){
                document.frmdivision.command.value="<%=Command.NEXT%>";
                document.frmdivision.prev_command.value="<%=Command.NEXT%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
            }

            function cmdListLast(){
                document.frmdivision.command.value="<%=Command.LAST%>";
                document.frmdivision.prev_command.value="<%=Command.LAST%>";
                document.frmdivision.action="division.jsp";
                document.frmdivision.submit();
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
        <style type="text/css">
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }

            #pos_list {
                border:1px solid #CCC; padding:3px 5px;
                background-color: #ddd; color: #333; margin:2px 0px; 
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            .note {
                padding: 5px 7px;
                background-color: #d1e8ef;
                border:1px solid #86bacb;
                color: #329abc;
                border-radius: 3px;
            }
        </style>
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
                                        <td>
                                            <div id="menu_utama">Division Master</div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:#EEE; ">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmdivision" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_division_id" value="<%=oidDivision%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td valign="middle" colspan="3" ><div id="mn_utama">Division List</div></td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                                try {
                                                                                                                    if (listDivision.size() > 0) {
                                                                                                    %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <%= drawList(listDivision, oidDivision, dictionaryD)%>
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
                                                                                                    <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmDivision.errorSize()<1)){
                                                                                                                if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmDivision.errorSize() < 1)) {
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
                                                                                                                            New </a> </td>
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
                                                                                    </table>
                                                                                    <table>
                                                                                        <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmDivision.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                        <tr align="left" valign="top">
                                                                                            <td valign="top">
                                                                                                
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr>
                                                                                                        <td><div id="mn_utama"><%=oidDivision == 0 ? "Add" : "Edit"%><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div></td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td height="100%">
                                                                                                            <table border="0" cellspacing="2" cellpadding="2">
                                                                                                                <tr align="left">
                                                                                                                    <td valign="middle">&nbsp;</td>
                                                                                                                    <td valign="middle" class="comment">*)entry required </td>
                                                                                                                </tr>
                                                                                                                <tr align="left">
                                                                                                                    <td valign="middle">
                                                                                                                        <%=dictionaryD.getWord(I_Dictionary.DIVISION)%></td>
                                                                                                                    <td valign="middle">
                                                                                                                        <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION]%>"  value="<%= division.getDivision()%>" class="elemenForm" size="50">
                                                                                                                        *<%=frmDivision.getErrorMsg(FrmDivision.FRM_FIELD_DIVISION)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left">
                                                                                                                    <td valign="middle"><%=dictionaryD.getWord(I_Dictionary.COMPANY)%></td>
                                                                                                                    <td valign="middle">
                                                                                                                        <%
                                                                                                                            Vector compKey = new Vector(1, 1);
                                                                                                                            Vector compValue = new Vector(1, 1);
                                                                                                                            Vector listComp = PstCompany.list(0, 0, "", "COMPANY");
                                                                                                                            for (int i = 0; i < listComp.size(); i++) {
                                                                                                                                Company company = (Company) listComp.get(i);
                                                                                                                                compKey.add(company.getCompany());
                                                                                                                                compValue.add("" + company.getOID());
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <%=ControlCombo.draw(frmDivision.fieldNames[FrmDivision.FRM_FIELD_COMPANY_ID], "formElemen", null, "" +division.getCompanyId(), compValue, compKey)%>
                                                                                                                        * <%=frmDivision.getErrorMsg(frmDivision.FRM_FIELD_COMPANY_ID)%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        <%=dictionaryD.getWord(I_Dictionary.DIVISION)+" "+dictionaryD.getWord("DESCRIPTION")%> </td>
                                                                                                                    <td valign="middle">
                                                                                                                        <textarea name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_DESCRIPTION]%>" class="elemenForm" cols="30" rows="3"><%= division.getDescription()%></textarea>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Division Type Id
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                        <select name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_DIVISION_TYPE_ID]%>">
                                                                                                                            <option value="0">-select-</option>
                                                                                                                            <%
                                                                                                                            Vector listDivisionType = PstDivisionType.list(0, 0, "", "");
                                                                                                                            if (listDivisionType != null && listDivisionType.size()>0){
                                                                                                                                for(int ldt=0; ldt<listDivisionType.size(); ldt++){
                                                                                                                                    DivisionType divT = (DivisionType)listDivisionType.get(ldt);
                                                                                                                                    if (division.getDivisionTypeId()== divT.getOID()){
                                                                                                                                        %>
                                                                                                                                        <option selected="selected" value="<%=divT.getOID()%>"><%=divT.getTypeName()%></option>
                                                                                                                                        <%
                                                                                                                                    } else {
                                                                                                                                        %>
                                                                                                                                        <option value="<%=divT.getOID()%>"><%=divT.getTypeName()%></option>
                                                                                                                                        <%
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                            %>
                                                                                                                            
                                                                                                                        </select>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                
                                                                                                                <tr>
                                                                                                                    <td colspan="2">
                                                                                                                        <div class="note">
                                                                                                                            <strong>note:</strong> fill some of field below, if you choose Branch of Company
                                                                                                                        </div>
                                                                                                                    </td>
                                                                                                                </tr>

                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Address
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                        <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_ADDRESS]%>" size="50" value="<%=division.getAddress()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        City
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_CITY]%>" size="50" value="<%=division.getCity()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        NPWP
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_NPWP]%>" size="50" value="<%=division.getNpwp()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Province
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_PROVINCE]%>" size="50" value="<%=division.getProvince()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Region
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_REGION]%>" size="50" value="<%=division.getRegion()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Sub Region
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_SUB_REGION]%>" size="50" value="<%=division.getSubRegion()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Village
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_VILLAGE]%>" size="50" value="<%=division.getVillage()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Area
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_AREA]%>" size="50" value="<%=division.getArea()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Telephone
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_TELEPHONE]%>" size="50" value="<%=division.getTelphone()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td valign="middle">
                                                                                                                        Fax Number
                                                                                                                    </td>
                                                                                                                    <td valign="middle">
                                                                                                                       <input type="text" name="<%=frmDivision.fieldNames[FrmDivision.FRM_FIELD_FAX_NUMBER]%>" size="50" value="<%=division.getFaxNumber()%>" />
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td colspan="2">
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidDivision + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidDivision + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidDivision + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setSaveCaption("Save");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete");
                                                                                                                ctrLine.setDeleteCaption("Delete");

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
                                                                                                
                                                                                            </td>
                                                                                            
                                                                                        </tr>
                                                                                        <%}%>
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
