<%-- 
    Document   : warning reprimand_bab
    Created on : Nov 6, 2014, 3:34:03 PM
    Author     : Hendra McHen
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmWarningReprimandBab"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlWarningReprimandBab"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!    
    // drawList(Vector, long); untuk menggambar table
    public String drawList(Vector objectClass, long oidBab) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.setAreaStyle("customTable");
        ctrlist.addHeader("No", "50%");
        ctrlist.addHeader("Bab Title", "90%");
        ctrlist.addHeader("Action","%30");

        /////////
        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();

        Vector lstLinkData = ctrlist.getLinkData();

        //ctrlist.setLinkPrefix("");

        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        //ctrlist.setLinkSufix("");
        ctrlist.setLinkSufix("')");
        ////////


        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        int index = -1;
        // objectClass mempunyai tipe data Vector
        // objectClass.size(); mendapatkan banyak record
        for (int i = 0; i < objectClass.size(); i++) {
            // membuat object WarningReprimandBab berdasarkan objectClass ke-i
            
            WarningReprimandBab bab = (WarningReprimandBab) objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();

            if (oidBab == bab.getOID()) {
                index = i;
            }
            no = no + 1;
            rowx.add("" + no);
            rowx.add(bab.getBabTitle());
            rowx.add("<a href=\"javascript:cmdDelete('"+bab.getOID()+"')\">Delete</a>");
            lstData.add(rowx);
            // menambah ID ke list LinkData
            lstLinkData.add(String.valueOf(bab.getOID()));

        }

        return ctrlist.draw(index); // mengembalikan data-data control list

    }

