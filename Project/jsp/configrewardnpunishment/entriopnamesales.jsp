
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentMain"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmSrcRewardPunishment"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmSrcRewardPunishment"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmSrcEntriOpnameSales"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.SrcEntriOpnameSales"%>
<%@page import="com.dimata.harisma.entity.periodestokopname.PstPeriodeStokOpname"%>
<%@page import="com.dimata.harisma.entity.periodestokopname.PeriodeStokOpname"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.ConfigRewardAndPunishment"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.PstConfigRewardAndPunishment"%>
<%@page import="com.dimata.harisma.entity.jenisSo.JenisSo"%>
<%@page import="com.dimata.harisma.entity.jenisSo.PstJenisSo"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.PstEntriOpnameSales"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.EntriOpnameSales"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmEntriOpnameSales"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.CtrlEntriOpnameSales"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ page language="java" %>  

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->

<%@ include file = "../main/javainit.jsp" %> 
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_ENTRI_OPNAME_SALES);%>
<%@ include file = "../main/checkuser.jsp" %>

<%
    //boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    //boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));

%>

<%!    public String drawList(int iCommand, Vector listEntriOpnameSales, long oidEntriOpname, EntriOpnameSales entriOpnameSales, FrmEntriOpnameSales frmEntriOpnameSales, int start,long oidLocation) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "2%");
        ctrlist.addHeader("Outlet", "7%");
 //        ctrlist.addHeader("Period", "20%");
        ctrlist.addHeader("Jenis So", "20%");
        ctrlist.addHeader("Type of Tolerance", "10%");
        ctrlist.addHeader("NetSales Period", "15%");
        ctrlist.addHeader("%toleransi", "10%");
        ctrlist.addHeader("Barang Hilang", "10%");
        ctrlist.addHeader("(+/-) ", "10%");
        ctrlist.addHeader("status", "10%");
        ctrlist.addHeader("Create Form Location Opname", "10%");
        ctrlist.addHeader("From Peroid", "10%");
        ctrlist.addHeader("To Period", "10%");
        ctrlist.addHeader("Process/<br>Reprocess<br>"
                +"<a href=\"Javascript:SetAllCheckBoxes('" + FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES + "','userSelect', true)\">Sel.All</a>"
                + "<br> <a href=\"Javascript:SetAllCheckBoxes('" + FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES + "','userSelect', false)\">Del.All</a>", "30%");
        ctrlist.addHeader("View", "10%");

       ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        Vector rowx = new Vector(1, 1);

        ctrlist.reset();
        String formatDouble = "#.##";
        int index = -1;
        //int no = 1;
        // int nmber = 1;
        int noCounter = start + 1;

        
        //set rupiah
         DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setCurrencySymbol("");
                        dfs.setMonetaryDecimalSeparator(',');
                        dfs.setGroupingSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
        
        
        Vector valueJenisSo = new Vector();
        Vector keyJenisSo = new Vector();
        valueJenisSo.add("" + 0);
        keyJenisSo.add("select");

         Vector valueOutlet = new Vector();
        Vector keyOutlet = new Vector();
        valueOutlet.add("" + 0);
        keyOutlet.add("select");

        Vector valuePeriod = new Vector();
        Vector keyPeriod = new Vector();
        valuePeriod.add("" + 0);
        keyPeriod.add("select");
        double nilaiSettingProsentase = 0;
        Vector vRewardnPunismendSetting = PstConfigRewardAndPunishment.list(0, 1, "", "");
        Vector valueTypeOfTolerance = new Vector();
        Vector keyTypeOfTolerance = new Vector();
        ConfigRewardAndPunishment configRewardAndPunishment = new ConfigRewardAndPunishment();
        valueTypeOfTolerance.add("" + ConfigRewardAndPunishment.DC);
        keyTypeOfTolerance.add("DC");
        valueTypeOfTolerance.add("" + ConfigRewardAndPunishment.NetOffSales);
        keyTypeOfTolerance.add("Net Sales");
        int typeTolerance=0;
        if (vRewardnPunismendSetting != null && vRewardnPunismendSetting.size() > 0) {
            for (int x = 0; x < vRewardnPunismendSetting.size(); x++) {
                configRewardAndPunishment = (ConfigRewardAndPunishment) vRewardnPunismendSetting.get(x);

                try {
                    if (entriOpnameSales.getLocationId() != 0) {
                        Location location = PstLocation.fetchExc(entriOpnameSales.getLocationId());
                        if (location.getType() == 0) {
                            //maka DC

                            entriOpnameSales.setTypeTolerance(ConfigRewardAndPunishment.DC);
                            typeTolerance=ConfigRewardAndPunishment.DC;
                            nilaiSettingProsentase = configRewardAndPunishment.getPresentaseToBod();
                        } else {
                            //maka net sales
                             typeTolerance=ConfigRewardAndPunishment.NetOffSales;
                            entriOpnameSales.setTypeTolerance(ConfigRewardAndPunishment.NetOffSales);
                            nilaiSettingProsentase = configRewardAndPunishment.getPresentaseToSales();
                        }
                    }
                } catch (Exception exc) {
                }
                //harus menggunakan anggka 0 tau 1
            }
        }

        Vector vListOutlet = PstLocation.list(0, 0, "", "");
        if (vListOutlet != null && vListOutlet.size() > 0) {
            for (int x = 0; x < vListOutlet.size(); x++) {
                Location location = (Location) vListOutlet.get(x);
                valueOutlet.add("" + location.getOID());
                keyOutlet.add("" + location.getName());
            }
        }

        Vector vJenisSo = PstJenisSo.list(0, 0, "", "");
        if (vJenisSo != null && vJenisSo.size() > 0) {
            for (int x = 0; x < vJenisSo.size(); x++) {
                JenisSo jenisSo = (JenisSo) vJenisSo.get(x);
                valueJenisSo.add("" + jenisSo.getOID());
                keyJenisSo.add("" + jenisSo.getNamaSo());
            }
        }
        Vector vPeriodOpname = PstPeriodeStokOpname.list(0, 0, "", "");
        if (vPeriodOpname != null && vPeriodOpname.size() > 0) {
            for (int x = 0; x < vPeriodOpname.size(); x++) {
                PeriodeStokOpname periodeStokOpname = (PeriodeStokOpname) vPeriodOpname.get(x);
                valuePeriod.add("" + periodeStokOpname.getOID());
                keyPeriod.add("" + periodeStokOpname.getNamePeriod()); 
            }
        }

         int persentase ;
            if (PstConfigRewardAndPunishment.getProsentaseToSales(null) != 0){
                persentase= PstConfigRewardAndPunishment.getProsentaseToSales(null);
            } else {
                persentase= 0;
            }
        boolean adaKlikEdit = true;
        if (listEntriOpnameSales != null && listEntriOpnameSales.size() > 0) {
           
            for (int i = 0; i < listEntriOpnameSales.size(); i++) {
//PublicLeave publicLeave = (PublicLeave) objectClass.get(i);
                EntriOpnameSales objEntriOpnameSales = (EntriOpnameSales) listEntriOpnameSales.get(i);
                rowx = new Vector();
                rowx.add("" + noCounter);
                noCounter++;
                if (oidEntriOpname == objEntriOpnameSales.getOID()) {
                    index = i;
                }
                if (index == i && (iCommand == Command.GOTO || iCommand == Command.EDIT || iCommand == Command.ASK)) {
                    //jika dia ada Edit
                    adaKlikEdit = false;
                    if (iCommand != Command.GOTO) {
                        // rowx.add("" + (noCounter));
                          objEntriOpnameSales = new EntriOpnameSales();
                          objEntriOpnameSales.setLocationId(oidLocation);
                          objEntriOpnameSales.setTypeTolerance(typeTolerance);
                        rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_LOCATION_ID]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getLocationId()), valueOutlet, keyOutlet, "onChange=\"javascript:cmdChangeLoc()\""));
                       // rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PERIOD_ID]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getPeriodId()), valuePeriod, keyPeriod, "") + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_PERIOD_ID));
                        rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_JENIS_SO_ID]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getJenisSoId()), valueJenisSo, keyJenisSo, "") + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_JENIS_SO_ID));
                        rowx.add("<input type=\"hidden\" name=\"" + "type_prosentase"+i+ "\" value=\"" + nilaiSettingProsentase + "\" size=\"10\" class=\"elemenForm\">" + ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_TYPE_OF_TOLERANCE]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getTypeTolerance()), valueTypeOfTolerance, keyTypeOfTolerance, ""));
                        rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_NET_SALES_PERIOD]+i+ "\" value=\"" + Formater.formatNumber(objEntriOpnameSales.getNetSalesPeriod(), formatDouble) + "\" onkeydown=\"return numbersonly(this, event);\" onkeyup=\"cmdCekLast("+i+")\" size=\"10\" class=\"elemenForm\">");

                        rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PROSENTASE_TOLERANCE]+i+ "\" onkeydown=\"return numbersonly(this, event);\" onkeyup=\"cmdCekLast("+i+")\" value=\"" + objEntriOpnameSales.getProsentaseTolerance() + "\" size=\"10\" class=\"elemenForm\">");
                        rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_BARANG_HILANG]+i+ "\" onkeydown=\"return numbersonly(this, event);\" onkeyup=\"cmdCekLast("+i+")\" value=\"" + Formater.formatNumber(objEntriOpnameSales.getBarangHilang(), formatDouble) + "\" size=\"10\" class=\"elemenForm\">");
                        rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PLUS_MINUS]+i+ "\" value=\"" + Formater.formatNumber(objEntriOpnameSales.getPlusMinus(), formatDouble) + "\" size=\"10\" class=\"elemenForm\">");

                        rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_STATUS_OPNAME]+i+ "\"  value=\"" + objEntriOpnameSales.getStatusOpname() + "\" size=\"10\" onkeydown=\"return numbersonly(this, event);\" onkeyup=\"cmdCekLast("+i+")\" class=\"elemenForm\">");

                        rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_CREATE_FORM_LOCATION_OPNAME]+i+ "\" value=\"" + objEntriOpnameSales.getCreateLocationName() + "\" size=\"15\" class=\"elemenForm\">" + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_CREATE_FORM_LOCATION_OPNAME));
                            
                        rowx.add(ControlDate.drawDateWithStyle(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_DATE_START_PERIOD]+i, objEntriOpnameSales.getDtFromPeriod(), 0, -40, "formElemen") +"*"+ frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_DATE_START_PERIOD));
                        rowx.add(ControlDate.drawDateWithStyle(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_DATE_END_PERIOD]+i, objEntriOpnameSales.getDtToPeriod(), 0, -40, "formElemen") +"*"+ frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_DATE_END_PERIOD));
                      
                        rowx.add("<input type=\"hidden\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_ENTRI_OPNAME_SALES_ID]+i+ "\" value=\""+objEntriOpnameSales.getOID()+"\" size=\"10\"");
                        rowx.add("-");
                    

                            } else {
                        //rowx.add("" + (noCounter));
                        objEntriOpnameSales.setLocationId(oidLocation); 
                        rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_LOCATION_ID]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getLocationId()), valueOutlet, keyOutlet, "onChange=\"javascript:cmdChangeLoc()\""));
                        //rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PERIOD_ID]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getPeriodId()), valuePeriod, keyPeriod, "") + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_PERIOD_ID));
                        rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_JENIS_SO_ID]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getJenisSoId()), valueJenisSo, keyJenisSo, "") + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_JENIS_SO_ID));
                        rowx.add("<input type=\"hidden\" name=\"" + "type_prosentase"+i + "\" value=\"" + nilaiSettingProsentase + "\" size=\"10\" class=\"elemenForm\">" + ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_TYPE_OF_TOLERANCE]+i, "formElemen", null, String.valueOf(entriOpnameSales.getTypeTolerance()), valueTypeOfTolerance, keyTypeOfTolerance, ""));
                        rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_NET_SALES_PERIOD]+i+ "\" value=\"" + Formater.formatNumber(objEntriOpnameSales.getNetSalesPeriod(), formatDouble) + "\" onkeydown=\"return numbersonly(this, event);\" onkeyup=\"cmdCekLast("+i+")\" size=\"10\" class=\"elemenForm\">");

                        rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PROSENTASE_TOLERANCE]+i+ "\" onkeydown=\"return numbersonly(this, event);\" onkeyup=\"cmdCekLast("+i+")\" value=\"" + objEntriOpnameSales.getProsentaseTolerance() + "\" size=\"10\" class=\"elemenForm\">");
                        rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_BARANG_HILANG]+i+ "\" onkeydown=\"return numbersonly(this, event);\" onkeyup=\"cmdCekLast("+i+")\" value=\"" + Formater.formatNumber(objEntriOpnameSales.getBarangHilang(), formatDouble) + "\" size=\"10\" class=\"elemenForm\">");
                        rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PLUS_MINUS]+i+ "\" value=\"" + Formater.formatNumber(objEntriOpnameSales.getPlusMinus(), formatDouble) + "\" size=\"10\" class=\"elemenForm\">");

                        rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_STATUS_OPNAME]+i + "\"  value=\"" + objEntriOpnameSales.getStatusOpname() + "\" size=\"10\" onkeydown=\"return numbersonly(this, event);\" onkeyup=\"cmdCekLast("+i+")\" class=\"elemenForm\">");

                        rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_CREATE_FORM_LOCATION_OPNAME]+i+ "\" value=\"" + objEntriOpnameSales.getCreateLocationName() + "\" size=\"15\" class=\"elemenForm\">" + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_CREATE_FORM_LOCATION_OPNAME));
                        rowx.add(ControlDate.drawDateWithStyle(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_DATE_START_PERIOD]+i, objEntriOpnameSales.getDtFromPeriod(), 0, -40, "formElemen") +"*"+ frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_DATE_START_PERIOD));
                        rowx.add(ControlDate.drawDateWithStyle(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_DATE_END_PERIOD]+i, objEntriOpnameSales.getDtToPeriod(), 0, -40, "formElemen") +"*"+ frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_DATE_END_PERIOD));

                        rowx.add("<input type=\"hidden\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_ENTRI_OPNAME_SALES_ID]+i+ "\" value=\""+objEntriOpnameSales.getOID()+"\"  size=\"10\"");
                        

                        rowx.add("-");
                      }


                    lstLinkData.add(String.valueOf(objEntriOpnameSales.getOID()));

                } else {
                    //jika sdh selesai save maka akan kesini / dia akan ngelist
                    //    rowx.add("<a href=\"javascript:cmdEdit('" + String.valueOf(objEntriOpnameSales.getOID()) + "')\">" + noCounter + "</a>");
                    String namalocation = PstLocation.GetNamaLocation(objEntriOpnameSales.getLocationId());
                    String namaJenisSo  = PstJenisSo.GetNamaJenisSo(objEntriOpnameSales.getJenisSoId());
                       
                        String hsl = "Rp. " + df.format(objEntriOpnameSales.getPlusMinus());
                    rowx.add(namalocation);
                    // rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PERIOD_ID]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getPeriodId()), valuePeriod, keyPeriod, "disabled "));
                   // rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_JENIS_SO_ID]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getJenisSoId()), valueJenisSo, keyJenisSo, "readonly"));
                    rowx.add(namaJenisSo);
                   
                    rowx.add("<input type=\"hidden\" readonly=\"readonly\" name=\"" + "type_prosentase" + "\" value=\"" + nilaiSettingProsentase + "\" size=\"40\" class=\"elemenForm\">" + ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_TYPE_OF_TOLERANCE]+i, "formElemen", null, String.valueOf(objEntriOpnameSales.getTypeTolerance()), valueTypeOfTolerance, keyTypeOfTolerance, "disabled"));
                    rowx.add("Rp. " + df.format(objEntriOpnameSales.getNetSalesPeriod()));

                    rowx.add("Rp. " + df.format(objEntriOpnameSales.getProsentaseTolerance()));
                    rowx.add("Rp. " + df.format(objEntriOpnameSales.getBarangHilang()));
                    rowx.add("Rp. " + df.format(objEntriOpnameSales.getPlusMinus()));

                    rowx.add(""+objEntriOpnameSales.getStatusOpname());

                    rowx.add("" +objEntriOpnameSales.getCreateLocationName());
                    
                    rowx.add(Formater.formatDate(objEntriOpnameSales.getDtFromPeriod(), "YYYY-MM-dd"));
                    rowx.add(Formater.formatDate(objEntriOpnameSales.getDtToPeriod(), "YYYY-MM-dd"));
                    
                    rowx.add("<input type=\"checkbox\" name=\"userSelect_"+i+"\" value=\"1\"><input type=\"hidden\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_ENTRI_OPNAME_SALES_ID]+i+ "\" value=\""+objEntriOpnameSales.getOID()+"\"  size=\"10\"");
                    long rewardPunismentMainId=PstRewardAndPunishmentMain.getMainIdWhereEntriOpname(objEntriOpnameSales.getOID());
                   // rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_REWARD_PUNISMENT_MAIN_ID]+i+ "\" value=\"" +rewardPunismentMainId +" \" size=\"15\" onChange=\"javascript:cmdSearchRewardPunisment()\" class=\"elemenForm\">");
                   
                    long mainId = PstRewardAndPunishmentMain.getMainIdWhereEntriOpname(objEntriOpnameSales.getOID());
                    if (mainId == 0){
                        rowx.add("Belum Di Proses");
                    } else {
                        rowx.add("<a href =\"search_reward_punisment_list.jsp?viewDirect="+rewardPunismentMainId+"\">view</a>");

                    }
                    
                   lstLinkData.add(String.valueOf(objEntriOpnameSales.getOID()));
                }
                lstData.add(rowx);
                //noCounter = (noCounter+1); 
            }
        }
        rowx = new Vector();

                             
        
        
        if (adaKlikEdit && (iCommand == Command.GOTO || iCommand == Command.ADD || (iCommand == Command.SAVE && frmEntriOpnameSales.errorSize() > 0) || (listEntriOpnameSales.size() < 1))) {
            rowx.add("" + noCounter);
            noCounter++;
           
                
            
            int valx=listEntriOpnameSales!=null ?listEntriOpnameSales.size()+1:1;
            //long oidLocations=entriOpnameSales.getLocationId();
            entriOpnameSales = new EntriOpnameSales();
            entriOpnameSales.setLocationId(oidLocation);
            entriOpnameSales.setTypeTolerance(typeTolerance);
            rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_LOCATION_ID]+valx, "formElemen", null, String.valueOf(entriOpnameSales.getLocationId()), valueOutlet, keyOutlet, "onChange=\"javascript:cmdChangeLoc()\""));
          //  rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PERIOD_ID]+valx, "formElemen", null, String.valueOf(entriOpnameSales.getPeriodId()), valuePeriod, keyPeriod, "") + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_PERIOD_ID));
            rowx.add(ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_JENIS_SO_ID]+valx, "formElemen", null, String.valueOf(entriOpnameSales.getJenisSoId()), valueJenisSo, keyJenisSo, "") + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_JENIS_SO_ID));
            rowx.add("<input type=\"hidden\" name=\"" + "type_prosentase"+valx + "\" value=\"" + nilaiSettingProsentase + "\" size=\"10\" class=\"elemenForm\">" + ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_TYPE_OF_TOLERANCE]+valx, "formElemen", null, String.valueOf(entriOpnameSales.getTypeTolerance()), valueTypeOfTolerance, keyTypeOfTolerance, ""));
            rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_NET_SALES_PERIOD]+valx + "\" value=\"" + Formater.formatNumber(entriOpnameSales.getNetSalesPeriod(), formatDouble) + "\" onkeyup=\"cmdCekLast("+valx+")\" onkeydown=\"return numbersonly(this, event);\"  size=\"10\" class=\"elemenForm\">");
            rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PROSENTASE_TOLERANCE]+valx + "\"   onkeyup=\"cmdCekLast("+valx+")\" onkeydown=\"return numbersonly(this, event);\" size=\"10\" class=\"elemenForm\">");
            rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_BARANG_HILANG]+valx + "\"   value=\"" + Formater.formatNumber(entriOpnameSales.getBarangHilang(), formatDouble) + "\" onkeyup=\"cmdCekLast("+valx+")\" onkeydown=\"return numbersonly(this, event);\" size=\"10\" class=\"elemenForm\">");
            rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PLUS_MINUS]+valx + "\" value=\"" + Formater.formatNumber(entriOpnameSales.getPlusMinus(), formatDouble) + "\" size=\"10\" class=\"elemenForm\">");
            rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_STATUS_OPNAME]+valx + "\"  value=\"" + entriOpnameSales.getStatusOpname() + "\" size=\"10\" onkeyup=\"cmdCekLast("+valx+")\" onkeydown=\"return numbersonly(this, event);\"  class=\"elemenForm\">");
            rowx.add("<input type=\"text\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_CREATE_FORM_LOCATION_OPNAME]+valx + "\" value=\"" + entriOpnameSales.getCreateLocationName() + "\" size=\"15\" class=\"elemenForm\">" + "*" + frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_CREATE_FORM_LOCATION_OPNAME));
            rowx.add(ControlDate.drawDateWithStyle(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_DATE_START_PERIOD]+valx, entriOpnameSales.getDtFromPeriod(), 0, -40, "formElemen") +"*"+ frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_DATE_START_PERIOD));
            rowx.add(ControlDate.drawDateWithStyle(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_DATE_END_PERIOD]+valx, entriOpnameSales.getDtFromPeriod(), 0, -40, "formElemen") +"*"+ frmEntriOpnameSales.getErrorMsg(frmEntriOpnameSales.FRM_FLD_DATE_END_PERIOD));

            rowx.add("<input type=\"hidden\" name=\"" + FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_ENTRI_OPNAME_SALES_ID]+valx + "\" value=\""+entriOpnameSales.getOID()+"\"  size=\"10\"");
            rowx.add("-");
            lstLinkData.add(String.valueOf(entriOpnameSales.getOID()));
            noCounter = (noCounter + 1);
        }
        lstData.add(rowx);
        return ctrlist.draw();
    }

