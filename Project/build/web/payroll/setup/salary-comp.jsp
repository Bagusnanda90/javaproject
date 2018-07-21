
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_COMPONENT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
            //boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
            //boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
%>
<%

            CtrlPayComponent ctrlPayComponent = new CtrlPayComponent(request);
            long oidPayComponent = FRMQueryString.requestLong(request, "pay_component_oid");
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            int iCommand = FRMQueryString.requestCommand(request);
            int startBenefit = FRMQueryString.requestInt(request, "startBenefit");

            int startDeduction = FRMQueryString.requestInt(request, "startDeduction");
            // untuk status benefit=1 dan deduction = 2
            int status = FRMQueryString.requestInt(request, "status");

            int iErrCode = FRMMessage.ERR_NONE;
            String msgString = "";
            ControlLine ctrLine = new ControlLine();
            System.out.println("iCommand = " + iCommand);
            iErrCode = ctrlPayComponent.action(iCommand, oidPayComponent);
            msgString = ctrlPayComponent.getMessage();
            FrmPayComponent frmPayComponent = ctrlPayComponent.getForm();
            PayComponent payComponent = ctrlPayComponent.getPayComponent();
            oidPayComponent = payComponent.getOID();

            /*variable declaration*/
            int recordToGet = 10;
            String whereClauseBenefit = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + "=" + PstPayComponent.TYPE_BENEFIT;
            String whereClauseDeduction = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + "=" + PstPayComponent.TYPE_DEDUCTION;
            String orderClause = " SORT_IDX ";
            Vector listBenefit = new Vector(1, 1);
            Vector listDeduction = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlPayComponent.action(iCommand, oidPayComponent);
            /* end switch*/

            /*count list All Language*/
            int vectSizeBenefit = PstPayComponent.getCount(whereClauseBenefit);
            int vectSizeDeduction = PstPayComponent.getCount(whereClauseDeduction);

            /*switch list Language*/
            if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                if (startBenefit < 0) {
                    startBenefit = 0;
                }
                if (startDeduction < 0) {
                    startDeduction = 0;
                }

                startBenefit = ctrlPayComponent.actionList(iCommand, startBenefit, vectSizeBenefit, recordToGet);
                startDeduction = ctrlPayComponent.actionList(iCommand, startDeduction, vectSizeDeduction, recordToGet);
            }
            /* end switch list*/

            //PayExecutive payExecutive = ctrLanguage.getLanguage();
            msgString = ctrlPayComponent.getMessage();

            /* get record to display */
            if (startBenefit < 0) {
                startBenefit = 0;
            }
            listBenefit = PstPayComponent.listPaySlipGroup(startBenefit, recordToGet, whereClauseBenefit, orderClause);

            if (startDeduction < 0) {
                startDeduction = 0;
            }
            listDeduction = PstPayComponent.listPaySlipGroup(startDeduction, recordToGet, whereClauseDeduction, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listBenefit.size() < 1 && startBenefit > 0) {
                if (vectSizeBenefit - recordToGet > recordToGet) {
                    startBenefit = startBenefit - recordToGet;
                } //go to Command.PREV
                else {
                    startBenefit = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                if (startBenefit < 0) {
                    startBenefit = 0;
                }
                listBenefit = PstPayComponent.listPaySlipGroup(startBenefit, recordToGet, whereClauseBenefit, orderClause);
            //listDeduction = PstPayComponent.list(startBenefit,recordToGet, whereClauseDeduction , orderClause);
            }

            if (listDeduction.size() < 1 && startDeduction > 0) {
                if (vectSizeDeduction - recordToGet > recordToGet) {
                    startDeduction = startDeduction - recordToGet;
                } //go to Command.PREV
                else {
                    startDeduction = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                if (startDeduction < 0) {
                    startDeduction = 0;
                }

                listDeduction = PstPayComponent.listPaySlipGroup(startDeduction, recordToGet, whereClauseDeduction, orderClause);
            //listDeduction = PstPayComponent.list(startBenefit,recordToGet, whereClauseDeduction , orderClause);
            }


%>

<%!
    public String drawList(int iCommand, FrmPayComponent frmObject, PayComponent objEntity, Vector objectClass, long payComponentId, int status, boolean privUpdate) {
        System.out.println("Status  " + status);
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");        
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Code", "");
        //ctrlist.addHeader("","");
        ctrlist.addHeader("Name", "");
        ctrlist.addHeader("Year Accumulate", "");
        ctrlist.addHeader("Default Period", "");
        ctrlist.addHeader("Take Home Pay", "");
        ctrlist.addHeader("Tax Calculation", "");
        ctrlist.addHeader("Tax Group", "");
        ctrlist.addHeader("Type", "");
        //update bby satrya 2013-01-24
        ctrlist.addHeader("Group", "");
           //update bby satrya 2013-02-07
        ctrlist.addHeader("Show Payslip", "");
        ctrlist.addHeader("Show in Reports", ""); 
        
        ctrlist.addHeader("Proporsional Calculate", ""); 
        ctrlist.addHeader("Decimal Format", ""); 
        ctrlist.addHeader("Mapping", ""); 
         
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        Vector rowx = new Vector(1, 1);
        Vector rowx1 = new Vector(1, 1);
        ctrlist.reset();
        int index = -1;


        //type
        Vector typeKey = new Vector();
        Vector typeValue = new Vector();
        typeKey.add(PstPayComponent.TYPE_BENEFIT + "");
        //typeKey.add(PstPayComponent.TYPE_DEDUCTION+"");
        //typeKey.add(PstPayComponent.TYPE_BLANK+"");
        typeValue.add(PstPayComponent.tpeComponent[PstPayComponent.TYPE_BENEFIT]);
        //typeValue.add(PstPayComponent.tpeComponent[PstPayComponent.TYPE_DEDUCTION]);
        //typeValue.add(PstPayComponent.tpeComponent[PstPayComponent.TYPE_BLANK]);



        //akumulasi Tahunan
        Vector akKey = new Vector();
        Vector akValue = new Vector();
        akKey.add(PstPayComponent.NO_AKUMULASI + "");
        akKey.add(PstPayComponent.YES_AKUMULASI + "");
        akValue.add(PstPayComponent.akTahunan[PstPayComponent.NO_AKUMULASI]);
        akValue.add(PstPayComponent.akTahunan[PstPayComponent.YES_AKUMULASI]);

        //Default Period
        Vector defKey = new Vector();
        Vector defValue = new Vector();
        defKey.add(PstPayComponent.MINGGUAN + "");
        defKey.add(PstPayComponent.BULANAN + "");
        defKey.add(PstPayComponent.TAHUNAN + "");
        defValue.add(PstPayComponent.defPeriod[PstPayComponent.MINGGUAN]);
        defValue.add(PstPayComponent.defPeriod[PstPayComponent.BULANAN]);
        defValue.add(PstPayComponent.defPeriod[PstPayComponent.TAHUNAN]);

        //Used In Formula
        Vector formKey = new Vector();
        Vector formValue = new Vector();
        formKey.add(PstPayComponent.NO_USED + "");
        formKey.add(PstPayComponent.YES_USED + "");
        formValue.add(PstPayComponent.usedForm[PstPayComponent.NO_USED]);
        formValue.add(PstPayComponent.usedForm[PstPayComponent.YES_USED]);

        //Tax Item
        Vector taxKey = new Vector();
        Vector taxValue = new Vector();
        taxKey.add(PstPayComponent.NO_TAX + "");
        taxKey.add(PstPayComponent.GAJI + "");
        taxKey.add(PstPayComponent.TUNJANGAN + "");
        //taxKey.add(PstPayComponent.BONUS_THR + "");
        taxKey.add(PstPayComponent.POTONGAN_GAJI+ "");
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.NO_TAX]);
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.GAJI]);
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.TUNJANGAN]);
       // taxValue.add(PstPayComponent.taxItem[PstPayComponent.BONUS_THR]);
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.POTONGAN_GAJI]);

        //Termasuk Tunjangan
        Vector tnjKey = new Vector();
        Vector tnjValue = new Vector();
        tnjKey.add(PstPayComponent.NO_TUNJANGAN + "");
        tnjKey.add(PstPayComponent.YES_TUNJANGAN + "");
        tnjValue.add(PstPayComponent.tunjangan[PstPayComponent.NO_TUNJANGAN]);
        tnjValue.add(PstPayComponent.tunjangan[PstPayComponent.YES_TUNJANGAN]);

        // update by kartika 20150802
        Vector taxRptKey = PstPayComponent.getTaxReportSelectInt(1);
        Vector taxRptVal =PstPayComponent.getTaxReportSelectString(1);
        
        //update by satrya 2013-01-24
        //Pay Group SLip
        
        Vector grkKey = new Vector();
        Vector grValue = new Vector();
        Vector listPaySlipGroup = PstPaySlipGroup.listAll();
        for (int r = 0; r < listPaySlipGroup.size(); r++) {
                    PaySlipGroup paySlipGroup = (PaySlipGroup) listPaySlipGroup.get(r);
                     grkKey.add(String.valueOf(paySlipGroup.getOID())); 
                    grValue.add(paySlipGroup.getGroupName());
        }  
        
                //update by satrya 2013-0207
        Vector spKey=new Vector();
        Vector spValue= new Vector();
        spKey.add(PstPayComponent.NO_SHOW_PAYSLIP + "");
        spKey.add(PstPayComponent.YES_SHOW_PAYSLIP + "");
        spKey.add(PstPayComponent.YES_NOT_EQUALS_0 + "");
        spValue.add(PstPayComponent.showPayslip[PstPayComponent.NO_SHOW_PAYSLIP]);
        spValue.add(PstPayComponent.showPayslip[PstPayComponent.YES_SHOW_PAYSLIP]);
        spValue.add(PstPayComponent.showPayslip[PstPayComponent.YES_NOT_EQUALS_0]);// 2015-01-12 | Hendra McHen
        
                //update by priska 2014-12-30
        Vector sirKey=new Vector();
        Vector sirValue= new Vector();
        sirKey.add(PstPayComponent.NO_SHOW_IN_REPORTS + "");
        sirKey.add(PstPayComponent.YES_SHOW_IN_REPORTS + "");
        sirKey.add(PstPayComponent.YES_NOT_EQUALS_0_IN_REPORTS + "");
        sirValue.add(PstPayComponent.showinReports[PstPayComponent.NO_SHOW_IN_REPORTS]);
        sirValue.add(PstPayComponent.showinReports[PstPayComponent.YES_SHOW_IN_REPORTS]);
        sirValue.add(PstPayComponent.showinReports[PstPayComponent.YES_NOT_EQUALS_0_IN_REPORTS]);
        
       //update by priska 2015-03-12
        Vector PcKey=new Vector();
        Vector PcValue= new Vector();
        PcKey.add(PstPayComponent.NO_PROPORSIONAL + "");
        PcKey.add(PstPayComponent.YES_PROPORSIONAL_BY_WORKDAYS + "");
        PcKey.add(PstPayComponent.YES_PROPORSIONAL_BY_COMENCING_AND_RESIGNED + "");
        
        PcValue.add(PstPayComponent.proporsional_calculate[PstPayComponent.NO_PROPORSIONAL]);
        PcValue.add(PstPayComponent.proporsional_calculate[PstPayComponent.YES_PROPORSIONAL_BY_WORKDAYS]);
        PcValue.add(PstPayComponent.proporsional_calculate[PstPayComponent.YES_PROPORSIONAL_BY_COMENCING_AND_RESIGNED]);
        /* Update by Hendra Putu | 2016-04-12*/
        Vector vDecimalFKey = new Vector();
        Vector vDecimalFVal = new Vector();
        vDecimalFKey.add(PstPayComponent.TANPA_PEMBULATAN +"");
        vDecimalFKey.add(PstPayComponent.SATU_ANGKA_BELAKANG_KOMA +"");
        vDecimalFKey.add(PstPayComponent.DUA_ANGKA_BELAKANG_KOMA +"");
        vDecimalFKey.add(PstPayComponent.TIGA_ANGKA_BELAKANG_KOMA +"");
        vDecimalFKey.add(PstPayComponent.SATUAN_TERDEKAT +"");
        vDecimalFKey.add(PstPayComponent.PULUHAN_TERDEKAT +"");
        vDecimalFKey.add(PstPayComponent.RATUSAN_TERDEKAT +"");
        vDecimalFKey.add(PstPayComponent.RIBUAN_TERDEKAT +"");
        
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.TANPA_PEMBULATAN]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.SATU_ANGKA_BELAKANG_KOMA]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.DUA_ANGKA_BELAKANG_KOMA]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.TIGA_ANGKA_BELAKANG_KOMA]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.SATUAN_TERDEKAT]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.PULUHAN_TERDEKAT]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.RATUSAN_TERDEKAT]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.RIBUAN_TERDEKAT]);
        for (int i = 0; i < objectClass.size(); i++) {
            PayComponent payComponent = (PayComponent) objectClass.get(i);
            rowx = new Vector();
            rowx1 = new Vector();
            if (payComponentId == payComponent.getOID()) {
                index = i;
            }
            if (index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)) {

                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_SORT_IDX] + "\" value=\"" + objEntity.getSortIdx() + "\" size=\"12\" class=\"elemenForm\">");
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_CODE] + "\" value=\"" + objEntity.getCompCode() + "\" size=\"12\" class=\"elemenForm\">");
                // untuk type
                //rowx.add("<input type=\"hiden\" name=\""+frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE] +"\" value=\""+PstPayComponent.TYPE_BENEFIT+"\" size=\"12\" class=\"elemenForm\">");
                //rowx.add(""+ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE],"formElemen",null, ""+objEntity.getCompType(), typeKey, typeValue) ) ;
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_NAME] + "\" value=\"" + objEntity.getCompName() + "\" size=\"40\" class=\"elemenForm\">");
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_YEAR_ACCUMLT], "formElemen", null, "" + objEntity.getYearAccumlt(), akKey, akValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PAY_PERIOD], "formElemen", null, "" + objEntity.getPayPeriod(), defKey, defValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_USED_IN_FORML], "formElemen", null, "" + objEntity.getUsedInForml(), formKey, formValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_TAX_ITEM], "formElemen", null, "" + objEntity.getTaxItem(), taxKey, taxValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_TAX_RPT_GROUP], "formElemen", null, "" + objEntity.getTaxRptGroup(), taxRptKey, taxRptVal));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE], "formElemen", null, "" + objEntity.getCompType(), typeKey, typeValue));
                //update by satrya 2013-01-24
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PAYSLIP_GROUP], "formElemen", null, "" + objEntity.getPayslipGroupId(), grkKey, grValue));
                //update by satry 2013-02-07
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_SHOW_PAYSLIP], "formElemen", null, "" + objEntity.getShowpayslip(), spKey, spValue));
                //update by priska 2014-12-30
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_SHOW_IN_REPORTS], "formElemen", null, "" + objEntity.getShowinreports(), sirKey, sirValue));
                //update by priska 2015-03-10
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PROPORSIONAL_CALCULATE], "formElemen", null, "" + objEntity.getProporsionalCalculate(), PcKey, PcValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_DECIMAL_FORMAT], "formElemen", null, "" + objEntity.getDecimalFormat(), vDecimalFKey, vDecimalFVal));
                rowx.add("<a href =\"../value_mapping/value_mapping_new.jsp?comp_code="+objEntity.getCompCode()+"\"> Mapping </a>");
                
            } else {
                //System.out.println("aku cek");
                if (privUpdate == true){
                    rowx.add("<a href=\"javascript:cmdEdit('" + String.valueOf(payComponent.getOID()) + "')\">" + payComponent.getSortIdx() + "</a>");
                } else {
                    rowx.add(""+payComponent.getSortIdx());
                }
                
                rowx.add("" + payComponent.getCompCode());
                //rowx.add(PstPayComponent.tpeComponent[payComponent.getCompType()]);
                rowx.add("" + payComponent.getCompName());
                rowx.add(PstPayComponent.akTahunan[payComponent.getYearAccumlt()]);
                rowx.add(PstPayComponent.defPeriod[payComponent.getPayPeriod()]);
                rowx.add(PstPayComponent.usedForm[payComponent.getUsedInForml()]);
                rowx.add(PstPayComponent.taxItem[payComponent.getTaxItem()]);
                rowx.add(PstPayComponent.TAX_RPT_GROUP[payComponent.getTaxRptGroup()]);// kartika 2015-09-02
                rowx.add(PstPayComponent.tpeComponent[payComponent.getCompType()]);
                //update by satrya 2013-01-24
                if(payComponent.getPaySlipGroupName()!=null){
                    rowx.add(payComponent.getPaySlipGroupName());
                }else{
                    rowx.add("");
                }
                //update by satrya 20130207
                rowx.add(PstPayComponent.showPayslip[payComponent.getShowpayslip()]);
                //update by satrya 2014-12-30
                rowx.add(PstPayComponent.showinReports[payComponent.getShowinreports()]);
                //update by priska 2015-03-12
                rowx.add(PstPayComponent.proporsional_calculate[payComponent.getProporsionalCalculate()]);
                rowx.add(PstPayComponent.valueStringDecimalFormat[payComponent.getDecimalFormat()]);
                if (privUpdate == true){
                    rowx.add("<a href =\"../value_mapping/value_mapping_new.jsp?comp_code="+payComponent.getCompCode()+"\"> Mapping </a>");
                } else {
                    rowx.add("");
                }
                
            }
            lstData.add(rowx);
        }
        rowx = new Vector();
        rowx1 = new Vector();
        if (status == 1 || status == 0) {
            if (iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0) || (objectClass.size() < 1)) {

                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_SORT_IDX] + "\" value=\"" + objEntity.getSortIdx() + "\" size=\"12\" class=\"elemenForm\">");
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_CODE] + "\" value=\"" + objEntity.getCompCode() + "\" size=\"12\" class=\"elemenForm\">");
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_NAME] + "\" value=\"" + objEntity.getCompName() + "\" size=\"40\" class=\"elemenForm\">");
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_YEAR_ACCUMLT], "formElemen", null, "" + objEntity.getYearAccumlt(), akKey, akValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PAY_PERIOD], "formElemen", null, "" + objEntity.getPayPeriod(), defKey, defValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_USED_IN_FORML], "formElemen", null, "" + objEntity.getUsedInForml(), formKey, formValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_TAX_ITEM], "formElemen", null, "" + objEntity.getTaxItem(), taxKey, taxValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_TAX_RPT_GROUP], "formElemen", null, "" + objEntity.getTaxRptGroup(), taxRptKey, taxRptVal));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE], "formElemen", null, "" + objEntity.getCompType(), typeKey, typeValue));
                 //update by satrya 2013-01-24
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PAYSLIP_GROUP], "formElemen", null, "" + objEntity.getPayslipGroupId(), grkKey, grValue));
                
                 //update by satrya 2013-02-07
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_SHOW_PAYSLIP], "formElemen", null, "" + objEntity.getShowpayslip(), spKey, spValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_SHOW_IN_REPORTS], "formElemen", null, "" + objEntity.getShowinreports(), sirKey, sirValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PROPORSIONAL_CALCULATE], "formElemen", null, "" + objEntity.getProporsionalCalculate(), PcKey, PcValue));
                rowx.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_DECIMAL_FORMAT], "formElemen", null, "" + objEntity.getDecimalFormat(), vDecimalFKey, vDecimalFVal));
                rowx.add("<a>A</a>");
            }
        }
        lstData.add(rowx);

        return ctrlist.draw();
    }

