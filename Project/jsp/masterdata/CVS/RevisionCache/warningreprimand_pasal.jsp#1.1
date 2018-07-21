<%-- 
    Document   : warning reprimand_pasal
    Created on : Nov 6, 2014, 4:22:56 PM
    Author     : Hendra McHen
--%>

<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmWarningReprimandPasal"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmWarningReprimandBab"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlWarningReprimandPasal"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!    // drawList(Vector, long); untuk menggambar table
    long bab_id = 0;
    public String drawList(Vector objectClass, long oidPasal) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.setAreaStyle("customTable");
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Pasal Title", "90%");
        ctrlist.addHeader("BAB Title", "90%");

        ctrlist.addHeader("Delete", "90%");
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

        // objectClass mempunyai tipe data Vector
        // objectClass.size(); mendapatkan banyak record
        for (int i = 0; i < objectClass.size(); i++) {
            // membuat object WarningReprimandBab berdasarkan objectClass ke-i
            WarningReprimandPasal pasal = (WarningReprimandPasal) objectClass.get(i);
            Vector rowx = new Vector();
            
            no = no + 1;
            rowx.add("" + no);
            rowx.add(pasal.getPasalTitle());
            
           
            WarningReprimandBab babName = new WarningReprimandBab();
            try {
                babName = PstWarningReprimandBab.fetchExc(pasal.getBabId());
            } catch (Exception e) {
            }

            if (oidPasal == pasal.getOID()){
                bab_id = pasal.getBabId();
            }
            
            rowx.add(babName.getBabTitle());
            rowx.add("<a href=\"javascript:cmdDelete('"+pasal.getOID()+"')\">Delete</a>");
            
            lstData.add(rowx);
            // menambah ID ke list LinkData
            lstLinkData.add(String.valueOf(pasal.getOID()));

        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }

%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidPasal = FRMQueryString.requestLong(request, "hidden_pasal_id");
 
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlWarningReprimandPasal ctrlPasal = new CtrlWarningReprimandPasal(request);
    ControlLine ctrLine = new ControlLine();
    Vector listPasal = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlPasal.action(iCommand, oidPasal);
    /* end switch*/
    FrmWarningReprimandPasal frmPasal = ctrlPasal.getForm();
    /*count list All Position*/
    int vectSize = PstWarningReprimandPasal.getCount(whereClause);
    WarningReprimandPasal pasal = ctrlPasal.getWarningReprimandPasal();
    msgString = ctrlPasal.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstWarningReprimandPasal.findLimitStart(pasal.getOID(), recordToGet, whereClause, orderClause);
        oidPasal = pasal.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlPasal.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listPasal = PstWarningReprimandPasal.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listPasal.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listPasal = PstWarningReprimandPasal.list(start, recordToGet, whereClause, orderClause);
    }



