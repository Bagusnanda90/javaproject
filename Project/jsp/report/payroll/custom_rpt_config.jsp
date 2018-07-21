<%-- 
    Document   : custom_rpt_config
    Created on : Mar 26, 2015, 2:26:00 PM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.entity.payroll.PstPaySlip"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySlipComp"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptConfig"%>
<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptConfig"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlCustomRptConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptMain"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlCustomRptMain"%>
<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%!
    
    public String drawList(int dataGroup, long oidCustom) {
        String str = ""; 
        for(int i=0; i<PstCustomRptConfig.realTableList.length; i++){
            String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = "+dataGroup+" AND RPT_CONFIG_TABLE_GROUP = '"+PstCustomRptConfig.realTableList[i]+"' AND RPT_MAIN_ID = "+oidCustom+" ";
                                                                                      
            Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
            if (listField !=null && listField.size()>0){
                for(int j=0; j<listField.size(); j++){
                    CustomRptConfig dataField = (CustomRptConfig)listField.get(j);

                    str += "<tr>";
                        str += "<td id=\"td1\">"+dataField.getRptConfigFieldHeader()+"</td>";
                        str += "<td id=\"td1\"><button id=\"btn3\" onclick=\"javascript:cmdAsk('"+dataField.getOID()+"')\">&times;</button></td>";
                    str += "</tr>";
                }
            }
        }
        return str;
    }
    
    public String drawListSubTotal(int dataType, long oidCustom) {
        String str = ""; 
        
        String whereView = " RPT_CONFIG_DATA_TYPE = "+dataType+" AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oidCustom+" ";

        Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
        if (listField !=null && listField.size()>0){
            for(int j=0; j<listField.size(); j++){
                CustomRptConfig dataField = (CustomRptConfig)listField.get(j);
                String func = "";
                if (dataField.getRptConfigFieldType() == 1){
                    func = "<strong>COUNT</strong>";
                } else {
                    func = "<strong>SUM</strong>";
                }
                str += "<tr>";
                    str += "<td id=\"td1\">"+func+"("+dataField.getRptConfigFieldHeader()+") <strong>BY</strong> "+dataField.getRptConfigTableName()+"</td>";
                    str += "<td id=\"td1\"><button id=\"btn3\" onclick=\"javascript:cmdAsk('"+dataField.getOID()+"')\">&times;</button></td>";
                str += "</tr>";
            }
        }
        
        return str;
    }
