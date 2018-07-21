<%-- 
    Document   : salary_level_generate
    Created on : Mar 16, 2015, 3:49:27 PM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.harisma.form.payroll.FrmSalaryLevel"%>
<%@page import="com.dimata.qdep.db.DBException"%>
<%@page import="com.dimata.harisma.entity.payroll.PstSalaryLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.SalaryLevel"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.payroll.SalaryLevelDetail"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.form.payroll.FrmSalaryLevelDetail"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlSalaryLevelDetail"%>
<%@page import="com.dimata.harisma.entity.payroll.PstSalaryLevelDetail"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_LEVEL);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%!    public String drawList(int iCommand, FrmSalaryLevelDetail frmObject, SalaryLevelDetail objEntity, Vector objectClass, long salaryLevelDetailId, String comp_code, int status) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("<strong>No</strong>", "");
        ctrlist.addHeader("<strong>Code</strong>", "");
        ctrlist.addHeader("<strong>Name</strong>", "");
        ctrlist.addHeader("<strong>Formula(*)</strong>", "");
        ctrlist.addHeader("<strong>Pay Period</strong>", "");
        ctrlist.addHeader("<strong>Accumulate Period</strong>", "");
        ctrlist.addHeader("<strong>Take Home Pay</strong>", "");
        ctrlist.addHeader("<strong>Same Next Month</strong>", "");
            
        //ini untuk Periode Pembayaran
        Vector val_period = new Vector();
        Vector key_period = new Vector();
        for (int j = 0; j < PstSalaryLevelDetail.periodKey.length; j++) {
            val_period.add("" + j);
            key_period.add(PstSalaryLevelDetail.periodKey[j]);
        }
            
        //ini untuk Take Home Pay
        Vector val_take = new Vector();
        Vector key_take = new Vector();
        for (int m = 0; m < PstSalaryLevelDetail.takeKey.length; m++) {
            val_take.add("" + m);
            key_take.add(PstSalaryLevelDetail.takeKey[m]);
        }
            
        //ini untuk SAME NEXT MONTH
        Vector val_copy = new Vector();
        Vector key_copy = new Vector();
        for (int n = 0; n < PstSalaryLevelDetail.copyKey.length; n++) {
            val_copy.add("" + n);
            key_copy.add(PstSalaryLevelDetail.copyKey[n]);
        }
            
        Vector val_code = new Vector();
        Vector key_code = new Vector();
        String wherePayComp = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + "=" + PstPayComponent.TYPE_BENEFIT;
        Vector temp = PstPayComponent.list(0, 0, wherePayComp, "");
        key_code.add("");
        val_code.add("");
        for (int j = 0; j < temp.size(); j++) {
            PayComponent payComponent = (PayComponent) temp.get(j);
            val_code.add("" + payComponent.getOID());
            key_code.add(payComponent.getCompName());
        }
            
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        Vector rowx = new Vector(1, 1);
        ctrlist.reset();
        int index = -1;
            
        for (int i = 0; i < objectClass.size(); i++) {
            SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail) objectClass.get(i);
            rowx = new Vector();
            if (salaryLevelDetailId == salaryLevelDetail.getOID()) {
                index = i;
            }
                
            if (index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)) {
                // mengambil nama berdasarkan component code
                long compId = 0;
                compId = PstPayComponent.getIdName(salaryLevelDetail.getCompCode());
                //update by satrya 2014-04-02
                if (compId == 0) {
                    compId = PstPayComponent.getIdComonenName(salaryLevelDetail.getCompName());
                }
                PayComponent payComp = new PayComponent();
                String compName = "";
                String periodAkumulasi = "";
                int defPeriod = 0;
                try {
                    payComp = PstPayComponent.fetchExc(compId);
                    compName = payComp.getCompName();
                    //System.out.println("compname    "+compName);
                    defPeriod = payComp.getPayPeriod();
                    comp_code = payComp.getCompCode();
                } catch (Exception e) {
                }
                if (defPeriod == 0) {
                    periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.MINGGUAN];
                }
                if (defPeriod == 1) {
                    periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.BULANAN];
                }
                if (defPeriod == 2) {
                    periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.TAHUNAN];
                }
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_SORT_IDX] + "\" value=\"" + salaryLevelDetail.getSortIdx() + "\" class=\"formElement\">");
                rowx.add("<input type=\"text\" disabled name=\"levelcode\" value=\"" + comp_code + "\" class=\"formElement\"><input type=\"hidden\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMP_CODE] + "\" value=\"" + comp_code + "\" class=\"formElement\" >");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID], null, "" + salaryLevelDetail.getComponentId(), val_code, key_code, "onchange=\"javascript:cmdload(" + salaryLevelDetail.getComponentId() + ")\"", "formElement"));
                //rowx.add(""+ControlCombo.draw(FrmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID],"formElemen",null, ""+compName, val_code, key_code) );
                rowx.add("<textarea  cols =\"30\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_FORMULA] + "\" class=\"formElement\">" + salaryLevelDetail.getFormula() + "</textarea>");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_PAY_PERIOD], null, String.valueOf(salaryLevelDetail.getPayPeriod()), val_period, key_period, "", "formElement"));
                rowx.add("<input type=\"text\" name=\"akumulasi\" value=\"" + periodAkumulasi + "\" class=\"formElement\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_TAKE_HOME_PAY], null, String.valueOf(salaryLevelDetail.getTakeHomePay()), val_take, key_take, "", "formElement"));
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COPY_DATA], null, String.valueOf(salaryLevelDetail.getCopyData()), val_copy, key_copy, "", "formElement"));
                    
            } else {
                rowx.add("<a href=\"javascript:cmdEdit('" + String.valueOf(salaryLevelDetail.getOID()) + "')\">" + salaryLevelDetail.getSortIdx() + "</a>");
                rowx.add("<a href=\"javascript:cmdEdit('" + String.valueOf(salaryLevelDetail.getOID()) + "')\">" + salaryLevelDetail.getCompCode() + "</a>");
                // mengambil nama berdasarkan component code
                long compId = 0;
                compId = PstPayComponent.getIdName(salaryLevelDetail.getCompCode());
                //update by satrya 2014-04-02
                if (compId == 0) {
                    compId = PstPayComponent.getIdComonenName(salaryLevelDetail.getCompName());
                }
                PayComponent payComp = new PayComponent();
                String compName = "";
                int defPeriod = 0;
                try {
                    payComp = PstPayComponent.fetchExc(compId);
                    compName = payComp.getCompName();
                    defPeriod = payComp.getPayPeriod();
                } catch (Exception e) {
                }
                rowx.add("<a href=\"javascript:cmdEdit('" + String.valueOf(salaryLevelDetail.getOID()) + "')\">" + compName + "</a>");
                rowx.add(salaryLevelDetail.getFormula());
                rowx.add(String.valueOf(PstSalaryLevelDetail.periodKey[salaryLevelDetail.getPayPeriod()]));
                if (defPeriod == 0) {
                    rowx.add("" + PstPayComponent.defPeriod[PstPayComponent.MINGGUAN]);
                }
                if (defPeriod == 1) {
                    rowx.add("" + PstPayComponent.defPeriod[PstPayComponent.BULANAN]);
                }
                if (defPeriod == 2) {
                    rowx.add("" + PstPayComponent.defPeriod[PstPayComponent.TAHUNAN]);
                }
                rowx.add(String.valueOf(PstSalaryLevelDetail.takeKey[salaryLevelDetail.getTakeHomePay()]));
                rowx.add(String.valueOf(PstSalaryLevelDetail.copyKey[salaryLevelDetail.getCopyData()]));
            }
                
            lstData.add(rowx);
        }
        rowx = new Vector();
        if (status == 1 || status == 0) {
            if (iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)) {
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_SORT_IDX] + "\" value=\"" + objEntity.getSortIdx() + "\" class=\"formElement\">");
                rowx.add("<input type=\"text\" disabled name=\"levelcode\" value=\"\" class=\"formElement\"><input type=\"hidden\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMP_CODE] + "\" value=\"" + comp_code + "\" class=\"formElement\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID], null, String.valueOf(objEntity.getComponentId()), val_code, key_code, "onchange=\"javascript:cmdload(" + objEntity.getComponentId() + ")\"", "formElement"));
                rowx.add("<textarea  cols =\"30\" type=\"text\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_FORMULA] + "\" value=\"" + objEntity.getFormula() + "\" class=\"formElement\"></textarea>");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_PAY_PERIOD], null, String.valueOf(objEntity.getPayPeriod()), val_period, key_period, "", "formElement"));
                rowx.add("<input type=\"text\" name=\"akumulasi\" value=\"\" class=\"formElement\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_TAKE_HOME_PAY], null, String.valueOf(objEntity.getTakeHomePay()), val_take, key_take, "", "formElement"));
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COPY_DATA], null, String.valueOf(objEntity.getCopyData()), val_copy, key_copy, "", "formElement"));
            }
        }
        lstData.add(rowx);
        return ctrlist.draw();
            
    }
