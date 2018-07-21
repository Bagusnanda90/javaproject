<%-- 
    Document   : competency_level
    Created on : Sep 30, 2015, 9:05:17 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.util.DynamicField"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCompetencyLevel"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCompetencyLevel"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String getCompetencyName(long comId){
        String str = "";
        try {
            Competency compe = PstCompetency.fetchExc(comId);
            str = compe.getCompetencyName();
        } catch (Exception e) {
            System.out.println("getCompetencyName=>"+e.toString());
        }
        return str;
    }
    
    public boolean isThereData(Vector data){
        boolean out = false;
        if (data != null && data.size()>0){
            out = true;
        }
        return out;
    }
%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidCompetencyLevel = FRMQueryString.requestLong(request, "hidden_comp_level_id");
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";
    String query = "";
    Vector fields = new Vector();
    String fieldCompId = PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_ID];
    
    CtrlCompetencyLevel ctrCompetencyLevel = new CtrlCompetencyLevel(request);
    ControlLine ctrLine = new ControlLine();
    Vector listCompetencyLevel = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrCompetencyLevel.action(iCommand, oidCompetencyLevel);
    /* end switch*/
    FrmCompetencyLevel frmCompetencyLevel = ctrCompetencyLevel.getForm();

    /*count list All Position*/
    int vectSize = PstCompetencyLevel.getCount(whereClause); 
    CompetencyLevel competencyLevel = ctrCompetencyLevel.getCompetencyLevel();
    msgString = ctrCompetencyLevel.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstCompetencyLevel.findLimitStart(competencyLevel.getOID(), recordToGet, whereClause, orderClause);
        oidCompetencyLevel = competencyLevel.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrCompetencyLevel.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* assign query */
    query  = "SELECT DISTINCT "+PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_ID];
    query += " FROM "+PstCompetencyLevel.TBL_COMPETENCY_LEVEL;
    /* assign fields */
    fields.add(fieldCompId);
    listCompetencyLevel = PstCompetencyLevel.getData(query, fields);
    


