<%-- 
    Document   : kadiv_mapping
    Created on : Jun 14, 2014, 11:12:58 PM
    Author     : Satrya Ramayu
--%>

<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.mappingkadiv.MappingKadivDetail"%>
<%@page import="com.dimata.harisma.entity.masterdata.mappingkadiv.MappingKadivMain"%>
<%@page import="com.dimata.harisma.form.masterdata.mappingkadiv.CtrMappingKadivMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.mappingkadiv.FrmMappingKadivMain"%>
<%@page import="com.dimata.harisma.form.masterdata.mappingkadiv.FrmMappingKadivDetail"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.util.LogicParser"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_OUTLET, AppObjInfo.OBJ_MAPPING_KADIV);%> 
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

%>
<%
     CtrMappingKadivMain ctrMappingKadivMain = new CtrMappingKadivMain(request); 
    long oidEmployee = FRMQueryString.requestLong(request, FrmMappingKadivMain.fieldNames[FrmMappingKadivMain.FRM_FIELD_EMPLOYEE_ID]);
    int iCommand = FRMQueryString.requestCommand(request);
    int iErrCode = FRMMessage.ERR_NONE;
    String msgString = "";
    ControlLine ctrLine = new ControlLine();
    iErrCode = ctrMappingKadivMain.action(iCommand, oidEmployee);
    msgString = ctrMappingKadivMain.getMessage(); 
    //FrmMappingKadivMain frmMappingKadivMain = ctrMappingKadivMain.getForm();
    //MappingKadivMain mappingKadivMain = ctrMappingKadivMain.getMappingKadivMain(); 
    MappingKadivDetail mappingKadivDetail = ctrMappingKadivMain.getMappingKadivDetail();

    /*variable declaration*/
%>  
<html>
    <head>