%>
<%!    public String drawListDeduction(int iCommand, FrmSalaryLevelDetail frmObject, SalaryLevelDetail objEntity, Vector objectClass, long salaryLevelDetailId, String comp_code, int status) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("<strong>No Urut</strong>", "");
        ctrlist.addHeader("<strong>Kode</strong>", "");
        ctrlist.addHeader("<strong>Nama</strong>", "");
        ctrlist.addHeader("<strong>Formula(*)</strong>", "");
        ctrlist.addHeader("<strong>Periode Pembayaran</strong>", "");
        ctrlist.addHeader("<strong>Periode Akumulasi</strong>", "");
        ctrlist.addHeader("<strong>Take Home Pay</strong>", "");
        ctrlist.addHeader("<strong>Same Next Month</strong>", "");
            
        //ini untuk Periode Pembayaran
        Vector val_period = new Vector();
        Vector key_period = new Vector();
        for (int j = 0; j < PstSalaryLevelDetail.periodKey.length; j++) {
            val_period.add("" + j);
            key_period.add(PstSalaryLevelDetail.periodKey[j]);
        }
            
        //ini untuk Take Home Pay
        Vector val_take = new Vector();
        Vector key_take = new Vector();
        for (int m = 0; m < PstSalaryLevelDetail.takeKey.length; m++) {
            val_take.add("" + m);
            key_take.add(PstSalaryLevelDetail.takeKey[m]);
        }
            
        //ini untuk SAME NEXT MONTH
        Vector val_copy = new Vector();
        Vector key_copy = new Vector();
        for (int n = 0; n < PstSalaryLevelDetail.copyKey.length; n++) {
            val_copy.add("" + n);
            key_copy.add(PstSalaryLevelDetail.copyKey[n]);
        }
            
        Vector val_code = new Vector();
        Vector key_code = new Vector();
        String wherePayComD = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + "=" + PstPayComponent.TYPE_DEDUCTION;
        Vector temp = PstPayComponent.list(0, 0, wherePayComD, "");
        key_code.add("");
        val_code.add("");
        for (int j = 0; j < temp.size(); j++) {
            PayComponent payComponent = (PayComponent) temp.get(j);
            val_code.add("" + payComponent.getOID());
            key_code.add(payComponent.getCompName());
        }
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        Vector rowx = new Vector(1, 1);
        ctrlist.reset();
        int index = -1;
            
        for (int i = 0; i < objectClass.size(); i++) {
            SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail) objectClass.get(i);
            rowx = new Vector();
            if (salaryLevelDetailId == salaryLevelDetail.getOID()) {
                index = i;
            }
                
            if (index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)) {
                // mengambil nama berdasarkan component code
                long compId = 0;
                compId = PstPayComponent.getIdName(salaryLevelDetail.getCompCode());
                PayComponent payComp = new PayComponent();
                String compName = "";
                String periodAkumulasi = "";
                int defPeriod = 0;
                try {
                    payComp = PstPayComponent.fetchExc(compId);
                    compName = payComp.getCompName();
                    defPeriod = payComp.getPayPeriod();
                    comp_code = payComp.getCompCode();
                } catch (Exception e) {
                }
                if (defPeriod == 0) {
                    periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.MINGGUAN];
                }
                if (defPeriod == 1) {
                    periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.BULANAN];
                }
                if (defPeriod == 2) {
                    periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.TAHUNAN];
                }
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_SORT_IDX] + "\" value=\"" + salaryLevelDetail.getSortIdx() + "\" class=\"formElement\">");
                rowx.add("<input type=\"text\" disabled name=\"levelcode\" value=\"" + comp_code + "\" class=\"formElement\"><input type=\"hidden\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMP_CODE] + "\" value=\"" + comp_code + "\" class=\"formElement\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID], null, String.valueOf(salaryLevelDetail.getComponentId()), val_code, key_code, "onchange=\"javascript:cmdload(" + salaryLevelDetail.getComponentId() + ")\"", "formElement"));
                //rowx.add(""+ControlCombo.draw(FrmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID],"formElemen",null, ""+compName, val_code, key_code) );
                rowx.add("<textarea  cols =\"30\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_FORMULA] + "\" class=\"formElement\">" + salaryLevelDetail.getFormula() + "</textarea>");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_PAY_PERIOD], null, String.valueOf(salaryLevelDetail.getPayPeriod()), val_period, key_period, "", "formElement"));
                rowx.add("<input type=\"text\" name=\"akumulasi\" value=\"" + periodAkumulasi + "\" class=\"formElement\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_TAKE_HOME_PAY], null, String.valueOf(salaryLevelDetail.getTakeHomePay()), val_take, key_take, "", "formElement"));
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COPY_DATA], null, String.valueOf(salaryLevelDetail.getCopyData()), val_copy, key_copy, "", "formElement"));
                    
            } else {
                rowx.add("" + salaryLevelDetail.getSortIdx());
                rowx.add(salaryLevelDetail.getCompCode());
                // mengambil nama berdasarkan component code
                long compId = 0;
                compId = PstPayComponent.getIdName(salaryLevelDetail.getCompCode());
                PayComponent payComp = new PayComponent();
                String compName = "";
                int defPeriod = 0;
                try {
                    payComp = PstPayComponent.fetchExc(compId);
                    compName = payComp.getCompName();
                    defPeriod = payComp.getPayPeriod();
                } catch (Exception e) {
                }
                    
                String strCompName = (compName == null || compName.length() < 1) ? "???" : compName;
                rowx.add("<a href=\"javascript:cmdEdit('" + String.valueOf(salaryLevelDetail.getOID()) + "')\">" + strCompName + "</a>");
                rowx.add(salaryLevelDetail.getFormula());
                rowx.add(String.valueOf(PstSalaryLevelDetail.periodKey[salaryLevelDetail.getPayPeriod()]));
                if (defPeriod == 0) {
                    rowx.add("" + PstPayComponent.defPeriod[PstPayComponent.MINGGUAN]);
                }
                if (defPeriod == 1) {
                    rowx.add("" + PstPayComponent.defPeriod[PstPayComponent.BULANAN]);
                }
                if (defPeriod == 2) {
                    rowx.add("" + PstPayComponent.defPeriod[PstPayComponent.TAHUNAN]);
                }
                rowx.add(String.valueOf(PstSalaryLevelDetail.takeKey[salaryLevelDetail.getTakeHomePay()]));
                rowx.add(String.valueOf(PstSalaryLevelDetail.copyKey[salaryLevelDetail.getCopyData()]));
            }
                
            lstData.add(rowx);
        }
        rowx = new Vector();
        if (status == 2 || status == 0) {
            if (iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)) {
                rowx.add("<input type=\"text\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_SORT_IDX] + "\" value=\"" + objEntity.getSortIdx() + "\" class=\"formElement\">");
                rowx.add("<input type=\"text\" disabled name=\"levelcode\" value=\"\" class=\"formElement\"><input type=\"hidden\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMP_CODE] + "\" value=\"" + comp_code + "\" class=\"formElement\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID], null, String.valueOf(objEntity.getComponentId()), val_code, key_code, "onchange=\"javascript:cmdload(" + objEntity.getComponentId() + ")\"", "formElement"));
                rowx.add("<textarea  cols =\"30\" type=\"text\" name=\"" + frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_FORMULA] + "\" value=\"" + objEntity.getFormula() + "\" class=\"formElement\"></textarea>");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_PAY_PERIOD], null, String.valueOf(objEntity.getPayPeriod()), val_period, key_period, "", "formElement"));
                rowx.add("<input type=\"text\" name=\"akumulasi\" value=\"\" class=\"formElement\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_TAKE_HOME_PAY], null, String.valueOf(objEntity.getTakeHomePay()), val_take, key_take, "", "formElement"));
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COPY_DATA], null, String.valueOf(objEntity.getCopyData()), val_copy, key_copy, "", "formElement"));
                    
            }
        }
        lstData.add(rowx);
        return ctrlist.draw();
            
    }