%>
<!DOCTYPE html>
<%  

    /* Update by Hendra Putu | 20150326 */
    int iCommand = FRMQueryString.requestCommand(request);
    String commandCustom = FRMQueryString.requestString(request, "command_custom");
    long oidCustom = FRMQueryString.requestLong(request, "oid_custom");
    long oidCustomConfig = FRMQueryString.requestLong(request, "oid_custom_config");
    int tblIdx = FRMQueryString.requestInt(request, "tbl_idx");
    String tableName = FRMQueryString.requestString(request, "table_name");
    int dataType = FRMQueryString.requestInt(request, "data_type");
    int dataGroup = FRMQueryString.requestInt(request, "data_group");
    String[] fieldName = FRMQueryString.requestStringValues(request, "field_name");
    String[] fieldDataList = FRMQueryString.requestStringValues(request, "field_data_list");
    String[] fieldBenefit = FRMQueryString.requestStringValues(request, "field_benefit");
    String[] fieldDeduction = FRMQueryString.requestStringValues(request, "field_deduction");
    String[] combineComponent = FRMQueryString.requestStringValues(request, "combine_component");
    String inpCombine = FRMQueryString.requestString(request, "inp_combine");
    String inpCombineShow = FRMQueryString.requestString(request, "inp_combine_show");
    String inpOperator = FRMQueryString.requestString(request, "inp_operator");
    String inpAlias = FRMQueryString.requestString(request, "inp_alias");
    int showTable = FRMQueryString.requestInt(request, "show_table");
    int showTableDataList = FRMQueryString.requestInt(request, "show_table_data_list");
    int showTableComponent = FRMQueryString.requestInt(request, "show_table_component");
    int showGroup = FRMQueryString.requestInt(request, "show_group");
    String inpColour = FRMQueryString.requestString(request, "inp_colour");
    String inpFieldHeader = FRMQueryString.requestString(request, "inp_field_header");
    int inpShowIdx = FRMQueryString.requestInt(request, "inp_show_idx");
    /* add field Sub Total | 2015-05-29 */
    /* select_func, select_item, select_total_by */
    int selectFunc = FRMQueryString.requestInt(request, "select_func");
    String selectItem = FRMQueryString.requestString(request, "select_item");
    String selectTotalBy = FRMQueryString.requestString(request, "select_total_by");
    /*variable declaration  | */
    int iErrCode = FRMMessage.NONE;
    /* get Custom Report Title */
    CustomRptMain customRptMain = new CustomRptMain();
    if (oidCustom > 0){
        customRptMain = PstCustomRptMain.fetchExc(oidCustom);
    }
    
    CtrlCustomRptConfig ctrCustomRptConfig = new CtrlCustomRptConfig(request);
    FrmCustomRptConfig frmCustomRptConfig = new FrmCustomRptConfig();
    frmCustomRptConfig = ctrCustomRptConfig.getForm();
    CustomRptConfig customRptConfig = new CustomRptConfig();

    if (commandCustom != null && commandCustom.length() > 0){
        if (commandCustom.equals("save_field_data_list")){
            if (fieldName != null) {
                for (int i = 0; i < fieldName.length; ++i) {
                    customRptConfig.setRptConfigTableGroup(PstCustomRptConfig.realTableList[tblIdx]);
                    customRptConfig.setRptConfigTableName(PstCustomRptConfig.tableSystem[tblIdx][Integer.valueOf(fieldName[i])]);
                    customRptConfig.setRptConfigFieldName(PstCustomRptConfig.showFieldSystem[tblIdx][Integer.valueOf(fieldName[i])]);
                    customRptConfig.setRptConfigFieldHeader(PstCustomRptConfig.showFieldList[tblIdx][Integer.valueOf(fieldName[i])]);
                    customRptConfig.setRptConfigFieldType(PstCustomRptConfig.fieldTypeSystem[tblIdx][Integer.valueOf(fieldName[i])]);
                    customRptConfig.setRptConfigDataType(0);
                    customRptConfig.setRptConfigDataGroup(0);
                    customRptConfig.setRptMainId(oidCustom);
                    PstCustomRptConfig.insertExc(customRptConfig);
                }
            }
        }
        if (commandCustom.equals("save_field_other")){
            /*
            * 1 = WHERE, 2 = ORDER BY, 3 = GROUP BY
            */
            if (fieldDataList != null){
                for(int i=0; i<fieldDataList.length; i++){
                    CustomRptConfig dataCustom = PstCustomRptConfig.fetchExc(Long.valueOf(fieldDataList[i]));
                    customRptConfig.setRptConfigTableGroup(dataCustom.getRptConfigTableGroup());
                    customRptConfig.setRptConfigTableName(dataCustom.getRptConfigTableName());
                    customRptConfig.setRptConfigFieldName(dataCustom.getRptConfigFieldName());
                    customRptConfig.setRptConfigFieldHeader(dataCustom.getRptConfigFieldHeader());
                    customRptConfig.setRptConfigFieldType(dataCustom.getRptConfigFieldType());
                    customRptConfig.setRptConfigDataType(0);
                    customRptConfig.setRptConfigDataGroup(showGroup);
                    customRptConfig.setRptMainId(oidCustom);
                    PstCustomRptConfig.insertExc(customRptConfig);
                }
            }
            
        }
        if (commandCustom.equals("update_item")){
            CustomRptConfig dataUp = PstCustomRptConfig.fetchExc(oidCustomConfig);
            customRptConfig.setOID(oidCustomConfig);
            customRptConfig.setRptConfigTableGroup(dataUp.getRptConfigTableGroup());
            customRptConfig.setRptConfigDataGroup(dataUp.getRptConfigDataGroup());
            customRptConfig.setRptConfigDataType(dataUp.getRptConfigDataType());
            customRptConfig.setRptConfigFieldName(dataUp.getRptConfigFieldName());
            customRptConfig.setRptConfigTableName(dataUp.getRptConfigTableName());
            customRptConfig.setRptConfigFieldType(dataUp.getRptConfigFieldType());
            customRptConfig.setRptConfigTablePriority(dataUp.getRptConfigTablePriority());
            customRptConfig.setRptMainId(dataUp.getRptMainId());
            customRptConfig.setRptConfigFieldColour(inpColour);
            customRptConfig.setRptConfigFieldHeader(inpFieldHeader);
            customRptConfig.setRptConfigShowIdx(inpShowIdx);
            PstCustomRptConfig.updateExc(customRptConfig);
            inpColour = "";
            inpFieldHeader = "";
            inpShowIdx = 0;
        }
        if (commandCustom.equals("save_component")){
            if (fieldBenefit != null && fieldBenefit.length > 0){
                for(int i=0; i<fieldBenefit.length; i++){
                    PayComponent dataComp1 = PstPayComponent.fetchExc(Long.valueOf(fieldBenefit[i]));
                    customRptConfig.setRptConfigTableGroup(PstPaySlip.TBL_PAY_SLIP);
                    customRptConfig.setRptConfigTableName(PstPaySlipComp.TBL_PAY_SLIP_COMP);
                    customRptConfig.setRptConfigFieldName(dataComp1.getCompCode());
                    customRptConfig.setRptConfigFieldHeader(dataComp1.getCompName());
                    customRptConfig.setRptConfigFieldType(1);
                    customRptConfig.setRptConfigDataType(2);
                    customRptConfig.setRptConfigDataGroup(0);
                    customRptConfig.setRptMainId(oidCustom);
                    PstCustomRptConfig.insertExc(customRptConfig);
                }
                customRptConfig = new CustomRptConfig();
            }
            if (fieldDeduction != null && fieldDeduction.length > 0){
                for(int j=0; j<fieldDeduction.length; j++){
                    PayComponent dataComp2 = PstPayComponent.fetchExc(Long.valueOf(fieldDeduction[j]));
                    customRptConfig.setRptConfigTableGroup(PstPaySlip.TBL_PAY_SLIP);
                    customRptConfig.setRptConfigTableName(PstPaySlipComp.TBL_PAY_SLIP_COMP);
                    customRptConfig.setRptConfigFieldName(dataComp2.getCompCode());
                    customRptConfig.setRptConfigFieldHeader(dataComp2.getCompName());
                    customRptConfig.setRptConfigFieldType(1);
                    customRptConfig.setRptConfigDataType(2);
                    customRptConfig.setRptConfigDataGroup(0);
                    customRptConfig.setRptMainId(oidCustom);
                    PstCustomRptConfig.insertExc(customRptConfig);
                }
            }
        }
        if (commandCustom.equals("plus")){
            String combinePlus = "";
            String combineKoma1 = "";
            String opr = "";
            if(combineComponent !=null && combineComponent.length > 0){
                for(int i=0; i<combineComponent.length; i++){
                    combinePlus +=combineComponent[i];
                    combineKoma1 +=combineComponent[i];

                    opr +=",";
                    
                    if(i==combineComponent.length-1){
                        combinePlus +="";
                    } else {
                        combinePlus +="+";
                        combineKoma1 +=",";
                        opr +="+";
                    }
                }
            }
            if (inpCombine.length() > 0){
                inpCombine += ","+combineKoma1;
                inpCombineShow += "+"+combinePlus;
                inpOperator += "+"+opr;
            } else {
                inpCombine += combineKoma1;
                inpCombineShow += combinePlus;
                inpOperator += opr;
            }        
        }
        if (commandCustom.equals("minus")){
            String combineMinus = "";
            String combineKoma2 = "";
            String opr1 = "";
            if(combineComponent !=null && combineComponent.length > 0){
                for(int i=0; i<combineComponent.length; i++){
                    combineMinus +=combineComponent[i];
                    combineKoma2 += combineComponent[i];

                    opr1 += ",";

                    
                    if(i==combineComponent.length-1){
                        combineMinus +="";
                    } else {
                        combineMinus +="-";
                        combineKoma2 += ",";
                        opr1 +="-";
                    }
                }
            }
            if (inpCombine.length() > 0){
                inpCombine += ","+combineKoma2;
                inpCombineShow += "-"+combineMinus;
                inpOperator += "-"+opr1;
            } else {
                inpCombine += combineKoma2;
                inpCombineShow += combineMinus;
                inpOperator += opr1;
            }
        }
        if (commandCustom.equals("submit_combine")){
            customRptConfig.setRptConfigTableGroup("0"+inpOperator+"0");
            customRptConfig.setRptConfigTableName(PstPaySlipComp.TBL_PAY_SLIP_COMP);
            customRptConfig.setRptConfigFieldName(inpCombine);
            customRptConfig.setRptConfigFieldHeader(inpAlias);
            customRptConfig.setRptConfigFieldType(1);
            customRptConfig.setRptConfigDataType(1);
            customRptConfig.setRptConfigDataGroup(0);
            customRptConfig.setRptMainId(oidCustom);
            PstCustomRptConfig.insertExc(customRptConfig);
            inpCombine = "";
            inpAlias = "";
        }
        if (commandCustom.equals("save_subtotal")){
            /* select_func, select_item, select_total_by */
            /*
            RPT_CONFIG_DATA_TYPE = 3
            RPT_CONFIG_DATA_GROUP = 0
            RPT_CONFIG_TABLE_GROUP = COUNT / SUM
            RPT_CONFIG_TABLE_NAME = hr_section
            RPT_CONFIG_FIELD_NAME = SECTION
            RPT_CONFIG_FIELD_TYPE = 0
            RPT_CONFIG_FIELD_HEADER = RECORD

            Query = COUNT(hr_section.SECTION)
            View = COUNT(RECORD) BY SECTION
            */
            String func = "";
            if (selectFunc > 0){
                if (selectFunc == 1){
                    func = "COUNT";
                } else {
                    func = "SUM";
                }
            }
            String headerName = "";
            String whereComp = " COMP_CODE='"+selectItem+"' ";
            Vector listComponent = PstPayComponent.list(0, 1, whereComp, "");
            if (listComponent != null && listComponent.size() > 0){
                for (int lc=0; lc<listComponent.size(); lc++){
                    PayComponent pcomp = (PayComponent)listComponent.get(lc);
                    headerName = pcomp.getCompName();
                }
            } else {
                headerName = selectItem;
            }
            customRptConfig.setRptConfigTableGroup(func);
            customRptConfig.setRptConfigTableName(selectTotalBy);
            customRptConfig.setRptConfigFieldName(selectItem);
            customRptConfig.setRptConfigFieldHeader(headerName);
            customRptConfig.setRptConfigFieldType(selectFunc);
            customRptConfig.setRptConfigDataType(3);
            customRptConfig.setRptConfigDataGroup(0);
            customRptConfig.setRptMainId(oidCustom);
            PstCustomRptConfig.insertExc(customRptConfig);
            selectTotalBy = "";
            selectItem = "";
            selectFunc = 0;
        }
        commandCustom = "";
        customRptConfig = new CustomRptConfig();
        
    }
    /* code process save */
    iErrCode = ctrCustomRptConfig.action(iCommand, oidCustomConfig);
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Custom Report Config</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">
            
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
            
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #btn {
              background: #C7C7C7;
              border: 1px solid #BBBBBB;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 11px;
              padding: 3px 7px;
              cursor: pointer;
            }

            #btn:hover {
              color: #FFF;
              background: #B3B3B3;
              border: 1px solid #979797;
            }
            #btn1 {
              background: #f27979;
              border: 1px solid #d74e4e;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 2px 5px;
              cursor: pointer;
            }

            #btn1:hover {
              background: #d22a2a;
              border: 1px solid #c31b1b;
            }
            
            #btn2 {
              background: #F7F7F7;
              border: 1px solid #CCC;
              border-radius: 3px;
              font-family: Arial;
              color: #575757;
              font-size: 12px;
              padding: 3px 7px;
              cursor: pointer;
            }

            #btn2:hover {
              background: #CCC;
              color: #FFF;
              border: 1px solid #B7B7B7;
            }
            
            #btn3 {
              background: #E3E3E3;
              border: 1px solid #D3D3D3;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 12px;
              padding: 2px 5px;
              cursor: pointer;
            }

            #btn3:hover {
              background: #f27979;
              color: #FFF;
              border: 1px solid #d74e4e;
            }
            
            #btn4 {
              background: #E3E3E3;
              border: 1px solid #D3D3D3;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 12px;
              padding: 2px 5px;
              cursor: pointer;
            }

            #btn4:hover {
              background: #b3e0fc;
              color: #2085c5;
              border: 1px solid #8dbfde;
            }
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #f2dede;border: 1px solid #eed3d7; color: #cc4a64; padding: 12px 17px;border-radius: 3px;}
            #part {background-color: #E3E3E3; padding: 7px;margin: 3px;border-radius: 5px;border: 1px solid #D3D3D3;color: #474747;}
            #tableDisplay{
                background-color: #E3E3E3; padding: 7px;margin: 3px; box-shadow: 0px 7px 5px #CCC;
                border-radius: 5px;border: 1px solid #CCC;color: #474747;
            }
            th {background-color: #575757;color: #EEE; padding: 4px 7px; border-radius: 3px;}
            #li {padding-right: 21px;background-color: #CCC; color: #575757; padding-left: 3px;}
            #ol {margin-top: 2px;  background-color: #E7E7E7; }
            #tbl1 {border-collapse: collapse;margin-left: 12px;}
            #td1 {border: 1px solid #f7f7f7; padding: 2px 4px;}
            #td2 {border: 1px solid #f7f7f7; padding: 2px 4px; background-color: #DDD; color: #474747;}
            #inp {padding: 2px 3px; border: 1px solid #D3D3D3; color: #575757;}
            #box_colour {background-color: #E7E7E7; padding: 3px; margin: 3px;border-radius: 3px;border: 1px solid #D3D3D3;}
            #colour1 {background-color: #c4e5f9; border: 1px solid #85b5d3; padding: 3px 5px; margin: 5px 2px; border-radius: 3px; cursor: pointer;}
            #colour2 {background-color: #ebfaaf; border: 1px solid #b1c75b; padding: 3px 5px; margin: 5px 2px; border-radius: 3px; cursor: pointer;}
            #colour3 {background-color: #fcd2b6; border: 1px solid #d5966c; padding: 3px 5px; margin: 5px 2px; border-radius: 3px; cursor: pointer;}
            #colour4 {background-color: #f9c0f6; border: 1px solid #df74d9; padding: 3px 5px; margin: 5px 2px; border-radius: 3px; cursor: pointer;}
            #colour5 {background-color: #acdadb; border: 1px solid #6fb7b8; padding: 3px 5px; margin: 5px 2px; border-radius: 3px; cursor: pointer;}
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.action = "custom_rpt_config.jsp";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.oid_custom.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command.value="<%=Command.BACK%>";               
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.action="custom_rpt_main.jsp";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.submit();
            }

            function cmdEdit(oid, oidCustom) {
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.oid_custom_config.value = oid;
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.oid_custom.value = oidCustom;
                getCmd();
            }
            
            function cmdUpdate(oid, oidCustom) {
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command_custom.value = "update_item";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command.value = "";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.oid_custom_config.value = oid;
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.oid_custom.value = oidCustom;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            function cmdAsk(oid){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command.value="<%=Command.ASK%>";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.oid_custom_config.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.oid_custom_config.value = oid;
                getCmd();
            }
            function showFields(id){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_data_list.value = "0";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_component.value = "0";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.tbl_idx.value = id;
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table.value = "1";
                getCmd();
            }
            function addFieldDataList(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command_custom.value = "save_field_data_list";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table.value = "0";
                getCmd();
            }
            function closeForm(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table.value = "0";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_data_list.value = "0";
                getCmd();
            }
            function showFieldDataList(id){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table.value = "0";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_component.value = "0";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_group.value = id;
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_data_list.value = "1";
                getCmd();
            }
            function addFieldOther(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command_custom.value = "save_field_other";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_data_list.value = "0";
                getCmd();
            }
            function cmdQuery(){
                var comm = document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command.value;
                newWindow=window.open("custom_rpt_query.jsp?comm="+comm,"GenerateQuery", "height=457,width=457,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
                newWindow.focus();
            }
            function showComponent(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table.value = "0";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_data_list.value = "0";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_component.value = "1";
                getCmd();
            }
            function addComponent(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command_custom.value = "save_component";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_component.value = "0";
                getCmd();
            }
            function closeComponent(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table_component.value = "0";
                getCmd();
            }
            function cmdAddCombine(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table.value = "2";
                getCmd();
            }
            function cmdSubTotal(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table.value = "3";
                getCmd();
            }
            function addSubTotal(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command_custom.value = "save_subtotal";
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.show_table.value = "0";
                getCmd();
            }
            function cmdPlus(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command_custom.value = "plus";
                getCmd();
            }
            function cmdMinus(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command_custom.value = "minus";
                getCmd();
            }
            function cmdSubmit(){
                document.<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>.command_custom.value = "submit_combine";
                getCmd();
            }
        </script>
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
                    <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                        <tr> 
                            <td width="100%" colspan="3" valign="top" style="padding: 12px"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <button id="btn" onclick="javascript:cmdBack()">Back</button>&nbsp; <span id="menu_title"><%=customRptMain.getRptMainTitle()%></span> - <span id="menu_teks">Custom Report Configuration</span></div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                            <td style="background-color:#EEE;" valign="top">
                                                <form name="<%=FrmCustomRptConfig.FRM_NAME_CUSTOM_RPT_CONFIG%>" method="POST" action="">
                                                    <input type="hidden" name="command" value="<%=iCommand%>" />
                                                    <input type="hidden" name="command_custom" value="<%=commandCustom%>" />
                                                    <input type="hidden" name="oid_custom" value="<%=oidCustom%>" />
                                                    <input type="hidden" name="oid_custom_config" value="<%=oidCustomConfig%>" />
                                                    <input type="hidden" name="tbl_idx" value="<%=tblIdx%>" />
                                                    <input type="hidden" name="table_name" value="<%=tableName%>" />
                                                    <input type="hidden" name="data_type" value="<%=dataType%>" />
                                                    <input type="hidden" name="data_group" value="<%=dataGroup%>" />
                                                    <input type="hidden" name="show_table" value="<%=showTable%>" />
                                                    <input type="hidden" name="show_table_data_list" value="<%=showTableDataList%>" />
                                                    <input type="hidden" name="show_table_component" value="<%=showTableComponent%>" />
                                                    <input type="hidden" name="show_group" value="<%=showGroup%>" />
                                                    <input type="hidden" name="inp_colour" id="inp_colour" value="<%=inpColour%>" />
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        <table>
                                                            <%
                                                            if (iCommand == Command.EDIT){
                                                            %>
                                                            <tr><!-- Color Panel -->
                                                                <td colspan="1">
                                                                    <div id="box_colour">
                                                                        <table>
                                                                            <tr>
                                                                                <td>&nbsp;</td>
                                                                                <td><button id="colour1" onclick="putColour('c4e5f9')">&nbsp;&nbsp;&nbsp;</button></td>
                                                                                <td>&nbsp;</td>
                                                                                <td><button id="colour2" onclick="putColour('ebfaaf')">&nbsp;&nbsp;&nbsp;</button></td>
                                                                                <td>&nbsp;</td>
                                                                                <td><button id="colour3" onclick="putColour('fcd2b6')">&nbsp;&nbsp;&nbsp;</button></td>
                                                                                <td>&nbsp;</td>
                                                                                <td><button id="colour4" onclick="putColour('f9c0f6')">&nbsp;&nbsp;&nbsp;</button></td>
                                                                                <td>&nbsp;</td>
                                                                                <td><button id="colour5" onclick="putColour('acdadb')">&nbsp;&nbsp;&nbsp;</button></td>
                                                                            </tr>
                                                                        </table>
                                                                    </div>
                                                                </td>
                                                                <td colspan="3">&nbsp;</td>
                                                            </tr><!-- End Of Color Panel -->
                                                            <%
                                                            }
                                                            %>
                                                            <%
                                                            if (iCommand == Command.ASK){
                                                            %>
                                                            <tr><!-- Alert Delete -->
                                                                <td colspan="2">
                                                                    <div id="confirm">
                                                                        <strong>Are you sure to delete item? </strong>
                                                                        <button id="btn1" onclick="javascript:cmdDelete('<%=oidCustomConfig%>')">Yes</button>&nbsp;
                                                                        <button id="btn1" onclick="javascript:cmdBack()">No</button>
                                                                    </div>
                                                                </td>
                                                                <td colspan="2">&nbsp;</td>
                                                            </tr><!-- End Alert Delete -->
                                                            <% } %>
                                                            <tr>
                                                                <!-- Data List Side -->
                                                                <td border="1" valign="top">
                                                                    <table id="part">
                                                                        <tr>
                                                                            <th>Data List</th>
                                                                        </tr>
                                                                        <%
                                                                        /* Get table on Persisten */
                                                                        for(int i=0; i<PstCustomRptConfig.showTableList.length; i++){
                                                                          
                                                                        %>
                                                                        <tr>
                                                                            <td><button id="btn" onclick="showFields('<%=i%>')"><%=PstCustomRptConfig.showTableList[i]%></button></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <table id="tbl1">
                                                                                    <%
                                                                                    String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_CONFIG_TABLE_GROUP = '"+PstCustomRptConfig.realTableList[i]+"' AND RPT_MAIN_ID = "+oidCustom+" ";
                                                                                    String tdWarna = "";
                                                                                    Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                                                                                    if (listField !=null && listField.size()>0){
                                                                                        for(int j=0; j<listField.size(); j++){
                                                                                            CustomRptConfig dataField = (CustomRptConfig)listField.get(j);
                                                                                            if (dataField.getRptConfigFieldColour()!= null && dataField.getRptConfigFieldColour().length()>0){
                                                                                                tdWarna = "#"+dataField.getRptConfigFieldColour();
                                                                                            }
                                                                                            if (iCommand == Command.EDIT && oidCustomConfig == dataField.getOID()){
                                                                                                %>
                                                                                                <tr>
                                                                                                    <td id="td1"><button id="btn4" onclick="javascript:cmdUpdate('<%=dataField.getOID()%>','<%=oidCustom%>')">s</button></td>
                                                                                                    <td id="td1"><input type="text" name="inp_show_idx" value="<%=dataField.getRptConfigShowIdx()%>" size="1" /></td>
                                                                                                    <td id="td1" style="background-color: <%=tdWarna%>;"><input type="text" name="inp_field_header" value="<%=dataField.getRptConfigFieldHeader()%>" /></td>
                                                                                                    <td id="td1"><button id="btn3" onclick="javascript:cmdAsk('<%=dataField.getOID()%>')">&times;</button></td>
                                                                                                </tr>
                                                                                                <%
                                                                                            } else {
                                                                                            %>
                                                                                            <tr>
                                                                                                <td id="td1"><button id="btn4" onclick="javascript:cmdEdit('<%=dataField.getOID()%>','<%=oidCustom%>')">e</button></td>
                                                                                                <td id="td1"><%=dataField.getRptConfigShowIdx()%></td>
                                                                                                <td id="td1" style="background-color: <%=tdWarna%>;"><%=dataField.getRptConfigFieldHeader()%></td>
                                                                                                <td id="td1"><button id="btn3" onclick="javascript:cmdAsk('<%=dataField.getOID()%>')">&times;</button></td>
                                                                                            </tr>
                                                                                            <%
                                                                                            }
                                                                                            tdWarna = "";
                                                                                        }
                                                                                    }
                                                                                    %>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                        <%
                                                                        }
                                                                        %>
                                                                        <tr>
                                                                            <td><button id="btn" onclick="showComponent()">Salary Component</button></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <table id="tbl1">
                                                                                <%
                                                                                String whereComp = " RPT_CONFIG_DATA_TYPE = 2 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oidCustom+" ";
                                                                                String tdColor = "";              
                                                                                Vector listComp = PstCustomRptConfig.list(0, 0, whereComp, "");
                                                                                if (listComp !=null && listComp.size()>0){
                                                                                    for(int j=0; j<listComp.size(); j++){
                                                                                        CustomRptConfig dataField = (CustomRptConfig)listComp.get(j);

                                                                                        if (dataField.getRptConfigFieldColour()!= null && dataField.getRptConfigFieldColour().length()>0){
                                                                                            tdColor = "#"+dataField.getRptConfigFieldColour();
                                                                                        }
                                                                                        if (iCommand == Command.EDIT && oidCustomConfig == dataField.getOID()){
                                                                                            %>
                                                                                            <tr>
                                                                                                <td id="td1"><button id="btn4" onclick="javascript:cmdUpdate('<%=dataField.getOID()%>','<%=oidCustom%>')">s</button></td>
                                                                                                <td id="td1"><input type="text" name="inp_show_idx" value="<%=dataField.getRptConfigShowIdx()%>" size="1" /></td>
                                                                                                <td id="td1" style="background-color: <%=tdColor%>;"><input type="text" name="inp_field_header" value="<%=dataField.getRptConfigFieldHeader()%>" /></td>
                                                                                                <td id="td1"><button id="btn3" onclick="javascript:cmdAsk('<%=dataField.getOID()%>')">&times;</button></td>
                                                                                            </tr>
                                                                                            <%
                                                                                        } else {
                                                                                        %>
                                                                                        <tr>
                                                                                            <td id="td1"><button id="btn4" onclick="javascript:cmdEdit('<%=dataField.getOID()%>','<%=oidCustom%>')">e</button></td>
                                                                                            <td id="td1"><%=dataField.getRptConfigShowIdx()%></td>
                                                                                            <td id="td1" style="background-color: <%=tdColor%>;"><%=dataField.getRptConfigFieldHeader()%></td>
                                                                                            <td id="td1"><button id="btn3" onclick="javascript:cmdAsk('<%=dataField.getOID()%>')">&times;</button></td>
                                                                                        </tr>
                                                                                        <%
                                                                                        }
                                                                                        tdColor = "";
                                                                                    }
                                                                                }
                                                                                %>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                        
                                                                        
                                                                        <tr>
                                                                            <td><button id="btn" onclick="cmdAddCombine()">Add Data Combine</button></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <table id="tbl1">
                                                                                <%
                                                                                String whereCombine = " RPT_CONFIG_DATA_TYPE = 1 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oidCustom+" ";
                                                                                String tdColour = "";              
                                                                                Vector listCombine = PstCustomRptConfig.list(0, 0, whereCombine, "");
                                                                                if (listCombine !=null && listCombine.size()>0){
                                                                                    for(int j=0; j<listCombine.size(); j++){
                                                                                        CustomRptConfig dataField = (CustomRptConfig)listCombine.get(j);

                                                                                        if (dataField.getRptConfigFieldColour()!= null && dataField.getRptConfigFieldColour().length()>0){
                                                                                            tdColour = "#"+dataField.getRptConfigFieldColour();
                                                                                        }
                                                                                        if (iCommand == Command.EDIT && oidCustomConfig == dataField.getOID()){
                                                                                            %>
                                                                                            <tr>
                                                                                                <td id="td1"><button id="btn4" onclick="javascript:cmdUpdate('<%=dataField.getOID()%>','<%=oidCustom%>')">s</button></td>
                                                                                                <td id="td1"><input type="text" name="inp_show_idx" value="<%=dataField.getRptConfigShowIdx()%>" size="1" /></td>
                                                                                                <td id="td1" style="background-color: <%=tdColour%>;"><input type="text" name="inp_field_header" value="<%=dataField.getRptConfigFieldHeader()%>" /></td>
                                                                                                <td id="td1"><button id="btn3" onclick="javascript:cmdAsk('<%=dataField.getOID()%>')">&times;</button></td>
                                                                                            </tr>
                                                                                            <%
                                                                                        } else {
                                                                                        %>
                                                                                        <tr>
                                                                                            <td id="td1"><button id="btn4" onclick="javascript:cmdEdit('<%=dataField.getOID()%>','<%=oidCustom%>')">e</button></td>
                                                                                            <td id="td1"><%=dataField.getRptConfigShowIdx()%></td>
                                                                                            <td id="td1" style="background-color: <%=tdColour%>;"><%=dataField.getRptConfigFieldHeader()%></td>
                                                                                            <td id="td1"><button id="btn3" onclick="javascript:cmdAsk('<%=dataField.getOID()%>')">&times;</button></td>
                                                                                        </tr>
                                                                                        <%
                                                                                        }
                                                                                        tdColour = "";
                                                                                    }
                                                                                }
                                                                                %>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td><button id="btn" onclick="cmdSubTotal()">Add Sub Total</button></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <table id="tbl1">
                                                                                    <%=drawListSubTotal(3, oidCustom)%>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                                <!-- td end Data List side -->
                                                                <!-- Selection, Sort by, Group by --->
                                                                <td valign="top">
                                                                    <table id="part">
                                                                        <tr>
                                                                            <th>Selection / Filter</th>
                                                                        </tr>
                                                                        <tr>
                                                                            <td><button id="btn" onclick="showFieldDataList('1')">Add Field</button></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <table id="tbl1">
                                                                                    <%=drawList(1, oidCustom)%>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                                <td valign="top">
                                                                    <table id="part">
                                                                        <tr>
                                                                            <th>Sort By</th>
                                                                        </tr>
                                                                        <tr>
                                                                            <td><button id="btn"  onclick="showFieldDataList('2')">Add Field</button></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <table id="tbl1">
                                                                                    <%=drawList(2, oidCustom)%>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                                <td border="1" valign="top">
                                                                    <table id="part">
                                                                        <tr>
                                                                            <th>Group By</th>
                                                                        </tr>
                                                                        <tr>
                                                                            <td><button id="btn"  onclick="showFieldDataList('3')">Add Field</button></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <table id="tbl1">
                                                                                    <%=drawList(3, oidCustom)%>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <!-- TD Right Main -->
                                                    <td valign="top">
                                                        <%
                                                        if (showTable == 1){
                                                        %>
                                                        <table id="tableDisplay">
                                                            <tr>
                                                                <th colspan="2">Choose Field from Table <%=PstCustomRptConfig.showTableList[tblIdx]%></th>
                                                            </tr>
                                                            <tr>
                                                            <%
                                                            int inc = 1;
                                                            for(int i=0; i<PstCustomRptConfig.showFieldList[tblIdx].length; i++){
                                                                %>
                                                                
                                                                    <td>
                                                                        <input type="checkbox" name="field_name" value="<%=i%>" /><%=PstCustomRptConfig.showFieldList[tblIdx][i]%>
                                                                    </td>
                                                                
                                                                <%
                                                                if (inc == 2){
                                                                    inc = 1;
                                                                    %>
                                                                    </tr>
                                                                    <%
                                                                } else {
                                                                    inc++;
                                                                }
                                                            }
                                                            if(inc == 2){
                                                                %>
                                                                <td>&nbsp;</td></tr>
                                                                <%
                                                            }
                                                            %>
                                                            
                                                            
                                                            <tr>
                                                                <td colspan="2">
                                                                    <button id="btn2" onclick="addFieldDataList()">Add</button>
                                                                    <button id="btn2" onclick="closeForm()">Close</button>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <% } 
                                                        if (showTableDataList == 1){
                                                        %>
                                                        <!-- Selection / WHERE, Sort by, Group by -->
                                                        <table id="tableDisplay">
                                                            <tr>
                                                                <th colspan="2">Choose Field from Data List</th>
                                                            </tr>
                                                            <%
                                                            for(int k=0; k<PstCustomRptConfig.showTableList.length; k++){
                                                                String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_CONFIG_TABLE_GROUP = '"+PstCustomRptConfig.realTableList[k]+"' AND RPT_MAIN_ID = "+oidCustom+" ";
                                                                Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                                                                if (listField !=null && listField.size()>0){
                                                                    for(int l=0; l<listField.size(); l++){
                                                                        CustomRptConfig dataField = (CustomRptConfig)listField.get(l);
                                                                        %>
                                                                        <tr>
                                                                            <td id="td1" colspan="2"><input type="checkbox" name="field_data_list" value="<%=dataField.getOID()%>" /><%=dataField.getRptConfigFieldHeader()%></td>
                                                                        </tr>
                                                                        <%
                                                                    }
                                                                }
                                                            }
                                                            %>
                                                            
                                                            <tr>
                                                                <td colspan="2">
                                                                    <button id="btn2" onclick="addFieldOther()">Add</button>
                                                                    <button id="btn2" onclick="closeForm()">Close</button>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <% }
                                                        if (showTableComponent == 1){
                                                        %>
                                                        <table id="tableDisplay">
                                                            <tr>
                                                                <th colspan="2">Choose Salary Component</th>
                                                            </tr>
                                                            <tr>
                                                                <td id="td2">
                                                                    Benefit
                                                                </td>
                                                                <td id="td2">
                                                                    Deduction
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td valign="top">
                                                                    <table>
                                                                        <%
                                                                        Vector listCompBenefit = PstPayComponent.list(0, 0, "COMP_TYPE=1", "");
                                                                        if (listCompBenefit != null && listCompBenefit.size()>0){
                                                                            for(int i=0; i<listCompBenefit.size(); i++){
                                                                                PayComponent payBenefit = (PayComponent)listCompBenefit.get(i);
                                                                                
                                                                                %>
                                                                                <tr>
                                                                                    <td>
                                                                                        <input type="checkbox" name="field_benefit" value="<%=payBenefit.getOID()%>" /><%=payBenefit.getCompName()%>
                                                                                    </td>
                                                                                </tr>
                                                                                <%
                                                                            }
                                                                        }
                                                                        %>
                                                                        
                                                                    </table>
                                                                </td>
                                                                <td valign="top">
                                                                    <table>
                                                                        <%
                                                                        Vector listCompDeduction = PstPayComponent.list(0, 0, "COMP_TYPE=2", "");
                                                                        if (listCompDeduction != null && listCompDeduction.size()>0){
                                                                            for(int i=0; i<listCompDeduction.size(); i++){
                                                                                PayComponent payDeduction = (PayComponent)listCompDeduction.get(i);
                                                                                
                                                                                %>
                                                                                <tr>
                                                                                    <td>
                                                                                        <input type="checkbox" name="field_deduction" value="<%=payDeduction.getOID()%>" /><%=payDeduction.getCompName()%>
                                                                                    </td>
                                                                                </tr>
                                                                                <%
                                                                            }
                                                                        }
                                                                        %>
                                                                        
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="2">
                                                                    <button id="btn2" onclick="addComponent()">Add</button>
                                                                    <button id="btn2" onclick="closeComponent()">Close</button>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <% } %>
                                                        <%
                                                        if (showTable == 2){
                                                        %>
                                                        <table id="tableDisplay">
                                                            <tr>
                                                                <th colspan="2">Salary Component to Combine Data</th>
                                                            </tr>
                                                            <%
                                                            
                                                                String whereView = " RPT_CONFIG_DATA_TYPE = 2 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oidCustom+" ";
                                                                Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                                                                if (listField !=null && listField.size()>0){
                                                                    for(int l=0; l<listField.size(); l++){
                                                                        CustomRptConfig dataField = (CustomRptConfig)listField.get(l);
                                                                        %>
                                                                        <tr>
                                                                            <td colspan="2"><input type="checkbox" name="combine_component" value="<%=dataField.getRptConfigFieldName()%>" /><%=dataField.getRptConfigFieldHeader()%></td>
                                                                        </tr>
                                                                        <%
                                                                    }
                                                                }
                                                            
                                                            %>
                                                            
                                                            <tr>
                                                                <td colspan="2">
                                                                    <button id="btn2" onclick="cmdPlus()">+</button>
                                                                    <button id="btn2" onclick="cmdMinus()">-</button>
                                                                    <button id="btn2" onclick="cmdSubmit()">Submit</button>
                                                                    <button id="btn2" onclick="closeForm()">Close</button>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="2">
                                                                    <input type="hidden" name="inp_combine" value="<%=inpCombine%>" size="35" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="2">
                                                                    <input type="text" name="inp_combine_show" value="<%=inpCombineShow%>" size="35" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="2">
                                                                    <input type="hidden" name="inp_operator" value="<%=inpOperator%>" size="35" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="2">
                                                                    <input type="text" name="inp_alias" value="<%=inpAlias%>" placeholder="Alias" size="35" />
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <% } %>
                                                        <%
                                                        if (showTable == 3){
                                                        %>
                                                        <table id="tableDisplay">
                                                            <tr>
                                                                <th colspan="3">Sub Total</th>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <!--
                                                                    <select name="select_func">
                                                                        <option value="0">-select-</option>
                                                                        <option value="1">COUNT</option>
                                                                        <option value="2">SUM</option>
                                                                    </select>
                                                                    -->
                                                                    <input type="hidden" name="select_func" value="1" /><strong>COUNT</strong>
                                                                </td>
                                                                <td>
                                                                    <!--
                                                                    <select name="select_item">
                                                                        <option value="0">-select-</option>
                                                                        <option value="RECORD">Record</option>
                                                                        
                                                                        
                                                                    </select>
                                                                    -->
                                                                    <input type="hidden" name="select_item" value="RECORD" />(RECORD)
                                                                </td>
                                                                <td><strong>BY</strong> &nbsp;
                                                                    <select name="select_total_by">
                                                                        <option value="0">-select-</option>
                                                                        <%
                                                                        String whereView = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID = "+oidCustom+" ";
                                                                        Vector listField = PstCustomRptConfig.list(0, 0, whereView, "");
                                                                        if (listField != null && listField.size() > 0){
                                                                            for (int L=0; L<listField.size(); L++){
                                                                                CustomRptConfig itemField = (CustomRptConfig)listField.get(L);
                                                                                %>
                                                                                <option value="<%=itemField.getRptConfigFieldName()%>"><%=itemField.getRptConfigFieldHeader()%></option>
                                                                                <%
                                                                            }
                                                                        }
                                                                        %>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="3">
                                                                    <button id="btn2" onclick="addSubTotal()()">Submit</button>
                                                                    <button id="btn2" onclick="closeForm()">Close</button>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <% } %>
                                                    </td>
                                                </tr>
                                            </table>
                                                </form>
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
                    <%@include file="../../footer.jsp" %>
                </td>
                            
            </tr>
            <%} else {%>
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
                function putColour(colour){
                    document.getElementById("inp_colour").value = colour;
                }
    </script>          
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