%>

<%


    long oidEntriOpname = FRMQueryString.requestLong(request, "oidEntriOpname");
    long oidLocation = 0;//FRMQueryString.requestLong(request, FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_LOCATION_ID]);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");




    int iErrCode = FRMMessage.ERR_NONE;
    String msgString = "";
    ControlLine ctrLine = new ControlLine();
    //System.out.println("iCommand = " + iCommand);
    CtrlEntriOpnameSales ctrlEntriOpnameSales = new CtrlEntriOpnameSales(request);
   
    //oidEntriOpname = entriOpnameSales.getOID();
    Vector listEntriOpnameSales = new Vector();
    PstEntriOpnameSales pstEntriOpnameSales = new PstEntriOpnameSales();
    /*variable declaration*/
    int recordToGet = 5;
    SrcEntriOpnameSales srcEntriOpname = new SrcEntriOpnameSales();
    FrmSrcEntriOpnameSales frmSrcEntriOpnameSales = new FrmSrcEntriOpnameSales(request, srcEntriOpname);
    if(iCommand==Command.VIEW){
         frmSrcEntriOpnameSales.requestEntityObject(srcEntriOpname);
    }
    if(iCommand==Command.GOTO || iCommand==Command.EDIT || iCommand==Command.BACK || (iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)){
        try
	{				
		srcEntriOpname = (SrcEntriOpnameSales) session.getValue("SEARCH_ENTRI_OPNAME");			
		if(srcEntriOpname == null)
		{
			srcEntriOpname = new SrcEntriOpnameSales();
		}		
	}
	catch (Exception e)
	{
		srcEntriOpname = new SrcEntriOpnameSales();
	}
    }
    int vectSize = PstEntriOpnameSales.getCountWithParam(srcEntriOpname); 
    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        if (start < 0) {
            start = 0;
        }


        start = ctrlEntriOpnameSales.actionList(iCommand, start, vectSize, recordToGet);

    }
    /* get record to display */
    if (start < 0) {
        start = 0;
    }

    listEntriOpnameSales = pstEntriOpnameSales.listWithParam(start, recordToGet, "", srcEntriOpname);
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listEntriOpnameSales.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;
        } //go to Command.PREV
        else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        if (start < 0) {
            start = 0;
        }
        listEntriOpnameSales = pstEntriOpnameSales.listWithParam(start, recordToGet, "", srcEntriOpname);
        //listDeduction = PstPayComponent.list(startBenefit,recordToGet, whereClauseDeduction , orderClause);
    }
    iErrCode = ctrlEntriOpnameSales.action(iCommand, oidEntriOpname,listEntriOpnameSales);
    msgString = ctrlEntriOpnameSales.getMessage();
    
    FrmEntriOpnameSales frmEntriOpnameSales = ctrlEntriOpnameSales.getForm();
    EntriOpnameSales entriOpnameSales = ctrlEntriOpnameSales.getEntriOpnameSales();
    oidLocation = entriOpnameSales.getLocationId();
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    vectSize = PstEntriOpnameSales.getCountWithParam(srcEntriOpname); 
    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        if (start < 0) {
            start = 0;
        }


        start = ctrlEntriOpnameSales.actionList(iCommand, start, vectSize, recordToGet);

    }
    /* get record to display */
    if (start < 0) {
        start = 0;
    }

    listEntriOpnameSales = pstEntriOpnameSales.listWithParam(start, recordToGet, "", srcEntriOpname);
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listEntriOpnameSales.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;
        } //go to Command.PREV
        else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        if (start < 0) {
            start = 0;
        }
        listEntriOpnameSales = pstEntriOpnameSales.listWithParam(start, recordToGet, "", srcEntriOpname);
        //listDeduction = PstPayComponent.list(startBenefit,recordToGet, whereClauseDeduction , orderClause);
    }
    
    session.putValue("SEARCH_ENTRI_OPNAME", srcEntriOpname);
    /*end handle condition if size of record to display = 0 and start > 0 	after delete*/