%>
<%!
    public void insertSalaryLevelDetail(Vector listData, SalaryLevelDetail insertData, String levelCode, int statusPensiun, int resigned){
        long oid = 0;
        insertData = new SalaryLevelDetail(); // Inisialisasi
        String compCodePensiun = "";
        String whereComp = " COMP_NAME LIKE 'PENSIUN FUND%' ";
        Vector listPayComponent = PstPayComponent.list(0, 1, whereComp, "");
        
        if (listPayComponent != null && listPayComponent.size() > 0 ){
            for(int c=0; c<listPayComponent.size(); c++){
                PayComponent comp = (PayComponent)listPayComponent.get(c);
                compCodePensiun = comp.getCompCode();
            }
        }
        String compCodeJht = "";
        whereComp = " COMP_NAME LIKE 'JHT%' ";
        listPayComponent = PstPayComponent.list(0, 1, whereComp, "");
        if (listPayComponent != null && listPayComponent.size() > 0 ){
            for(int c=0; c<listPayComponent.size(); c++){
                PayComponent comp = (PayComponent)listPayComponent.get(c);
                compCodeJht = comp.getCompCode();
            }
        }
        for(int i=0; i<listData.size(); i++){
            SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail)listData.get(i);
            insertData.setLevelCode(levelCode);
            insertData.setCompCode(salaryLevelDetail.getCompCode());
            if (salaryLevelDetail.getCompCode().equals(compCodePensiun) || salaryLevelDetail.getCompCode().equals(compCodeJht)){  
                if (salaryLevelDetail.getCompCode().equals(compCodePensiun)){
                    if (statusPensiun == 1 || resigned == 1){
                        insertData.setFormula("= 0");
                    } else {
                        insertData.setFormula(salaryLevelDetail.getFormula());
                    }
                }
                if (salaryLevelDetail.getCompCode().equals(compCodeJht)){
                    if (resigned == 1){
                        insertData.setFormula("= 0");
                    } else {
                        insertData.setFormula(salaryLevelDetail.getFormula());
                    }
                }
            } else {
                insertData.setFormula(salaryLevelDetail.getFormula());
            }
            insertData.setSortIdx(salaryLevelDetail.getSortIdx());
            insertData.setPayPeriod(salaryLevelDetail.getPayPeriod());
            insertData.setTakeHomePay(salaryLevelDetail.getTakeHomePay());
            insertData.setCopyData(salaryLevelDetail.getCopyData());
            insertData.setComponentId(salaryLevelDetail.getComponentId());
            try {
               oid = PstSalaryLevelDetail.insertExc(insertData);
            } catch (DBException dbe) {
               System.out.println(dbe.toString());
            }
        }
        listData = null;
    }

    public void insertSalaryLevel(String levelCode, String levelName, long levelAssign, String note){
        long oid = 0;
        SalaryLevel salaryLevel = new SalaryLevel();
        salaryLevel.setLevelCode(levelCode);
        salaryLevel.setLevelName(levelName);
        salaryLevel.setLevelAssign(levelAssign);
        salaryLevel.setSort_Idx(0);
        salaryLevel.setAmountIs(1);
        salaryLevel.setCur_Code("RP");
        salaryLevel.setSalaryLevelStatus(0);
        salaryLevel.setSalaryLevelNote(note);
        try {
            oid = PstSalaryLevel.insertExc(salaryLevel);
        } catch (DBException dbe) {
            System.out.println(dbe.toString());
        }
    }
