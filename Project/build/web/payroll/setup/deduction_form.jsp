<%-- 
    Document   : deduction_form
    Created on : Feb 19, 2015, 5:59:27 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.payroll.PstBenefitConfigDeduction"%>
<%@page import="com.dimata.harisma.entity.payroll.BenefitConfigDeduction"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.payroll.PstBenefitConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.BenefitConfig"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.form.payroll.FrmBenefitConfig"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.payroll.FrmBenefitConfigDeduction"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlBenefitConfigDeduction"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    String oidBenefit = FRMQueryString.requestString(request, "oid_benefit");
    long oidPosCom = FRMQueryString.requestLong(request, "hidden_pos_comp_id");
    String benefitConfigId = request.getParameter("benefit_id");
    String oid = request.getParameter("oid");
    int msg = 0;
    if (oidPosCom != 0 && oidPosCom > 0){
        oid = String.valueOf(oidPosCom);
        msg = 1;
    }
    /*variable declaration*/
    int iErrCode = FRMMessage.NONE;


    
    CtrlBenefitConfigDeduction ctrDeduction = new CtrlBenefitConfigDeduction(request);

    /*switch statement */
    iErrCode = ctrDeduction.action(iCommand, oidPosCom);
    /* end switch*/
    FrmBenefitConfigDeduction frmDeduction = ctrDeduction.getForm();



%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            body {
                margin: 0px;
                padding: 0px;
                background-color: #F5F5F5;
            }
            h1 {border-bottom: 1px solid #048CAD; padding-left: 21px;}
            td {padding: 3px 7px;}
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
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.action = "deduction_form.jsp";
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.service_charge_id.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.service_charge_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            function cmdRefresh(oid){
                document.<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>.oid_benefit.value = oid;
                self.opener.document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value = "<%=Command.EDIT%>";                 
                //self.close();
                self.opener.document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.submit();
            }
        </script>
    </head>
    <body>

        <h1>Deduction form</h1>
        <form name="<%=FrmBenefitConfigDeduction.FRM_NAME_DEDUCTION%>" method="POST" action="">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="oid_benefit" value="<%=benefitConfigId%>">
            <input type="hidden" name="<%=FrmBenefitConfigDeduction.fieldNames[FrmBenefitConfigDeduction.FRM_FIELD_BENEFIT_CONFIG_ID]%>" value="<%=benefitConfigId%>">
            <table style="margin: 14px 21px;">
                <tr>
                    <td>Deduction Percent</td>
                    <td>
                        <input type="text" name="<%=FrmBenefitConfigDeduction.fieldNames[FrmBenefitConfigDeduction.FRM_FIELD_DEDUCTION_PERCENT]%>" value="" size="5" />
                        %&nbsp;&times;&nbsp;
                        <%
                        /*
                        * get data total revenue [0]
                        * get data deduction result [1]
                        */
                        String oidBenefitCharge = "0";
                        if (oidBenefit.equals("")){
                            oidBenefitCharge = benefitConfigId;
                        } else {
                            oidBenefitCharge = oidBenefit;
                        }
                        
                        Vector reference_val = new Vector();
                        Vector reference_key = new Vector();
                        int index = 0;
                        reference_val.add("0");
                        reference_key.add("Total Revenue");
                        String whereClause = "BENEFIT_CONFIG_ID="+benefitConfigId;
                        Vector listDeduction = PstBenefitConfigDeduction.list(0, 0, whereClause, "");
                        if (listDeduction.size() > 0) {
                            for (int i = 0; i < listDeduction.size(); i++) {
                                BenefitConfigDeduction ded = (BenefitConfigDeduction)listDeduction.get(i);
                                reference_val.add(""+ded.getOID());
                                reference_key.add("Deduction index-"+(i+1));
                                index++;
                            }
                            index = index + 1;
                        } else {
                           index = 1; 
                        }
                        %>
                        <%= ControlCombo.draw(FrmBenefitConfigDeduction.fieldNames[FrmBenefitConfigDeduction.FRM_FIELD_DEDUCTION_REFERENCE], "formElemen", null, "0", reference_val, reference_key, "") %>
                    </td>
                </tr>
                
                
                <tr>
                    <td>Deduction Description</td>
                    <td><textarea name="<%=FrmBenefitConfigDeduction.fieldNames[FrmBenefitConfigDeduction.FRM_FIELD_DEDUCTION_DESCRIPTION]%>"></textarea></td>
                </tr>
                
                <tr>
                    <td>Deduction Index</td>
                    <td><input type="hidden" name="<%=FrmBenefitConfigDeduction.fieldNames[FrmBenefitConfigDeduction.FRM_FIELD_DEDUCTION_INDEX]%>" value="<%=index%>" size="9" /></td>
                </tr>
                <tr>
                    <td colspan="2" style="background-color: #EEE;">
                        <button id="btn" onclick="cmdSave()">Save</button>&nbsp;
                        <button id="btn" onclick="cmdRefresh('<%=oidBenefitCharge%>')">Refresh</button>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