%>


<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Entri Opname Sales</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
            //untuk membuat number
            function numbersonly(ini, e){
                if (e.keyCode>=49){
                    if(e.keyCode<=57){
                        a = ini.value.toString().replace(".","");
                        b = a.replace(/[^\d]/g,"");
                        b = (b=="0")?String.fromCharCode(e.keyCode):b + String.fromCharCode(e.keyCode);
                        ini.value = tandaPemisahTitik(b);
                        //ketika ngisi
                        //alert("oi1");
                        return true;
                    }
                    else if(e.keyCode<=105){
                        if(e.keyCode>=96){
                            //e.keycode = e.keycode - 47;
                            a = ini.value.toString().replace(".","");
                            b = a.replace(/[^\d]/g,"");
                            b = (b=="0")?String.fromCharCode(e.keyCode-48):b + String.fromCharCode(e.keyCode-48);
                            //ini.value = tandaPemisahTitik(b);
                            //alert(e.keycode);
                            //alert("oi2");
                            return false;
                        }
                        else {
                            //ketika membuat huruf
                           // alert("oi'2");
                            return false;
                        }
                    }
                    else {
                        //ketika membuat koma
                        //alert("oi'1");
                        return true; 
                        
                    }
                }else if (e.keyCode==48){
                    a = ini.value.replace(".","") + String.fromCharCode(e.keyCode);
                    b = a.replace(/[^\d]/g,"");
                    if (parseFloat(b)!=0){
                        //ini.value = tandaPemisahTitik(b);
                        //alert("oi3");
                        //ada spasi
                        return true;
                    } else {
                        //ketika ingin membuat 0
                        //alert("oi'3");
                        return true;
                    }
                }else if (e.keyCode==95){
                    a = ini.value.replace(".","") + String.fromCharCode(e.keyCode-48);
                    b = a.replace(/[^\d]/g,"");
                    if (parseFloat(b)!=0){
                        //ini.value = tandaPemisahTitik(b);
                        //alert("oi4");
                        return false;
                    } else {
                       // alert("oi'4");
                        return false;
                    }
                }else if (e.keyCode==8 || e.keycode==46){
                    a = ini.value.replace(".","");
                    b = a.replace(/[^\d]/g,"");
                    b = b.substr(0,b.length -1);
                    if (tandaPemisahTitik(b)!=""){
                        //ini.value = tandaPemisahTitik(b);
                        //ketika hapus
                        //alert("oi5");
                    } else {
                        //ini.value = "";
                        //alert("oi6");
                    }
		
                    return false;
                } else if (e.keyCode==9){
                    //alert("oi7");
                    return true;
                } else if (e.keyCode==17){
                    //alert("oi8");
                    return true;
                } else {
                    //alert (e.keyCode);
                    //alert("oi'6");
                    return false;
                }

            }



            function cmdEdit(oidEntriOpname){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.oidEntriOpname.value=oidEntriOpname;
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.EDIT%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            function cmdProsess(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.POST%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
                
            }
          
            function cmdChangeLoc(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            function cmdAdd(){
                
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.oidEntriOpname.value="0";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.ADD%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            
            function cmdSave(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.SAVE%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            function cmdBack(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.BACK%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            function cmdViewResult(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.SEARCH%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="search_reward_punisment.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            function cmdBackSrc(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.BACK%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="search_entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            function cmdListFirst(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.prev_command.value="<%=Command.FIRST%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            function cmdListPrev(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.PREV%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.prev_command.value="<%=Command.PREV%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            function cmdListNext(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.prev_command.value="<%=Command.NEXT%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            function cmdListLast(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.LAST%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.prev_command.value="<%=Command.LAST%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            function cmdConfirmDelete(oid){
                var x = confirm(" Are You Sure to Delete?");
                if(x){
                    document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.DELETE%>";
                    document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                    document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
                }
            }
            


            function cmdCekLast(index){
                var netSalesPeriod;
                var barangHilang;
                var toleransiPersen;
                var toleansi;
                var plusMinus;
                var status;
                var dataSudahAda=new Boolean(0); //artinya false
                
                
                
   //switch(index){
   //alert(index);
    <%
        int tot=0;
        for(int k=0;k<listEntriOpnameSales.size();k++){
    %>
            if(index==<%=""+k%>){
                dataSudahAda=new Boolean(1); //artinya true
                netSalesPeriod = parseFloat(document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_NET_SALES_PERIOD]+k%>.value);
                //alert(netSalesPeriod);
                barangHilang = parseFloat(document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_BARANG_HILANG]+k%>.value); 
                toleransiPersen = parseFloat(document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%="type_prosentase"+k%>.value); 
               
               
                toleansi = (netSalesPeriod * (toleransiPersen/100)); 
                plusMinus = toleansi-barangHilang;
               
                if(toleansi>barangHilang){
                    status = "Reward";
                }else{
                    status = "Punisment";
                }
                //merubahValueKomaMenjadiTiti(netSalesPeriod);
                //merubahValueKomaMenjadiTiti(barangHilang);
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PROSENTASE_TOLERANCE]+k%>.value=toleansi;///format number menjadi 3 angka d belakang koma   
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PLUS_MINUS]+k%>.value=plusMinus;///format number menjadi 3 angka d belakang koma   
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_STATUS_OPNAME]+k%>.value=status;        
            }
                
    <% }%>
    if(dataSudahAda==false){
        <%
            tot = listEntriOpnameSales!=null?listEntriOpnameSales.size()+1:1;
        %>
                netSalesPeriod = parseFloat(document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_NET_SALES_PERIOD]+tot%>.value);
                //alert(netSalesPeriod);
                barangHilang = parseFloat(document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_BARANG_HILANG]+tot%>.value); 
                toleransiPersen = parseFloat(document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%="type_prosentase"+tot%>.value); 
               
               
                toleansi = (netSalesPeriod * (toleransiPersen/100)); 
                plusMinus = toleansi-barangHilang;
               
                if(toleansi>barangHilang){
                    status = "Reward";
                }else{
                    status = "Punisment";
                }
                //merubahValueKomaMenjadiTiti(netSalesPeriod);
                //merubahValueKomaMenjadiTiti(barangHilang);
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PROSENTASE_TOLERANCE]+tot%>.value=toleansi;///format number menjadi 3 angka d belakang koma   
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_PLUS_MINUS]+tot%>.value=plusMinus;///format number menjadi 3 angka d belakang koma   
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_STATUS_OPNAME]+tot%>.value=status;             
    }
    //}//untuk swict index 
}