%>
<%
    //menangkap variabel-variabel yang dikirim oleh halaman sebelumnya
    long employeeId = FRMQueryString.requestLong(request, "employee_oid"); //request.getParameter("oid");
    String salary_level_code = FRMQueryString.requestString(request, "salary_level_code");
    String salary_level_name = FRMQueryString.requestString(request, "salary_level_name");
    String salary_level_note = FRMQueryString.requestString(request, "salary_level_note");
    
    long empId = FRMQueryString.requestLong(request, "employee_id");
    String salary_level = FRMQueryString.requestString(request, "salary_level");
    String cur = request.getParameter("cur");
    String code_cur = request.getParameter("code_cur");
    String comp_code = request.getParameter("comp_code");
        
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int startDeduction = FRMQueryString.requestInt(request, "startDeduction");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidSalaryLevelDetail = FRMQueryString.requestLong(request, "hidden_salary_detail_id");
// untuk status benefit=1 dan deduction = 2
    int status = FRMQueryString.requestInt(request, "status");
    Date startDate = null;
    Date endDate = null;
    Employee emp = new Employee();    
    /* Get Employee */
    /*
    * mendapatkan id employee dari url dengan method GET
    * kemudian fetch data emplyee berdasarkan ID 
    */
    if (employeeId > 0){
        empId = employeeId;
   
        try {
            emp = PstEmployee.fetchExc(Long.valueOf(employeeId));
        } catch(Exception e) {
            System.out.println(e.toString());
        }

        /* Get Level Code */
        /*
        * Dapatkan salary level berdasarkan LEVEL_ASSIGN == hr_employee.LEVEL_ID
        * tampung nilai level code ke salary_level
        */
        String whereSL = "";
        Vector listSalaryLevel = new Vector(1,1);

        whereSL = "LEVEL_ASSIGN="+emp.getLevelId();
        listSalaryLevel = PstSalaryLevel.list(0, 1, whereSL, "");
        for(int i=0; i<listSalaryLevel.size(); i++){
            SalaryLevel salaryLevel = (SalaryLevel)listSalaryLevel.get(i);
            salary_level = salaryLevel.getLevelCode();
        }
        /* Get Salary Level Detail */
        //untuk benefit
        Vector recordBenefit = new Vector(1,1);
        Vector recordDeduction = new Vector(1,1);
        recordBenefit = null;
        recordDeduction = null;
        long oidInsert = 0;
        SalaryLevelDetail detailSalaryLevel = new SalaryLevelDetail();
        /*
        * Mengambil SalaryLevelDetail berdasarkan template LEVEL_ASSIGN
        * keyword : LEVEL_CODE, LEVEL_ASSIGN 
        */
        recordBenefit = PstSalaryLevelDetail.listComponentNoLimit(salary_level, PstPayComponent.TYPE_BENEFIT);
        recordDeduction = PstSalaryLevelDetail.listComponentNoLimit(salary_level, PstPayComponent.TYPE_DEDUCTION);
        /* ubah nilai salary_level (LEVEL_CODE) dengan nilai yg didapat dari input level code employee edit */
        salary_level = salary_level_code; 
        /* Data SalaryLevelDetail Template di duplicate dan dibuatkan baru berdasarkan nama LEVEL_CODE yg telah ditentukan */
        insertSalaryLevelDetail(recordBenefit, detailSalaryLevel, salary_level, emp.getStatusPensiunProgram(), emp.getResigned());
        insertSalaryLevelDetail(recordDeduction, detailSalaryLevel, salary_level, emp.getStatusPensiunProgram(), emp.getResigned());
        insertSalaryLevel(salary_level, salary_level_name, emp.getLevelId(), salary_level_note);
        
    } else {
        try {
            emp = PstEmployee.fetchExc(Long.valueOf(empId));
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }
    
    
    
    /* Get Salary Level */
    String whereSLevel = "LEVEL_CODE='"+salary_level+"'";
    Vector vsalaryLevel = PstSalaryLevel.list(0, 1, whereSLevel, "");
    String levelCode = "";
    String levelName = "";
    String levelNote = "";
    if (vsalaryLevel != null && vsalaryLevel.size() > 0){
        for(int i=0; i<vsalaryLevel.size(); i++){
            SalaryLevel sl = (SalaryLevel)vsalaryLevel.get(i);
            levelCode = sl.getLevelCode();
            levelName = sl.getLevelName();
            levelNote = sl.getSalaryLevelNote();
        }
    }
    
    /*variable declaration*/
    int recordToGet = 20;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
        
    CtrlSalaryLevelDetail ctrlSalaryLevelDetail = new CtrlSalaryLevelDetail(request);
    ControlLine ctrLine = new ControlLine();
    Vector listBenefit = new Vector(1, 1);
    Vector listDeduction = new Vector(1, 1);
        
        
    /*switch statement */
    iErrCode = ctrlSalaryLevelDetail.action(iCommand, oidSalaryLevelDetail);
    /* end switch*/
    FrmSalaryLevelDetail frmSalaryLevelDetail = ctrlSalaryLevelDetail.getForm();
        
    /*count list All kategori*/
    int vectSize = PstSalaryLevelDetail.getCountListComponent(salary_level, PstPayComponent.TYPE_BENEFIT);
    int vectSizeDeduction = PstSalaryLevelDetail.getCountListComponent(salary_level, PstPayComponent.TYPE_DEDUCTION);
        
    /*switch list kategori*/
    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlSalaryLevelDetail.actionList(iCommand, start, vectSize, recordToGet);
        startDeduction = ctrlSalaryLevelDetail.actionList(iCommand, startDeduction, vectSizeDeduction, recordToGet);
            
    }
    /* end switch list*/
        
    SalaryLevelDetail salaryLevelDetail = ctrlSalaryLevelDetail.getSalaryLevelDetail();
    msgString = ctrlSalaryLevelDetail.getMessage();
        
    /* get record to display */