%>
<%

    /*
     * Some code of ComboBox
     */

    String CtrOrderClause = PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_ID];
    Vector vectMasterClass = PstWarningReprimandBab.list(0, 10, "", CtrOrderClause);

    Vector val_MasterClass = new Vector(1, 1); //hidden values that will be deliver on request (oids) 

    Vector key_MasterClass = new Vector(1, 1); //texts that displayed on combo box

    val_MasterClass.add("0");

    key_MasterClass.add("All Bab");

    for (int c = 0; c < vectMasterClass.size(); c++) {

        WarningReprimandBab cbBab = (WarningReprimandBab) vectMasterClass.get(c);

        val_MasterClass.add("" + cbBab.getOID());

        key_MasterClass.add(cbBab.getBabTitle());

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
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.action = "warningreprimand_pasal.jsp";
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.hidden_pasal_id.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.hidden_pasal_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.hidden_pasal_id.value = oid;
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value="<%=Command.DELETE%>";
                document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.prev_command.value="<%=prevCommand%>";
                getCmd();
            }
            function cmdListFirst(){
                    document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value="<%=Command.FIRST%>";
                    document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.prev_command.value="<%=Command.FIRST%>";
                    getCmd();
            }

            function cmdListPrev(){
                    document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value="<%=Command.PREV%>";
                    document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.prev_command.value="<%=Command.PREV%>";
                    getCmd();
                    }

            function cmdListNext(){
                    document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value="<%=Command.NEXT%>";
                    document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.prev_command.value="<%=Command.NEXT%>";
                    getCmd();
            }

            function cmdListLast(){
                    document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.command.value="<%=Command.LAST%>";
                    document.<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>.prev_command.value="<%=Command.LAST%>";
                    getCmd();
            }

        </script>
        <style type="text/css">   
            #title_master_data {background-color: #FFF; font-size: 12px; padding: 3px; color: #FF0000; border-left: 2px solid #333; padding-left: 5px;}
            #table_area {background-color: #EEEEEE;border:1px solid #CCCCCC;padding: 5px;}
            #btn {padding: 7px;color:#FFFFFF;background-color: #4474c3;border: 1px solid #2c508d;cursor: pointer; border-radius: 3px;}
            #bth:hover {background-color: #333333;}
            #inputTx {padding:7px; border: 1px solid #ccc;font-size: 14px;}
            .customTable td {padding:5px;}
            .cbBab {padding: 7px;border: 1px solid #ccc;font-size: 14px;}
        </style>
        <title>Hairisma Master Data Pasal</title>
        </head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
<%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
<%@include file="../styletemplate/template_header.jsp" %>
<%}else{%>
<tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"><!-- #BeginEditable "header" -->
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --></td>
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
    <td valign="top" align="left">
        <table width="100%" border="0" cellspacing="3" cellpadding="2">
            <tr>
                <td width="100%">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td height="20">
                                <font color="#FF6600" face="Arial"><strong>
                                    <!-- #BeginEditable "contenttitle" -->
                                    Master Data &gt; Pasal<!-- #EndEditable -->
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
          <form name="<%=FrmWarningReprimandPasal.FRM_NAME_PASAL%>" method="POST" action="">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="hidden_pasal_id" value="<%=oidPasal%>">
            <input type="hidden" name="vectSize" value="<%=vectSize%>">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="prev_command" value="<%=prevCommand%>">

          <table>
              <tr><td><div id="title_master_data">Pasal List</div></td></tr>
              <tr>
                  <%if (listPasal != null && listPasal.size() > 0) {%>
                  <td>
                      <!-- untuk drawlist -->
                      <!-- membuat tabel yang ditulis dari method drowList(); -->
                      <%=drawList(listPasal, oidPasal)%>
                  </td>
                  <%} else {%>
                  <td>
                      record not found
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
                                      if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidPasal == 0)) {
                                          cmd = PstWarningReprimandPasal.findLimitCommand(start, recordToGet, vectSize);
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

                    <button id="btn" onclick="cmdAdd()">Add New Pasal</button>

                </td>

              </tr>
              <%}%>
              <!-- inputan user -->
              <%if (iCommand == Command.ADD || iCommand == Command.EDIT) {%>
              <tr><td><div id="title_master_data">Add Pasal</div></td></tr>
              <tr>
                  <td>
<input placeholder="type Pasal title..." id="inputTx" type="text" name="<%=FrmWarningReprimandPasal.fieldNames[FrmWarningReprimandPasal.FRM_FIELD_PASAL_TITLE]%>" value="<%=pasal.getPasalTitle()%>" size="70">
                  </td>
              </tr>
              <tr>
                  <td>

<%=ControlCombo.draw(FrmWarningReprimandPasal.fieldNames[FrmWarningReprimandPasal.FRM_FIELD_BAB_ID], null, String.valueOf(bab_id), val_MasterClass, key_MasterClass, "", "cbBab")%>                                    
                  </td>
              </tr>
              <tr>
                  <td>
                  <button id="btn" onclick="cmdSave()">Save Pasal</button>
                  <button id="btn" onclick="cmdBack()">Back</button>
                  </td>
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