%>
<%

    /*
     * Some code of ComboBox
     */

    String CtrOrderClause = PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_ID];
    Vector vectListComp = PstCompetency.list(0, 100, "", CtrOrderClause);
    Vector valComp = new Vector(1, 1); //hidden values that will be deliver on request (oids) 
    Vector keyComp = new Vector(1, 1); //texts that displayed on combo box
    valComp.add("0");
    keyComp.add("All Competency");
    for (int c = 0; c < vectListComp.size(); c++) {
        Competency comp = (Competency) vectListComp.get(c);
        valComp.add("" + comp.getOID());
        keyComp.add(comp.getCompetencyName());
    }
    
    Vector valLevelUnit = new Vector(1,1);
    Vector keyLevelUnit = new Vector(1,1);
    valLevelUnit.add("0");
    valLevelUnit.add("1");
    valLevelUnit.add("2");
    valLevelUnit.add("3");
    keyLevelUnit.add("point");
    keyLevelUnit.add("Day");
    keyLevelUnit.add("Month");
    keyLevelUnit.add("Year");

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Competency Level</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
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
            .btn {
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            #btn1 {
              background: #f27979;
              border: 1px solid #d74e4e;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn1:hover {
              background: #d22a2a;
              border: 1px solid #c31b1b;
            }
            
            #btnX {
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            #btnX:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px; background-color: #FFF;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC; }
            .title_tbl {font-weight: bold; background-color: #DDD; color: #575757;}
            
            .form-add {
                -webkit-box-shadow: 0px 3px 29px 0px rgba(94,94,94,0.51);
                -moz-box-shadow: 0px 3px 29px 0px rgba(94,94,94,0.51);
                box-shadow: 0px 3px 29px 0px rgba(94,94,94,0.51);
                margin-left: 45px;
                border-radius: 5px;
                background-color: #FFF;
            }
            #caption {padding: 7px 17px 2px 17px; font-size: 11px; color: #575757;}
            #div_input {padding: 0px 17px;}
            
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #desc_field_type{padding:7px 12px; background-color: #F3F3F3; border:1px solid #FFF; margin:3px 0px;}
            #text_desc {background-color: #FFF;color:#575757; padding:3px; font-size: 9px;}
            #data_list{padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px;}
            #data_list_close {padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px; cursor: pointer;}
            #data_list_close:hover {padding:3px 5px; color:#FFF; background-color: #0099FF; margin:2px 1px 2px 0px; border-radius: 3px;}
        </style>
        <script language="JavaScript">
            function getCmd(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.action = "competency_level.jsp";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.hidden_comp_level_id.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.hidden_comp_level_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdListFirst(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.PREV%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.LAST%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.prev_command.value="<%=Command.LAST%>";
                getCmd();
            }
            function cmdAsk(oid){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.ASK%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.hidden_comp_level_id.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.hidden_comp_level_id.value = oid;
                getCmd();
            }

        </script>
<link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
<script src="<%=approot%>/javascripts/jquery.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
<script>
$(function() {
    $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
});
</script>
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
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
                    <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                        <tr> 
                            <td width="100%" colspan="3" valign="top" style="padding: 12px"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <!-- #BeginEditable "contenttitle" -->Competency Level<!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:9px; border:1px solid <%=garisContent%>;"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        
                                                        
                                                        <form name="<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>" method="POST" action="">

                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                        <input type="hidden" name="hidden_comp_level_id" value="<%=oidCompetencyLevel%>">
                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                            
                                                            
                                                            <table>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <div id="mn_utama">Competency Level</div>
                                                                    </td>
                                                                    <td valign="top">
                                                                        &nbsp;
                                                                    </td>
                                                                </tr>

                                                                <%
                                                                if (iCommand == Command.ASK){
                                                                %>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <span id="confirm">
                                                                            <strong>Are you sure to delete item ?</strong> &nbsp;
                                                                            <button id="btn1" onclick="javascript:cmdDelete('<%=oidCompetencyLevel%>')">Yes</button>
                                                                            &nbsp;<button id="btn1" onclick="javascript:cmdBack()">No</button>
                                                                        </span>
                                                                    </td>
                                                                </tr>
                                                                <%
                                                                }
                                                                %>
                                                                <!-- ROW DATA -->    
                                                                <tr>
                                                                    <td valign="top">
                                                                        <div style="padding:5px 0px;">
                                                                        <button id="btn" onclick="cmdAdd()">Add Data</button>
                                                                        <button id="btn" onclick="cmdBack()">Back</button>
                                                                        </div>
                                                                        <%
                                                                        if (isThereData(listCompetencyLevel)){
                                                                            for(int i=0; i<listCompetencyLevel.size(); i++){
                                                                                DynamicField dynamic = (DynamicField)listCompetencyLevel.get(i);
                                                                                %>
                                                                                <table class="tblStyle">
                                                                                    <tr>
                                                                                        <td class="title_tbl"><%=i+1%></td>
                                                                                        <td class="title_tbl"><%=getCompetencyName(Long.valueOf(dynamic.getField(fieldCompId)))%></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>&nbsp;</td>
                                                                                        <td>
                                                                                            <table class="tblStyle">
                                                                                                <tr>
                                                                                                    <td class="title_tbl">Level Min</td>
                                                                                                    <td class="title_tbl">Level Max</td>
                                                                                                    <td class="title_tbl">Level Unit</td>
                                                                                                    <td class="title_tbl">Score Value</td>
                                                                                                    <td class="title_tbl">Valid Start</td>
                                                                                                    <td class="title_tbl">Valid End</td>
                                                                                                    <td class="title_tbl">Description</td>
                                                                                                    <td class="title_tbl">&nbsp;</td>
                                                                                                </tr>
                                                                                                <%
                                                                                                whereClause = fieldCompId +"="+dynamic.getField(fieldCompId);
                                                                                                Vector listLevel = PstCompetencyLevel.list(0, 0, whereClause, "");
                                                                                                if (isThereData(listLevel)){
                                                                                                    for(int j=0; j<listLevel.size(); j++){
                                                                                                        CompetencyLevel level = (CompetencyLevel)listLevel.get(j);
                                                                                                %>
                                                                                                <tr>
                                                                                                    <td><%=level.getLevelMin()%></td>
                                                                                                    <td><%=level.getLevelMax()%></td>
                                                                                                    <td><%=level.getLevelUnit()%></td>
                                                                                                    <td><%=level.getScoreValue()%></td>
                                                                                                    <td><%=level.getValidStart()%></td>
                                                                                                    <td><%=level.getValidEnd()%></td>
                                                                                                    <td><%=level.getDescription()%></td>
                                                                                                    <td><button id="btnX" onclick="cmdEdit('<%=level.getOID()%>')">E</button>&nbsp;<button id="btnX" onclick="cmdAsk('<%=level.getOID()%>')">&times;</button></td>
                                                                                                </tr>
                                                                                                <%
                                                                                                    }                                                                                                
                                                                                                } 
                                                                                                %>
                                                                                            </table>
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                                
                                                                                <%
                                                                            }
                                                                        } else {
        
                                                                        }
                                                                        %>
                                                                    </td>
                                                                    
                                                                    <td valign="top">
                                                                        <%if (iCommand == Command.ADD || iCommand == Command.EDIT) {%>
                                                                        <div class="form-add">
                                                                        <table class="tblStyle">
                                                                            <tr>
                                                                                <td class="title_tbl" style="padding-left:21px; padding-right: 21px;">Form Competency Level</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">
                                                                                    <div id="caption">Competency</div>
                                                                                    <div id="div_input"><%=ControlCombo.draw(frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_COMPENTENCY_ID], null, String.valueOf(competencyLevel.getCompetencyId()), valComp, keyComp, "", "cbComp")%></div>
                                                                                    <div id="caption">Level Min</div>
                                                                                    <div id="div_input"><input type="text" name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_LEVEL_MIN]%>" placeholder="Level min" value="<%=competencyLevel.getLevelMin()%>" /></div>
                                                                                    <div id="caption">Level Max</div>
                                                                                    <div id="div_input"><input type="text" name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_LEVEL_MAX]%>" placeholder="Level max" value="<%=competencyLevel.getLevelMax()%>" /></div>
                                                                                    <div id="caption">Level Unit</div>
                                                                                    <div id="div_input"><%=ControlCombo.draw(frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_LEVEL_UNIT], null, "-", keyLevelUnit, keyLevelUnit, "", "cbComp")%></div>
                                                                                    <div id="caption">Score</div>
                                                                                    <div id="div_input"><input type="text" name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_SCORE_VALUE]%>" placeholder="Score value" value="<%=competencyLevel.getScoreValue()%>" /></div>
                                                                                    <div id="caption">Valid Start</div>
                                                                                    <div id="div_input"><input type="text" name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_VALID_START]%>" id="datepicker1" value="<%= competencyLevel.getValidStart() %>" /></div>
                                                                                    <div id="caption">Valid End</div>
                                                                                    <div id="div_input"><input type="text" name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_VALID_END]%>" id="datepicker2" value="<%= competencyLevel.getValidEnd() %>" /></div>
                                                                                    <div id="caption">Description</div>
                                                                                    <div id="div_input"><textarea name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_DESCRIPTION]%>" placeholder="Description"><%=competencyLevel.getDescription()%></textarea></div>
                                                                                    <div id="div_input" style="padding-top:9px; padding-bottom: 9px;">
                                                                                        <button id="btnX" onclick="cmdSave()">Save</button>
                                                                                        <button id="btnX" onclick="cmdBack()">Close</button>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                        </div>
                                                                        <%}%>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </form>
                                                        
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>
                                        
                                        </td>
                                    </tr>
                                </table><!---End Tble--->
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>&nbsp;</td>
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
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