<script src="../../javascripts/jquery.min-1.6.2.js" type="text/javascript"></script>
<script src="../../javascripts/chosen.jquery.js" type="text/javascript"></script>
        <title>Harisma - Employee Mapping Kadiv</title>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
            <!-- #EndEditable --> 
            <link rel="stylesheet" href="../../styles/main.css" type="text/css">
                    <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
                    <link rel="stylesheet" href="../../styles/tab.css" type="text/css"/>
                    <link rel="stylesheet" href="../../stylesheets/chosen.css" />
                    <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
                    <script type="text/javascript">

                        function changeHashOnLoad() {
                            window.location.href += "#";
                            setTimeout("changeHashAgain()", "50");
                        }

                        function changeHashAgain() {
                            window.location.href +="1";
                        }

                        var storedHash = window.location.hash;
                        window.setInterval(function () {
                            if (window.location.hash != storedHash) {
                                window.location.hash = storedHash;
                            }
                        }, 50);

                    </script>
                    <SCRIPT language=JavaScript>
                        function cmdUpdateDiv(){
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.action="kadiv_mapping.jsp";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.submit();
                        }
                        function cmdUpdateDep(){
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.action="kadiv_mapping.jsp";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.submit();
                        }
                        function cmdUpdatePos(){
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.action="kadiv_mapping.jsp";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.submit();
                        }
                        function cmdSave(){
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.command.value="<%=Command.SAVE%>";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.sourceOutlerSearch.value="kadiv_mapping";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.action="kadiv_mapping.jsp";
                            document.<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>.submit();
                        }
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
                           
                            <tr>
                                <td width="88%" valign="top" align="left">

                                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                                        <tr>
                                            <td width="100%">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="20">
                                                            <font color="#FF6600" face="Arial"><strong>
                                                                    Outlet &gt; Employee Outlet
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
                                                                                                <form name="<%=FrmMappingKadivMain.FRM_NAME_MAPPING_KADIV_MAIN%>" action="" method="post">
                                                                                                    <input type="hidden" name="command" value=""/>
                                                                                                    <input type="hidden" name="sourceOutlerSearch" value=""/>
                                                                                                    <input type="hidden" name="<%=FrmMappingKadivMain.fieldNames[FrmMappingKadivMain.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>"/>
                                                                                                    

                                                                                                    <table  width="100%">
                                                                                                        <tr>
                                                                                                            <td valign="top">


                                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                                    <tr>
                                                                                                                        <td nowrap colspan="2"><div align=left>Kadiv Mapping</div></td>
                                                                                                                    </tr>
                                                                                                                    <tr>
                                                                                                                        <td width="6%" align="right" nowrap><div align=left>Employee</div></td>
                                                                                                                        <td colspan="3" nowrap="nowrap">:
                                                                                                                                <%
                                                                                                                                    String fullName="";
                                                                                                                                    try{
                                                                                                                                        Employee employee = PstEmployee.fetchExc(oidEmployee);
                                                                                                                                        fullName = employee.getFullName();
                                                                                                                                    }catch(Exception exc){
                                                                                                                                    }
                                                                                                                                    out.print(fullName); 
                                                                                                                                %>  
                                                                                                                        </td>  
                                                                                                                    </tr>
                                                                                                                    <tr>
                                                                                                                        <td width="6%" align="right" nowrap><div align=left>Position</div></td>
                                                                                                                        <td colspan="3" nowrap="nowrap">:
                                                                                                                                <%
                                                                                                                                    String PositionName="";
                                                                                                                                    try{
                                                                                                                                        Employee employee = PstEmployee.fetchExc(oidEmployee);
                                                                                                                                       Position position = PstPosition.fetchExc(employee.getPositionId());
                                                                                                                                       PositionName = position.getPosition();
                                                                                                                                    }catch(Exception exc){
                                                                                                                                    }
                                                                                                                                    out.print(PositionName); 
                                                                                                                                %>  
                                                                                                                        </td>  
                                                                                                                    </tr>
                                                                                                                    <tr>
                                                                                                                        <td width="6%" align="right" nowrap><div align=left>Location</div></td>
                                                                                                                        <td colspan="3" nowrap="nowrap">:
                                                                                                                            <%
                                                                                                                            String attTagDept = "data-placeholder=\"\" style=\"width:350px;\"  tabindex=\"4\"   multiple";
                                                                                                                            Vector listLocationWithRegency = PstLocation.listLocationWithRegency();
                                                                                                                            Vector loc_key=new Vector();
                                                                                                                            Vector loc_value=new Vector();
                                                                                                                            if(listLocationWithRegency!=null && listLocationWithRegency.size()==2){
                                                                                                                                loc_value = (Vector)listLocationWithRegency.get(0); 
                                                                                                                                loc_key = (Vector)listLocationWithRegency.get(1);
                                                                                                                            }
                                                                                                                            %>
                                                                                                                                <%=ControlCombo.drawStringArraySelected(FrmMappingKadivDetail.fieldNames[FrmMappingKadivDetail.FRM_FIELD_LOCATION_ID], "chosen-select", null, mappingKadivDetail.getLocationIds(), loc_key,loc_value,null, attTagDept)%>    
                                                                                                                        </td>  
                                                                                                                    </tr>
                                                                                                                </table>

                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <td>
                                                                                                                <%if(iCommand==Command.NONE){%> 
                                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="137">
                                                                                                                    <tr>
                                                                                                                        <td width="16"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                                                                                        <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                                        <td width="94" class="command" nowrap><a href="javascript:cmdSave()">Save Mapping</a></td>
                                                                                                                    </tr>
                                                                                                                </table>
                                                                                                                <%}else{%>
                                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr> 
                                                                                                        <td colspan="2"> 
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidEmployee + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidEmployee + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidEmployee + "')"; 
                                                                                                                ctrLine.setBackCaption("");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("");
                                                                                                                ctrLine.setSaveCaption("Save Mapping Kadiv");
                                                                                                                ctrLine.setConfirmDelCaption("");
                                                                                                                ctrLine.setDeleteCaption("");
                                                                                                                ctrLine.setAddCaption("");
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
                                                                                                                if (iCommand == Command.SAVE) {
                                                                                                                    ctrLine.setSaveCaption("Save Mapping Kadiv");
                                                                                                                    ctrLine.setDeleteCaption("");
                                                                                                                    ctrLine.setBackCaption("");
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
                                    </table>

                                </td>
                            </tr>
                            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                            <tr>
                                <td valign="bottom">

                                    <%@include file="../../footer.jsp" %>
                                </td>

                            </tr>
                            <%} else {%>
                            <tr> 
                                <td colspan="2" height="20">
                                    <%@ include file = "../../main/footer.jsp" %>
                                </td>
                            </tr>
                            <%}%>
                            <script type="text/javascript">
        var config = {
            '.chosen-select'           : {},
            '.chosen-select-deselect'  : {allow_single_deselect:true},
            '.chosen-select-no-single' : {disable_search_threshold:10},
            '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chosen-select-width'     : {width:"95%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
</script>
                        </table>
                    </body>
                    <!-- #BeginEditable "script" --> 
                    <!-- #EndEditable --> <!-- #EndTemplate -->
                    </html>