//untuk benefit
    listBenefit = PstSalaryLevelDetail.listComponent(start, recordToGet, salary_level, PstPayComponent.TYPE_BENEFIT);
//untuk deduction
    listDeduction = PstSalaryLevelDetail.listComponent(startDeduction, recordToGet, salary_level, PstPayComponent.TYPE_DEDUCTION);
        
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listBenefit.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listBenefit = PstSalaryLevelDetail.listComponent(start, recordToGet, salary_level, PstPayComponent.TYPE_BENEFIT);
    }
        
    if (listDeduction.size() < 1 && startDeduction > 0) {
        if (vectSizeDeduction - recordToGet > recordToGet) {
            startDeduction = startDeduction - recordToGet;   //go to Command.PREV
        } else {
            startDeduction = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listDeduction = PstSalaryLevelDetail.listComponent(startDeduction, recordToGet, salary_level, PstPayComponent.TYPE_DEDUCTION);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Generate Salary Level</title>
        <style type="text/css">
            body {font-family: Arial, Helvetica, sans-serif; font-size: 12px; background-color: #F3F3F3;}
            h1 { margin: 21px; border-left: 2px solid #048CAD; color: #999; background-color: #EEE; font-weight: 100;padding: 12px 14px;}
            #content {padding: 21px; margin: 21px; border: 1px solid #ddd;}
            #content-sub {background-color: #F5F5F5; margin: 12px 0px; padding: 14px 0px;}
            #sub-title {font-size: 12px; color: #FF6600; border-left: 1px solid #048CAD; padding: 7px 9px; background-color: #EEE;}
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #ddd;}
            .title_tbl {font-weight: bold;background-color: #EEE; color: #575757;}
            a {text-decoration: none; font-weight: bold; color: #0066CC;}
            a:hover {color: red;}
            input { border: 1px solid #CCC; color: #575757; padding: 3px;}
            select {border: 1px solid #CCC; color: #575757;}
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
            #notif {border-left: 1px solid #048CAD; padding: 7px; font-size: 11px; color: #595959; background-color: #EEE;}
        </style>
        <script type="text/javascript">
            function cmdload(oid){
                
                // alert(oid);
		var oidbk = document.frmSalaryLevelDetail.<%=FrmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID]%>.value; 
		//alert(oidbk);
                
                if(oid==null)
                    return;
                if(oidbk!=null){
                <%
                Vector temp = PstPayComponent.listAll();
                if (temp != null) {
                    for (int i = 0; i < temp.size(); i++) {
                        PayComponent paycomponent = (PayComponent) temp.get(i);
                %>
                                if(oidbk=="<%=paycomponent.getOID()%>"){
                                    document.frmSalaryLevelDetail.levelcode.value="<%=paycomponent.getCompCode()%>";
                                    document.frmSalaryLevelDetail.comp_code.value="<%=paycomponent.getCompCode()%>"; 
                                    document.frmSalaryLevelDetail.akumulasi.value="<%=PstPayComponent.defPeriod[paycomponent.getPayPeriod()]%>";
                                    document.frmSalaryLevelDetail.<%=FrmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMP_CODE]%>.value  = "<%=paycomponent.getCompCode()%>";
                                }
                                
                <%  }
                }%>
                                 
                }
            } 
                         
                function cmdAdd(){
                    document.frmSalaryLevelDetail.hidden_salary_detail_id.value="0"; 
                    document.frmSalaryLevelDetail.status.value="1";
                    document.frmSalaryLevelDetail.command.value="<%=Command.ADD%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }
                function cmdState(oid){
                    document.frmSalaryLevelDetail.hidden_salary_detail_id.value=oid; 
                    document.frmSalaryLevelDetail.command.value="<%=Command.NONE%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                } 

                function cmdAsk(oidSalaryLevelDetail){
                    document.frmSalaryLevelDetail.hidden_salary_detail_id.value=oidSalaryLevelDetail; 
                    document.frmSalaryLevelDetail.command.value="<%=Command.ASK%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                } 

                function cmdConfirmDelete(oid){
                    var x = confirm(" Are You Sure to Delete?");
                    if(x){
                        document.frmSalaryLevelDetail.command.value="<%=Command.DELETE%>";
                        document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                        document.frmSalaryLevelDetail.submit();
                    }
                }

                function cmdSave(){
                    document.frmSalaryLevelDetail.command.value="<%=Command.SAVE%>"; 
                    document.frmSalaryLevelDetail.status.value="3";
                    document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdEdit(oidSalaryLevelDetail){
                    document.frmSalaryLevelDetail.hidden_salary_detail_id.value=oidSalaryLevelDetail; 
                    document.frmSalaryLevelDetail.status.value="0";
                    document.frmSalaryLevelDetail.command.value="<%=Command.EDIT%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdCancel(oidSalaryLevelDetail){
                    document.frmSalaryLevelDetail.hidden_salary_detail_id.value=oidSalaryLevelDetail; 
                    document.frmSalaryLevelDetail.command.value="<%=Command.EDIT%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdBack(){
                    document.frmSalaryLevelDetail.command.value="<%=Command.BACK%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdBackSal(oid){
                    document.frmSalaryLevelDetail.command.value="<%=Command.BACK%>";
                    document.frmSalaryLevelDetail.employee_oid.value=oid;
                    document.frmSalaryLevelDetail.action="employee_edit.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdListFirst(){
                    document.frmSalaryLevelDetail.command.value="<%=Command.FIRST%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=Command.FIRST%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdListPrev(){
                    document.frmSalaryLevelDetail.command.value="<%=Command.PREV%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=Command.PREV%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdListNext(){
                    document.frmSalaryLevelDetail.command.value="<%=Command.NEXT%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=Command.NEXT%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdListLast(){
                    document.frmSalaryLevelDetail.command.value="<%=Command.LAST%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=Command.LAST%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
                }

                function cmdAddD(){
                    document.frmSalaryLevelDetail.hidden_salary_detail_id.value="0";
                    document.frmSalaryLevelDetail.status.value="2";
                    document.frmSalaryLevelDetail.command.value="<%=Command.ADD%>";
                    document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
                    document.frmSalaryLevelDetail.action="salary_level_generate.jsp";
                    document.frmSalaryLevelDetail.submit();
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
                    function balik(oid){

                       document.frmSalaryLevelDetail.employee_oid.value=oid;
                       document.frmSalaryLevelDetail.command.value="<%=Command.EDIT%>";
                       document.frmSalaryLevelDetail.prev_command.value="<%=Command.EDIT%>";
                       document.frmSalaryLevelDetail.action="employee_edit.jsp";
                       document.frmSalaryLevelDetail.submit();
                    }
        </script>
    </head>
    <body>
        <h1>Salary Component : <%=emp.getEmployeeNum()+" | "+emp.getFullName()%></h1>
        <div id="content">
            <form name="frmSalaryLevelDetail" method ="post" action=""> 
            <div id="sub-title">Salary Level</div>
            <div id="content-sub">
                <table>
                    <tr>
                        <td>Level Code</td>
                        <td><input type="text" name="lc" disabled="disabled" value="<%=levelCode%>" /></td>
                    </tr>
                    <tr>
                        <td>Level Name</td>
                        <td><input type="text" name="ln" value="<%=levelName%>" disabled="disabled" size="50" /></td>
                    </tr>
                    <tr>
                        <td>Note</td>
                        <td><input type="text" name="ln" value="<%=levelNote%>" disabled="disabled" size="70" /></td>
                    </tr>
                </table>
            </div>
            <div id="sub-title">Benefit List</div>
            <div id="content-sub">
                
                    <input type="hidden" name="command" value="<%=iCommand%>"> 
                    <input type="hidden" name="vectSize" value="<%=vectSize%>"> 
                    <input type="hidden" name="start" value="<%=start%>"> 
                    <input type="hidden" name="startDeduction" value="<%=startDeduction%>"> 
                    <input type="hidden" name="<%=frmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_LEVEL_CODE]%>" value="<%=salary_level%>"> 
                    <input type="hidden" name="prev_command" value="<%=prevCommand%>"> 
                    <input type="hidden" name="salary_level" value="<%=salary_level%>">
                    <input type="hidden" name="employee_id" value="<%=empId%>">
                    <input type="hidden" name="employee_oid" value="">
                    <input type="hidden" name="cur" value="<%=cur%>"> 
                    <input type="hidden" name="comp_code" value="<%=comp_code%>"> 
                                            
                    <input type="hidden" name="code_cur" value="<%=code_cur%>"> 
                    <input type="hidden" name="hidden_salary_detail_id" value="<%=oidSalaryLevelDetail%>"> 
                    <input type="hidden" name="status" value="<%=status%>">

                <div>
                    <%= drawList(iCommand, frmSalaryLevelDetail, salaryLevelDetail,listBenefit,oidSalaryLevelDetail,comp_code,status)%>
                </div>
                <div> 
                    <%
                        int cmd = 0;
                        if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                            cmd = iCommand;
                        } else {
                            if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                cmd = Command.FIRST;
                            } else {
                                if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                                    cmd = PstSalaryLevelDetail.findLimitCommand(start, recordToGet, vectSize);
                                } else {
                                    cmd = prevCommand;
                                }
                            }
                        }

                    %>
                    <% ctrLine.setLocationImg(approot + "/images");
                        ctrLine.initDefault();
                    %>
                    <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                </div>
                <div>
                    <%
                    if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmSalaryLevelDetail.errorSize()<1)){
                    if(privAdd){%>
                    <div>
                        <img src="<%=approot%>/images/spacer.gif" width="1" height="1">
                        <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)">
                            <img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data">
                        </a>
                        <a href="javascript:cmdAdd()" class="command">Add New Benefit</a> 
                    </div>
                        <%}
                    }%>
                    
                        <% if(status==1||status==0){
                                          if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmSalaryLevelDetail.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                        <div>
                            <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                            <a href="javascript:cmdSave()" class="command">Save Benefit</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                            <a href="javascript:cmdConfirmDelete()" class="command">Delete Benefit</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                            <a href="javascript:cmdBack()" class="command">Back to List Salary Component</a> 
                        </div>
                            <%}
                                                                                      
                    }%>
                </div>
            </div>
            <div id="sub-title">Deduction List</div>
            <div id="content-sub">
                
                <div>
                    <%= drawListDeduction(iCommand,frmSalaryLevelDetail, salaryLevelDetail,listDeduction,oidSalaryLevelDetail,comp_code,status)%>
                </div>
                
                
                    <% 
                    cmd = 0;
                    if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
                    (iCommand == Command.NEXT || iCommand == Command.LAST))
                    cmd =iCommand; 
                    else{
                    if(iCommand == Command.NONE || prevCommand == Command.NONE)
                    cmd = Command.FIRST;
                    else 
                    cmd =prevCommand; 
                    } 
                    %>
                    <% ctrLine.setLocationImg(approot + "/images");
                       ctrLine.initDefault();
                    %>
                    <%=ctrLine.drawImageListLimit(cmd, vectSizeDeduction, startDeduction, recordToGet)%> 
                                                      
                    <%                                                                         
                        if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmSalaryLevelDetail.errorSize() < 1)) {
                            if (privAdd) {
                    %>
                    <div>
                        <a href="javascript:cmdAddD()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                        <a href="javascript:cmdAddD()" class="command">Add New Deduction</a> 
                    </div>
                        <%}
                    }%>
                    <%if (status == 2 || status == 0) {
                        if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmSalaryLevelDetail.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                    <div>
                        <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                        <a href="javascript:cmdSave()" class="command">Save Deduction</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                        <a href="javascript:cmdConfirmDelete()" class="command">Delete Deduction</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                        <a href="javascript:cmdBack()" class="command">Back to List Salary Component</a> 
                    </div>
                    <%}
                    }%>
                    <div>&nbsp;</div>
                    <div>
                        <a href="javascript:cmdBackSal('<%=empId%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                        <a href="javascript:cmdBackSal('<%=empId%>')" class="command">Back to List Salary Level</a> 
                    </div>
                
                
            </div>
            </form>
        </div>
                
    <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
    <div>
            <!-- untuk footer -->
            <%@include file="../../footer.jsp" %>
    </div>
    <%} else {%>
    <div>
            <%@ include file = "../../main/footer.jsp" %>
    </div>
    <%}%>
    </body>
</html>