%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidBab = FRMQueryString.requestLong(request, "hidden_bab_id");
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlWarningReprimandBab ctrlBab = new CtrlWarningReprimandBab(request);
    ControlLine ctrLine = new ControlLine();
    Vector listBab = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlBab.action(iCommand, oidBab);
    /* end switch*/
    FrmWarningReprimandBab frmBab = ctrlBab.getForm();

    /*count list All Position*/
    int vectSize = PstWarningReprimandBab.getCount(whereClause);
    
    WarningReprimandBab bab = ctrlBab.getWarningReprimandBab();
    msgString = ctrlBab.getMessage();
    /*switch list Division*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstWarningReprimandBab.findLimitStart(bab.getOID(), recordToGet, whereClause, orderClause);
        oidBab = bab.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlBab.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listBab = PstWarningReprimandBab.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listBab.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listBab = PstWarningReprimandBab.list(start, recordToGet, whereClause, orderClause);
    }



%>
<!DOCTYPE html>
<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --><!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --><!-- #BeginEditable "headerscript" -->
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
        <script language="JavaScript">
            function getCmd(){
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.action = "warningreprimand_bab.jsp";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.hidden_bab_id.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.hidden_bab_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdAsk(oid){
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.hidden_bab_id.value = oid;
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.ASK%>";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.prev_command.value="<%=prevCommand%>";
                getCmd();
            }

            function cmdConfirmDelete(oid){
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.hidden_bab_id.value = oid;
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.DELETE%>";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.prev_command.value="<%=prevCommand%>";
                getCmd();
            }
            
            function cmdDelete(oid){
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.hidden_bab_id.value = oid;
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.DELETE%>";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.prev_command.value="<%=prevCommand%>";
                getCmd();
            }
            
            function cmdListFirst(){
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.PREV%>";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.command.value="<%=Command.LAST%>";
                document.<%=FrmWarningReprimandBab.FRM_NAME_BAB%>.prev_command.value="<%=Command.LAST%>";
                getCmd();
            }

        </script>
    <title>Hairisma Master Data BAB</title>
    <style type="text/css">   
        #title_master_data {background-color: #FFF; font-size: 12px; padding: 3px; color: #FF0000; border-left: 2px solid #333; padding-left: 5px;}
        #table_area {background-color: #EEEEEE;border:1px solid #CCCCCC;padding: 5px;}
        #btn {padding: 7px;color:#FFFFFF;background-color: #4474c3;border: 1px solid #2c508d;cursor: pointer; border-radius: 3px;}
        #bth:hover {background-color: #333333;}
        #inputTx {padding:7px; border: 1px solid #ccc;font-size: 14px;}
        .customTable td {padding:5px;}
    </style>
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<!-- The Main Table-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
<%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
<%@include file="../styletemplate/template_header.jsp" %>
<% } else { %>
  <tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54%">
        <!-- #BeginEditable "header" -->
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable -->
    </td>
  </tr>
  <tr>
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"><!-- #BeginEditable "menumain" -->
      <%@ include file = "../main/mnmain.jsp" %>
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
                                    Master Data &gt; BAB<!-- #EndEditable -->
                                </strong>
                                </font>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td  style="background-color:<%=bgColorContent%>; ">
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                            <tr>
                                                                <td valign="top">
                                                                    <!-- #BeginEditable "content" -->
                                                                    <form name="<%=FrmWarningReprimandBab.FRM_NAME_BAB%>" method="POST" action="">
                                                                                                               
                                                                        <input type="hidden" name="start" value="<%=start%>">
                                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                                        <input type="hidden" name="hidden_bab_id" value="<%=oidBab%>">
                                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                        <input type="hidden" name="start" value="<%=start%>">
                                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                        <table  style="padding: 9px" width="50%" border="0" cellspacing="3" cellpadding="2">
                                                                            <tr><td><div id="title_master_data">Chapter(BAB) List</div></td></tr>
                                                                            <tr>
                                                                                <%if (listBab != null && listBab.size() > 0) {%>
                                                                                
                                                                                <td>
                                                                                    <!-- untuk drawlist -->
                                                                                    <!-- membuat tabel yang ditulis dari method drowList(); -->
                                                                                    <%=drawList(listBab, oidBab)%>
                                                                                </td>
                                                                                <%} else {%>
                                                                                <td>
                                                                                    Record not found
                                                                                </td>
                                                                                <%}%>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
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
                                                                                                    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidBab == 0)) {
                                                                                                        cmd = PstWarningReprimandBab.findLimitCommand(start, recordToGet, vectSize);
                                                                                                    } else {
                                                                                                        cmd = prevCommand;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        %>
                                                                                        <%
                                                                                            ctrLine.setLocationImg(approot + "/images");
                                                                                            ctrLine.initDefault();
                                                                                        %>
                                                                                        <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                                                                                    </span>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <!-- untuk list record -->
                                                                                </td>
                                                                            </tr>
                                                                            <%if (!(iCommand == Command.ADD || iCommand == Command.EDIT)) {%>
                                                                            <tr>

                                                                                <td>

                                                                                    <button id="btn" onclick="cmdAdd()">Add New BAB</button>

                                                                                </td>

                                                                            </tr>
                                                                            <%}%>
                                                                            <!-- inputan user -->
                                                                            <%if (iCommand == Command.ADD || iCommand == Command.EDIT) {%>
                                                                            <tr><td><div id="title_master_data">Add Chapter</div></td></tr>
                                                                            <tr>
                                                                                <td>

                                                                                    <input placeholder="type bab title..." id="inputTx" type="text" name="<%=FrmWarningReprimandBab.fieldNames[FrmWarningReprimandBab.FRM_FIELD_BAB_TITLE]%>" value="<%=bab.getBabTitle()%>" size="50">
                                                                                    <button id="btn" onclick="cmdSave()">Save BAB</button>
                                                                                    <button id="btn" onclick="cmdBack()">Back</button>
                                                                                </td>
                                                                            </tr>
                                                                            
                                                                            
                                                                            <tr> 
                                                                                <td width="1%">&nbsp;</td>
                                                                                <td width="7%">&nbsp;</td>
                                                                                <td width="92%">&nbsp;</td>
                                                                            </tr>
                                                                            <%}%>   
                                                                        </table>
                                                                    </form>
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
<%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
<tr>
    <td valign="bottom">
        <!-- untuk footer -->
        <%@include file="../footer.jsp" %>
    </td>

</tr>
<%} else {%>
<tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
        <%@ include file = "../main/footer.jsp" %>
        <!-- #EndEditable --> </td>
</tr>
<%}%>
</table>
</body>
</html>