function SetAllCheckBoxes(FormName, CheckValue){
	    if(!document.forms[FormName])
		return;
            <%  
               if(listEntriOpnameSales!=null){ 
                for(int i = 0 ; i < listEntriOpnameSales.size() ; i++){
                    String nameInp = "prosess"+i; 
                    %>  
                            document.forms[FormName].<%=nameInp%>.checked = CheckValue;
                    <%
                 }
               }else{ 
            %>
                    return;
                    <%}%>
        }
function SetAllCheckBoxes(FormName, FieldName, CheckValue)
{
	if(!document.forms[FormName])
		return;
	var objCheckBoxes = document.forms[FormName].elements[FieldName];
	if(!objCheckBoxes)
		return;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = CheckValue;
	else
		// set the check value for all check boxes
			for(var i = 0; i < countCheckBoxes; i++)
			objCheckBoxes[i].checked = CheckValue;
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
        <!-- #EndEditable --> 
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
                    <!-- #EndEditable --> </td>
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Entri Reward And Punishment<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td height="20"> </td>
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
                                                                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                                                                <form name="<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>" method="post" action=""> 
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    
                                                                                        <input type="hidden" name="oidEntriOpname" value="<%=oidEntriOpname%>">
                                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr> 
                                                                                            <td>
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">   
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td  align="left" height="14" valign="middle" colspan="2" class="listtitle">&nbsp;Reward And Punishment Setting </td>
                                                                                                    </tr> 
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                            <%
                                                                                                try {
                                                                                                    // out.println("listBenefit"+listBenefit.size());
                                                                                                    if ((listEntriOpnameSales == null || listEntriOpnameSales.size() < 1) && (iCommand == Command.NONE)) {
                                                                                                        iCommand = Command.ADD;
                                                                                                    }

                                                                                            %>
                                                                                        <tr> 
                                                                                            <td width="24%" colspan="2">
                                                                                                <%= drawList(iCommand, listEntriOpnameSales, oidEntriOpname, entriOpnameSales, frmEntriOpnameSales, start,oidLocation)%> 
                                                                                            <%
                                                                                                        } catch (Exception exc) {
                                                                                                        }
                                                                                                    %>    
                                                                                              </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table cellpadding="0" cellspacing="0" border="0">
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td width="100%" valign="top">
                                                                                                            <table width="100%" border="0" cellspacing="0" border="0">
                                                                                                                <%if (msgString != null && msgString.length() > 0) {%>
                                                                                                                <tr>
                                                                                                                    <td width="100%" valign="top">
                                                                                                                        <table width="100%" cellspacing="0" border="0">
                                                                                                                            <tr>
                                                                                                                                <td>
                                                                                                                                    <%if (iErrCode != FRMMessage.ERR_NONE) {%>
                                                                                                                                    <div class="alert-box notice-error"><span>error: </span><%=" " + msgString%></div>
                                                                                                                                    <%} else {%>
                                                                                                                                    <div class="alert-box notice"><span>notice: </span><%=" " + msgString%></div>
                                                                                                                                 
                                                                                                                                    <%}%>

                                                                                                                                </td>
                                                                                                                            </tr>
                                                                                                                        </table>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <%}%>
                                                                                                                <tr>
                                                                                                                    <td>
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

                                                                                                                        if (iCommand == Command.GOTO) {
                                                                                                                            iCommand = Command.ADD;
                                                                                                                        }
                                                                                                                    %>

                                                                                                                    <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                   
                                                                                                                    <%
                                                                                                                        //	out.println("masukq"+Command.ADD) ;
                                                                                                                        if (((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) || (iCommand == Command.POST)) && (frmEntriOpnameSales.errorSize() < 1)) {%>
                                                                                                                <tr> 
                                                                                                                    <td width="100%">
                                                                                                                        <table width="100%">
                                                                                                                            <tr>
                                                                                                                                <td width="150"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                                                                                                                        <a href="javascript:cmdAdd()" class="command">Add Entri Op Sales</a> </td>
                                                                                                                                <td width="150"><a href="javascript:cmdProsess()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Prosess"></a>
                                                                                                                        <a href="javascript:cmdProsess()" class="command">Prosess Opname</a> </td>
                                                                                                                                <td width="150"><a href="javascript:cmdViewResult()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Result"></a>
                                                                                                                        <a href="javascript:cmdViewResult()" class="command">Go To Result</a> </td>
                                                                                                                                <td width="150"><a href="javascript:cmdBackSrc()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="View Result"></a>
                                                                                                                        <a href="javascript:cmdBackSrc()" class="command">Back Search</a> </td>
                                                                                                                            </tr>
                                                                                                                        </table>
                                                                                                                    </td>
                                                                                                                    
                                                                                                                </tr>
                                                                                                                <%}%>
                                                                                                                
                                                                                                                 <% if ((iCommand == Command.EDIT)) {%>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td >
                                                                                                                        <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                                                                                                                        <a href="javascript:cmdConfirmDelete()" class="command">Delete Op Sales</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                                        <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                                                                                                        <a href="javascript:cmdBack()" class="command">Back to Op Sales</a> </td>
                                                                                                                </tr>
                                                                                                                <%}
                                                                                                                %>


                                                                                                                <% if (true) {
                                                                                                                        if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmEntriOpnameSales.errorSize() > 0) ||  (iCommand == Command.ASK)) {%>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td >
                                                                                                                        <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)">
                                                                                                                        <img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                                                                                                                        <a href="javascript:cmdSave()" class="command">Save Op Sales</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                                                                                        <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                                                                                                                        <a href="javascript:cmdConfirmDelete()" class="command">Delete Op Sales</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                                        <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                                                                                                        <a href="javascript:cmdBack()" class="command">Back to Op Sales</a> </td>
                                                                                                                </tr>
                                                                                                                <%}
                                                                                                                    }%>
                                                                                                               
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>


                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </form> 
                                                                               <!-- #EndEditable -->      
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
                                <!-- #BeginEditable "script" --> <script language="JavaScript">
                                        var oBody = document.body;
                                        var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
                                </script>

                                <!-- #EndEditable --> <!-- #EndTemplate -->
                                </html>