%>

<%!    public String drawListDeduction(int iCommand, FrmPayComponent frmObject, PayComponent objEntity, Vector objectClass, long payComponentId, int status, boolean privUpdate) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Code", "");
        //ctrlist.addHeader("","");
        ctrlist.addHeader("Name", "");
        ctrlist.addHeader("Year Accumulate", "");
        ctrlist.addHeader("Default Period", "");
        ctrlist.addHeader("Take Home Pay", "");
        ctrlist.addHeader("Tax Calculation", "");
        ctrlist.addHeader("Tax Group", "");
        ctrlist.addHeader("Type", "");
        //update bby satrya 2013-01-24
        ctrlist.addHeader("Group", "");
        //update bby satrya 2013-02-07
        ctrlist.addHeader("Show Payslip", "");
        ctrlist.addHeader("Show In Reports", "");
        ctrlist.addHeader("Proporsional Calculate", ""); 
        ctrlist.addHeader("Decimal Format", ""); 
        ctrlist.addHeader("Mapping", "");
         
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        Vector rowxD = new Vector(1, 1);
        Vector rowx1 = new Vector(1, 1);
        ctrlist.reset();
        int index = -1;


        //type
        Vector typeKey = new Vector();
        Vector typeValue = new Vector();
        typeKey.add(PstPayComponent.TYPE_DEDUCTION + "");
        typeValue.add(PstPayComponent.tpeComponent[PstPayComponent.TYPE_DEDUCTION]);



        //akumulasi Tahunan
        Vector akKey = new Vector();
        Vector akValue = new Vector();
        akKey.add(PstPayComponent.NO_AKUMULASI + "");
        akKey.add(PstPayComponent.YES_AKUMULASI + "");
        akValue.add(PstPayComponent.akTahunan[PstPayComponent.NO_AKUMULASI]);
        akValue.add(PstPayComponent.akTahunan[PstPayComponent.YES_AKUMULASI]);

        //Default Period
        Vector defKey = new Vector();
        Vector defValue = new Vector();
        defKey.add(PstPayComponent.MINGGUAN + "");
        defKey.add(PstPayComponent.BULANAN + "");
        defKey.add(PstPayComponent.TAHUNAN + "");
        defValue.add(PstPayComponent.defPeriod[PstPayComponent.MINGGUAN]);
        defValue.add(PstPayComponent.defPeriod[PstPayComponent.BULANAN]);
        defValue.add(PstPayComponent.defPeriod[PstPayComponent.TAHUNAN]);

        //Used In Formula
        Vector formKey = new Vector();
        Vector formValue = new Vector();
        formKey.add(PstPayComponent.NO_USED + "");
        formKey.add(PstPayComponent.YES_USED + "");
        formValue.add(PstPayComponent.usedForm[PstPayComponent.NO_USED]);
        formValue.add(PstPayComponent.usedForm[PstPayComponent.YES_USED]);

        //Tax Item
        Vector taxKey = new Vector();
        Vector taxValue = new Vector();
        taxKey.add(PstPayComponent.NO_TAX + "");
        taxKey.add(PstPayComponent.GAJI + "");
        taxKey.add(PstPayComponent.TUNJANGAN + "");
        taxKey.add(PstPayComponent.BONUS_THR + "");
        taxKey.add(PstPayComponent.POTONGAN_GAJI + "");
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.NO_TAX]);
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.GAJI]);
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.TUNJANGAN]);
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.BONUS_THR]);
        taxValue.add(PstPayComponent.taxItem[PstPayComponent.POTONGAN_GAJI]);

        //Termasuk Tunjangan
        Vector tnjKey = new Vector();
        Vector tnjValue = new Vector();
        tnjKey.add(PstPayComponent.NO_TUNJANGAN + "");
        tnjKey.add(PstPayComponent.YES_TUNJANGAN + "");
        tnjValue.add(PstPayComponent.tunjangan[PstPayComponent.NO_TUNJANGAN]);
        tnjValue.add(PstPayComponent.tunjangan[PstPayComponent.YES_TUNJANGAN]);

        
        // update by kartika 20150802
        Vector taxRptKey = PstPayComponent.getTaxReportSelectInt(-1);
        Vector taxRptVal =PstPayComponent.getTaxReportSelectString(-1);
        
        
          //update by satrya 2013-01-24
        //Pay Group SLip
        
        Vector grkKey = new Vector();
        Vector grValue = new Vector();
        Vector listPaySlipGroup = PstPaySlipGroup.listAll();
        for (int r = 0; r < listPaySlipGroup.size(); r++) {
                    PaySlipGroup paySlipGroup = (PaySlipGroup) listPaySlipGroup.get(r);
                     grkKey.add(String.valueOf(paySlipGroup.getOID())); 
                    grValue.add(paySlipGroup.getGroupName());
        }  
        //update by satrya 2013-0207
        Vector spKey=new Vector();
        Vector spValue= new Vector();
        spKey.add(PstPayComponent.NO_SHOW_PAYSLIP + "");
        spKey.add(PstPayComponent.YES_SHOW_PAYSLIP + "");
        spKey.add(PstPayComponent.YES_NOT_EQUALS_0 + "");
        spValue.add(PstPayComponent.showPayslip[PstPayComponent.NO_SHOW_PAYSLIP]);
        spValue.add(PstPayComponent.showPayslip[PstPayComponent.YES_SHOW_PAYSLIP]);
        spValue.add(PstPayComponent.showPayslip[PstPayComponent.YES_NOT_EQUALS_0]);// 2015-01-12 | Hendra McHen

        //update by priska 2014-12-30
        Vector sirKey=new Vector();
        Vector sirValue= new Vector();
        sirKey.add(PstPayComponent.NO_SHOW_IN_REPORTS + "");
        sirKey.add(PstPayComponent.YES_SHOW_IN_REPORTS + "");
        sirKey.add(PstPayComponent.YES_NOT_EQUALS_0_IN_REPORTS + "");
        sirValue.add(PstPayComponent.showinReports[PstPayComponent.NO_SHOW_IN_REPORTS]);
        sirValue.add(PstPayComponent.showinReports[PstPayComponent.YES_SHOW_IN_REPORTS]);
        sirValue.add(PstPayComponent.showinReports[PstPayComponent.YES_NOT_EQUALS_0_IN_REPORTS]);
        
         //update by priska 2015-03-12
        Vector PcKey=new Vector();
        Vector PcValue= new Vector();
        PcKey.add(PstPayComponent.NO_PROPORSIONAL + "");
        PcKey.add(PstPayComponent.YES_PROPORSIONAL_BY_WORKDAYS + "");
        PcKey.add(PstPayComponent.YES_PROPORSIONAL_BY_COMENCING_AND_RESIGNED + "");
        
        PcValue.add(PstPayComponent.proporsional_calculate[PstPayComponent.NO_PROPORSIONAL]);
        PcValue.add(PstPayComponent.proporsional_calculate[PstPayComponent.YES_PROPORSIONAL_BY_WORKDAYS]);
        PcValue.add(PstPayComponent.proporsional_calculate[PstPayComponent.YES_PROPORSIONAL_BY_COMENCING_AND_RESIGNED]);
        
        /* Update by Hendra Putu | 2016-04-12*/
        Vector vDecimalFKey = new Vector();
        Vector vDecimalFVal = new Vector();
        vDecimalFKey.add(PstPayComponent.TANPA_PEMBULATAN +"");
        vDecimalFKey.add(PstPayComponent.SATU_ANGKA_BELAKANG_KOMA +"");
        vDecimalFKey.add(PstPayComponent.DUA_ANGKA_BELAKANG_KOMA +"");
        vDecimalFKey.add(PstPayComponent.TIGA_ANGKA_BELAKANG_KOMA +"");
        vDecimalFKey.add(PstPayComponent.SATUAN_TERDEKAT +"");
        vDecimalFKey.add(PstPayComponent.PULUHAN_TERDEKAT +"");
        vDecimalFKey.add(PstPayComponent.RATUSAN_TERDEKAT +"");
        vDecimalFKey.add(PstPayComponent.RIBUAN_TERDEKAT +"");
        
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.TANPA_PEMBULATAN]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.SATU_ANGKA_BELAKANG_KOMA]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.DUA_ANGKA_BELAKANG_KOMA]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.TIGA_ANGKA_BELAKANG_KOMA]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.SATUAN_TERDEKAT]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.PULUHAN_TERDEKAT]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.RATUSAN_TERDEKAT]);
        vDecimalFVal.add(PstPayComponent.valueStringDecimalFormat[PstPayComponent.RIBUAN_TERDEKAT]);
        for (int i = 0; i < objectClass.size(); i++) {
            PayComponent payComponent = (PayComponent) objectClass.get(i);
            rowxD = new Vector();
            rowx1 = new Vector();
            if (payComponentId == payComponent.getOID()) {
                index = i;
            }
            if (index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)) {
                
                rowxD.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_SORT_IDX] + "\" value=\"" + objEntity.getSortIdx() + "\" size=\"12\" class=\"elemenForm\">");
                rowxD.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_CODE] + "\" value=\"" + objEntity.getCompCode() + "\" size=\"12\" class=\"elemenForm\">");
                // untuk type
                //rowx.add("<input type=\"hiden\" name=\""+frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE] +"\" value=\""+PstPayComponent.TYPE_BENEFIT+"\" size=\"12\" class=\"elemenForm\">");
                //rowx.add(""+ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE],"formElemen",null, ""+objEntity.getCompType(), typeKey, typeValue) ) ;
                rowxD.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_NAME] + "\" value=\"" + objEntity.getCompName() + "\" size=\"40\" class=\"elemenForm\">");
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_YEAR_ACCUMLT], "formElemen", null, "" + objEntity.getYearAccumlt(), akKey, akValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PAY_PERIOD], "formElemen", null, "" + objEntity.getPayPeriod(), defKey, defValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_USED_IN_FORML], "formElemen", null, "" + objEntity.getUsedInForml(), formKey, formValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_TAX_ITEM], "formElemen", null, "" + objEntity.getTaxItem(), taxKey, taxValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_TAX_RPT_GROUP], "formElemen", null, "" + objEntity.getTypeTunjangan(), taxRptKey, taxRptVal));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE], "formElemen", null, "" + objEntity.getCompType(), typeKey, typeValue));
                         //update by satrya 2013-01-24
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PAYSLIP_GROUP], "formElemen", null, "" + objEntity.getPayslipGroupId(), grkKey, grValue));
                   //update by satrya 2013-02-07
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_SHOW_PAYSLIP], "formElemen", null, "" + objEntity.getShowpayslip(), spKey, spValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_SHOW_IN_REPORTS], "formElemen", null, "" + objEntity.getShowinreports(), sirKey, sirValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PROPORSIONAL_CALCULATE], "formElemen", null, "" + objEntity.getProporsionalCalculate(), PcKey, PcValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_DECIMAL_FORMAT], "formElemen", null, "" + objEntity.getDecimalFormat(), vDecimalFKey, vDecimalFVal));
                rowxD.add("<a href =\"../value_mapping/value_mapping_new.jsp?comp_code="+objEntity.getCompCode()+"\"> Mapping </a>");

                
            } else {
                //System.out.println("aku cek");
                if (privUpdate == true){
                    rowxD.add("<a href=\"javascript:cmdEdit('" + String.valueOf(payComponent.getOID()) + "')\">" + payComponent.getSortIdx() + "</a>");
                } else {
                    rowxD.add(""+payComponent.getSortIdx());
                }
                
                rowxD.add("" + payComponent.getCompCode());
                //rowx.add(PstPayComponent.tpeComponent[payComponent.getCompType()]);
                rowxD.add("" + payComponent.getCompName());
                rowxD.add(PstPayComponent.akTahunan[payComponent.getYearAccumlt()]);
                rowxD.add(PstPayComponent.defPeriod[payComponent.getPayPeriod()]);
                rowxD.add(PstPayComponent.usedForm[payComponent.getUsedInForml()]);
                rowxD.add(PstPayComponent.taxItem[payComponent.getTaxItem()]);
                rowxD.add(PstPayComponent.TAX_RPT_GROUP[payComponent.getTaxRptGroup()< PstPayComponent.TAX_RPT_GROUP.length ? payComponent.getTaxRptGroup() :0]);
                rowxD.add(PstPayComponent.tpeComponent[payComponent.getCompType()]);
                if(payComponent.getPaySlipGroupName()!=null){
                rowxD.add(payComponent.getPaySlipGroupName());
                }else{
                rowxD.add("");
                }
                //update by satrya 2013-02-07
                rowxD.add(PstPayComponent.showPayslip[payComponent.getShowpayslip()]);
                //update by priska 2014-12-30
                rowxD.add(PstPayComponent.showinReports[payComponent.getShowinreports()]);
                rowxD.add(PstPayComponent.proporsional_calculate[payComponent.getProporsionalCalculate()]);
                rowxD.add(PstPayComponent.valueStringDecimalFormat[payComponent.getDecimalFormat()]);
                if (privUpdate == true){
                    rowxD.add("<a href =\"../value_mapping/value_mapping_new.jsp?comp_code="+payComponent.getCompCode()+"\"> Mapping </a>");
                } else {
                    rowxD.add("");
                }
                
            }
            lstData.add(rowxD);
        }
        rowxD = new Vector();
        rowx1 = new Vector();
        if (status == 2 || status == 0) {
            if (iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0) || (objectClass.size() < 1)) {
                rowxD.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_SORT_IDX] + "\" value=\"" + objEntity.getSortIdx() + "\" size=\"12\" class=\"elemenForm\">");
                rowxD.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_CODE] + "\" value=\"" + objEntity.getCompCode() + "\" size=\"12\" class=\"elemenForm\">");
                //rowx.add(""+ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE],"formElemen",null, ""+objEntity.getCompType()+"\" selected=\""+PstPayComponent.tpeComponent[PstPayComponent.TYPE_BENEFIT], typeKey, typeValue)) ;
                // unyuk type
                //rowx.add("<input type=\"hiden\" name=\""+frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE] +"\" value=\""+PstPayComponent.TYPE_BENEFIT+"\" size=\"12\" class=\"elemenForm\">");
                rowxD.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmPayComponent.FRM_FIELD_COMP_NAME] + "\" value=\"" + objEntity.getCompName() + "\" size=\"40\" class=\"elemenForm\">");
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_YEAR_ACCUMLT], "formElemen", null, "" + objEntity.getYearAccumlt(), akKey, akValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PAY_PERIOD], "formElemen", null, "" + objEntity.getPayPeriod(), defKey, defValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_USED_IN_FORML], "formElemen", null, "" + objEntity.getUsedInForml(), formKey, formValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_TAX_ITEM], "formElemen", null, "" + objEntity.getTaxItem(), taxKey, taxValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_TAX_RPT_GROUP], "formElemen", null, "" + objEntity.getTypeTunjangan(), taxRptKey, taxRptVal));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_COMP_TYPE], "formElemen", null, "" + objEntity.getCompType(), typeKey, typeValue));
                 //update by satrya 2013-01-24
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PAYSLIP_GROUP], "formElemen", null, "" + objEntity.getPayslipGroupId(), grkKey, grValue));
                //update by satrya 2013-02-07
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_SHOW_PAYSLIP], "formElemen", null, "" + objEntity.getShowpayslip(), spKey, spValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_SHOW_IN_REPORTS], "formElemen", null, "" + objEntity.getShowinreports(), sirKey, sirValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_PROPORSIONAL_CALCULATE], "formElemen", null, "" + objEntity.getProporsionalCalculate(), PcKey, PcValue));
                rowxD.add("" + ControlCombo.draw(FrmPayComponent.fieldNames[FrmPayComponent.FRM_FIELD_DECIMAL_FORMAT], "formElemen", null, "" + objEntity.getDecimalFormat(), vDecimalFKey, vDecimalFVal));
                rowxD.add("<a>A</a>");
            }
        }
        lstData.add(rowxD);

        return ctrlist.draw();
    }

%>
<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Setup - Salary Component</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
            
            function cmdEdit(oidPayComponent){
                document.frm_component.pay_component_oid.value=oidPayComponent;
                document.frm_component.status.value="0";
                document.frm_component.command.value="<%=Command.EDIT%>";
                document.frm_component.prev_command.value="<%=prevCommand%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            
            function cmdAdd(){
                document.frm_component.pay_component_oid.value="0";
                document.frm_component.status.value="1";
                document.frm_component.command.value="<%=Command.ADD%>";
                document.frm_component.prev_command.value="<%=prevCommand%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            
            function cmdAddD(){
                document.frm_component.pay_component_oid.value="0";
                document.frm_component.status.value="2";
                document.frm_component.command.value="<%=Command.ADD%>";
                document.frm_component.prev_command.value="<%=prevCommand%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            
            function cmdSave(){
                document.frm_component.command.value="<%=Command.SAVE%>";
                document.frm_component.status.value="3";
                document.frm_component.prev_command.value="<%=prevCommand%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            
            function cmdBack(){
                document.frm_component.command.value="<%=Command.BACK%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            
            function cmdListFirst(){
                document.frm_component.command.value="<%=Command.FIRST%>";
                document.frm_component.prev_command.value="<%=Command.FIRST%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            
            function cmdListPrev(){
                document.frm_component.command.value="<%=Command.PREV%>";
                document.frm_component.prev_command.value="<%=Command.PREV%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            function cmdListNext(){
                document.frm_component.command.value="<%=Command.NEXT%>";
                document.frm_component.prev_command.value="<%=Command.NEXT%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            
            function cmdListLast(){
                document.frm_component.command.value="<%=Command.LAST%>";
                document.frm_component.prev_command.value="<%=Command.LAST%>";
                document.frm_component.action="salary-comp.jsp";
                document.frm_component.submit();
            }
            
            function cmdConfirmDelete(oid){
                var x = confirm(" Are You Sure to Delete?");
                if(x){
                    document.frm_component.command.value="<%=Command.DELETE%>";
                    document.frm_component.action="salary-comp.jsp";
                    document.frm_component.submit();
                }
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
            
            function showObjectForMenu(){
                
            }
            
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
        </SCRIPT>
        <style type="text/css">
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
        </style>
        <!-- #EndEditable --> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr> 
                            <td width="100%"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"><div class="title_page">Salary Component</div></td>
                                    </tr>
                                    <tr> 
                                        <td height="20" style="color:#575757;"> <strong>Note:</strong> Code of Salary component may not consist of "IN_" / "in_" / "In_" / "iN_" sible, cause this is reserved characters for input</td>
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
                                                                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                                                                <form name="frm_component" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <input type="hidden" name="startBenefit" value="<%=startBenefit%>">
                                                                                    <input type="hidden" name="startDeduction" value="<%=startDeduction%>">
                                                                                    <input type="hidden" name="pay_component_oid" value="<%=oidPayComponent%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="status" value="<%=status%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                    <tr> <td>
                                                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">   
                                                                                                <tr align="left" valign="top"> 
                                                                                                    <td  align="left" height="14" valign="middle" colspan="2" class="listtitle"><div class="title_part">Benefit List</div> </td>
                                                                                                </tr> 
                                                                                            </table>
                                                                                    </td></tr>
                                                                                    <%
            try {
                // out.println("listBenefit"+listBenefit.size());
                if ((listBenefit == null || listBenefit.size() < 1) && (iCommand == Command.NONE)) {
                    iCommand = Command.ADD;
                }

                                                                                    %>
                                                                                    <tr> 
                                                                                    <td width="24%" colspan="2">
                                                                                    <%= drawList(iCommand, frmPayComponent, payComponent, listBenefit, oidPayComponent, status, privUpdate)%>
                                                                                    <table cellpadding="0" cellspacing="0" border="0">
                                                                                        <%
            } catch (Exception exc) {
            }
                                                                                        %>
                                                                                        
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td width="519">
                                                                                                
                                                                                                <table width="136%" height="68" border="0">
                                                                                                    
                                                                                                    <tr>
                                                                                                    
                                                                                                    <%
            int cmd = 0;
            if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
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
                                                                                                    <%=ctrLine.drawImageListLimit(cmd, vectSizeBenefit, startBenefit, recordToGet)%> 
                                                                                                    
                                                                                                    <%
            //	out.println("masukq"+Command.ADD) ;

            if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmPayComponent.errorSize() < 1)) {
                if (privAdd) {%>
                                                                                                    <tr> 
                                                                                                        <td width="150"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                                                                                                            <a href="javascript:cmdAdd()" class="command">Add New Benefit</a> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%}
            }%>
                                                                                                    <% if (status == 1 || status == 0) {
                if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPayComponent.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td >
                                                                                                            <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                                                                                                            <a href="javascript:cmdSave()" class="command">Save Benefit</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                            <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                                                                                                            <a href="javascript:cmdConfirmDelete()" class="command">Delete Benefit</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                            <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                                                                                        <a href="javascript:cmdBack()" class="command">Back to List Salary Component</a> </td>
                                                                                                    </tr>
                                                                                                    <%}
            }%>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td  align="left" height="14" valign="middle" colspan="2" class="listtitle"><div class="title_part">Deduction List</div> </td>
                                                                                        </tr>
                                                                                        <%
            try {
                // out.println("listBenefit"+listBenefit.size());
                if ((listDeduction == null || listDeduction.size() < 1) && (iCommand == Command.NONE)) {
                    iCommand = Command.ADD;
                }

                                                                                        %>
                                                                                        <tr> 
                                                                                            <td> 
                                                                                                <%= drawListDeduction(iCommand, frmPayComponent, payComponent, listDeduction, oidPayComponent, status, privUpdate)%>
                                                                                                
                                                                                            </td>
                                                                                            <%
            } catch (Exception exc) {
            }
                                                                                            %>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <table width="100%" border="0">
                                                                                        <tr>
                                                                                        <%
            cmd = 0;
            if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
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
                                                                                        <%=ctrLine.drawImageListLimit(cmd, vectSizeDeduction, startDeduction, recordToGet)%> 
                                                                                        
                                                                                        <%
            //	out.println("masukq"+Command.ADD) ;
            if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmPayComponent.errorSize() < 1)) {
                if (privAdd) {%>
                                                                                        <tr> 
                                                                                            <td width="150" colspan="3"><a href="javascript:cmdAddD()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                                                                                                <a href="javascript:cmdAddD()" class="command">Add 
                                                                                            New Deduction</a> </td>
                                                                                        </tr>
                                                                                        <%}
            }%>
                                                                                        <%if (status == 2 || status == 0) {
                if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPayComponent.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td>
                                                                                                <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                                                                                                <a href="javascript:cmdSave()" class="command">Save Deduction</a> &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
                                                                                                <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                                                                                                <a href="javascript:cmdConfirmDelete()" class="command">Delete Deduction</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
                                                                                                <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                                                                            <a href="javascript:cmdBack()" class="command">Back to List Salary Component</a> </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <%}
            }%>
                                                                                </form>
                                                                            <!-- #EndEditable --> </td>
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
            <tr>
                <td>&nbsp;</td>
            </tr>
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
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
