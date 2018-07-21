<%-- 
    Document   : outlet
    Created on : Feb 25, 2014, 3:05:26 PM
    Author     : Satrya Ramayu
--%>
<%@page import="com.dimata.harisma.session.attendance.employeeoutlet.SessSearchEmployeeMapping"%>
<%@page import="com.dimata.harisma.session.attendance.employeeoutlet.SessSearchEmployeeMapping"%>
<%@page import="com.dimata.harisma.entity.attendance.EmployeeSchedule"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Remove"%>
<%@page import="com.dimata.harisma.session.attendance.employeeoutlet.SessEmployeeOutlet"%>
<%@page import="com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet"%>
<%@page import="com.dimata.harisma.entity.attendance.employeeoutlet.EmployeeOutlet"%>
<%@page import="com.dimata.harisma.form.attendance.employeeoutlet.CtrlEmployeeOutlet"%>
<%@page import="com.dimata.gui.jsp.ControlDatePopup"%>
<%@page import="com.dimata.harisma.form.attendance.employeeoutlet.FrmEmployeeOutlet"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.util.LogicParser"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.startdata.PstOutletStart"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_OUTLET, AppObjInfo.OBJ_EMPLOYEE_OUTLET);%>
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

    
//cek tipe browser, browser detection
    //String userAgent = request.getHeader("User-Agent");
    //boolean isMSIE = (userAgent!=null && userAgent.indexOf("MSIE") !=-1); 

%>
<%!    public static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
//update by devin 2014-04-24
    public static String drawlistLocation(JspWriter outJsp, String locationSelected) {
        String result = "";

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(1);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("No", "5%", "2", "0");
        ctrlist.addHeader("Sub Regency", "5%", "2", "0");
        //ctrlist.addHeader("Nama", "50%","0","5");
        ctrlist.addHeader("Location Nama", "60%", "0", "5");
        ctrlist.addHeader("1", "10%", "0", "0");
        ctrlist.addHeader("2", "10%", "0", "0");
        ctrlist.addHeader("3", "10%", "0", "0");
        ctrlist.addHeader("4", "10%", "0", "0");
        ctrlist.addHeader("5", "10%", "0", "0");
        // ctrlist.addHeader("6", "10%","0", "0");


        //ctrlist.setLinkRow(0);
        //ctrlist.setLinkSufix(""); 
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        //ctrlist.setLinkPrefix("javascript:cmdEdit('");
        //ctrlist.setLinkSufix("')");
        ctrlist.reset();


        int no = 0;
        int start = 0;
        int recordToGet = 5;
        //Hashtable findLocation=PstLocation.findLocation(); 
        Vector regency = new Vector();
        long oidReg = 0;
        Vector dataRegency = new Vector();
        Vector listSubRegency = new Vector();
        if (locationSelected != null && locationSelected.length() > 0) {
            String where = "loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + " IN (" + locationSelected + ")";
            String order = PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID];
            listSubRegency = PstLocation.listJoinWithRegency(0, 0, where, order);
            if (listSubRegency != null && listSubRegency.size() > 0) {
                for (int cv = 0; cv < listSubRegency.size(); cv++) {
                    Location loc = (Location) listSubRegency.get(cv);
                    if (oidReg != loc.getSubRegencyId()) {
                        oidReg = loc.getSubRegencyId();
                        dataRegency.add(oidReg);
                    }
                }
            }
        }

        if (listSubRegency != null && listSubRegency.size() > 0) {
            ctrlist.drawListHeaderWithJsVer2SelectTable(outJsp);//header 
            //ctrlist.drawLfromLimitistHeaderWithJsVer2(outJsp);
            try {
                boolean chkLokasiNew = true;
                long oidRegency = 0;
                String test = "";
                Hashtable hashDataRegencySudahAda = new Hashtable();
                for (int x = 0; x < dataRegency.size(); x++) {
                    //Vector rowx = new Vector(1,1);
                    ControlList ctControlList = new ControlList();
                    //Vector rowX=new Vector();
                    Location location = new Location();
                    Kecamatan kecamatan = new Kecamatan();
                    if (chkLokasiNew) {
                        location = (Location) listSubRegency.get(x);
                        oidRegency = (Long) dataRegency.get(x);

                    }
                    if (listSubRegency != null && listSubRegency.size() > 0) {
                        test = "";
                        for (int b = 0; b < listSubRegency.size(); b++) {

                            Location lc = (Location) listSubRegency.get(b);
                            if (oidRegency == lc.getSubRegencyId()) {
                                test = test + lc.getOID() + ",";
                            }

                        }
                        if (test != null && test.length() > 0) {
                            test = test.substring(0, test.length() - 1);
                        }
                    }


                    Vector listLocation = PstLocation.listJoinWithRegency(start, recordToGet, PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + " IN (" + test + ")", "");
                    //if(listLocation!=null && listLocation.size()>0 && namacc.length()>0){ 
                    if (listLocation != null && listLocation.size() > 0) {
                        start = start + 5;
                        //toLimit=toLimit+5;
                        //recortToGet=5;
                        x = x - 1;

                    } else {
                        start = 0;
                        //recortToGet=5; 
                        chkLokasiNew = true;
                    }


                    if (listLocation != null && listLocation.size() > 0) {



                        int locx = 0;
                        boolean chkNoCreateNameRegency = true;
                        for (int i = 0; i < listLocation.size(); i++) {
                            Location dev = (Location) listLocation.get(i);
                            if (chkNoCreateNameRegency) {
                                if (hashDataRegencySudahAda != null && hashDataRegencySudahAda.containsKey("" + dev.getSubRegencyId())) {
                                    ctControlList.addColoms("", "0", "0");
                                    ctControlList.addColoms("", "0", "0");
                                } else {
                                    no = no + 1;
                                    ctControlList.addColoms("" + no, "0", "0");
                                    ctControlList.addColoms("" + dev.getRegencyName(), "0", "0");
                                    hashDataRegencySudahAda.put("" + dev.getSubRegencyId(), true);
                                }

                            }
                            // location = (Location)listLocation.get(i); 
                            locx = locx + 1;
                            if (dev.getSubRegencyId() == oidRegency) {
                                if (locx < 6) {
                                    //rowX.add(location.getName()); 
                                    ctControlList.addColoms("<input type=\"checkbox\" checked=\"checked\" name=\"locationSearchId\" value=\"" + dev.getOID() + "\" >" + dev.getName(), "0", "0");
                                    //listLocation.remove(i);
                                    //i=i-1;
                                    chkLokasiNew = true;
                                    chkNoCreateNameRegency = false;
                                    int data = listLocation.size();

                                    if ((5 - data) != 0 && (data - i) == 1) {
                                        int hasil = 5 - data;
                                        for (int vx = 0; vx < hasil; vx++) {
                                            ctControlList.addColoms("", "0", "0");
                                        }
                                    }

                                    if ((locx + 1) == 6) {
                                        chkLokasiNew = true;
                                        chkNoCreateNameRegency = true;
                                        locx = 0;
                                    }
                                }
                            }
                        }

                        ctrlist.drawListRowJsVer2CoolspanRowsPan(outJsp, 0, ctControlList, x);
                    }


                }

            } catch (Exception exc) {
                System.out.println(exc);
            }

            result = "";
            ctrlist.drawListEndTableJsVer2(outJsp);
        } else {
            result = "<i>Belum ada data dalam sistem ...</i>";
        }
        return result;
    }


%>
<%!    public String drawList(JspWriter outJsp, Vector objectClass, Date dtStart, Date dtEnd, Hashtable hashScheduleSymbol, Hashtable hashSchedule, int vMode) {
        String result = "";

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(2);
        ctrlist.setIdStlyeFreezing("GridView2");
        //mengambil nama dari kode komponent
        ctrlist.addHeader("No", "5%");
        ctrlist.addHeader("Employee Nr.", "5%");
        ctrlist.addHeader("Nama", "50%");
        if(vMode == 2){
            ctrlist.addHeader("Jabatan", "30%");
        }
        ctrlist.addHeader("Selected <br> <a href=\"Javascript:SetAllCheckBoxes('FRM_EMPLOYEE_OUTLET','userSelect', true)\">All</a> | <a href=\"Javascript:SetAllCheckBoxes('FRM_EMPLOYEE_OUTLET','userSelect', false)\">Deselect All</a>", "7%");
        long diffStartToFinish = 0;
        int itDate = 0;
        if (dtStart != null && dtEnd != null) {
            diffStartToFinish = dtEnd.getTime() - dtStart.getTime();
            if (diffStartToFinish >= 0) {
                itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
                
                for (int idx = 0; idx <= itDate; idx++) {
                    Date selectedDate = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate() + idx));
                    Date selectedDatePrev = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate() + (idx >= 0 ? idx - 1 : idx)));
                    SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                    String dayString = formatterDay.format(selectedDate);
                    if (vMode == 1){
                    if (idx != 0 && selectedDate.getYear() == selectedDatePrev.getYear()) {
                        if (dayString.equalsIgnoreCase("Sat")) {
                            ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%" + "</font>");
                        } else if (dayString.equalsIgnoreCase("Sun")) {
                            ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%" + "</font>");
                        } else {
                            ctrlist.addHeader(Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%");
                        }

                    } else {
                        if (dayString.equalsIgnoreCase("Sat")) {
                            ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%", "5%" + "</font>");
                        } else if (dayString.equalsIgnoreCase("Sun")) {
                            ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%", "5%" + "</font>");
                        } else {
                            ctrlist.addHeader(Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%");
                        }
                    }
                    } else {
                        ctrlist.addHeader(Formater.formatDate(selectedDate, "dd"), "5%");
                    }
                    
                    
                }
            }
        }

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        
        int no = 0;
        if (objectClass != null && objectClass.size() > 0) {
            ctrlist.drawListHeaderWithJsVer2SelectTable(outJsp);//header
            //ctrlist.drawListHeaderWithJsVer2(outJsp);
            try {
                for (int x = 0; x < objectClass.size(); x++) {
                    //Vector rowx = new Vector(1,1);
                    ControlList ctControlList = new ControlList();
                    no = no + 1;
                    Employee employee = (Employee) objectClass.get(x);
                    ctControlList.addColoms("" + no, "0", "0");
                    //rowx.add(""+no);
                    ctControlList.addColoms(employee.getEmployeeNum() == null || employee.getEmployeeNum().length() == 0 ? "-" : employee.getEmployeeNum(), "0", "0");
                    //rowx.add(employee.getEmployeeNum()==null || employee.getEmployeeNum().length()==0?"-":employee.getEmployeeNum());
                    ctControlList.addColoms(employee.getFullName() == null || employee.getFullName().length() == 0 ? "-" : employee.getFullName(), "0", "0");
                    //rowx.add(employee.getFullName()==null || employee.getFullName().length()==0?"-":employee.getFullName()); 
                    if(vMode == 2) {
                        // result Position by mchen
                        Vector listPosition = PstPosition.list(0, 0, "POSITION_ID = " + employee.getPositionId(), "");
                        if(listPosition != null && listPosition.size() > 0){
                            for(int idxPos=0; idxPos < listPosition.size(); idxPos++){
                                Position pos = (Position) listPosition.get(idxPos);
                                ctControlList.addColoms(pos.getPosition(), "0", "0");
                            }
                        }
                        // end result position
                    }    
                    ctControlList.addColoms("<input type=\"checkbox\" id=\"userSelect\" name=\"userSelect\" value=\"" + employee.getOID() + "\">", "0", "0");
                    //rowx.add("<input type=\"checkbox\" name=\"userSelect\" value=\""+employee.getOID()+"\">");  
                    Hashtable hashCekTanggal = new Hashtable();
                    Hashtable hashListCekDateSame = new Hashtable();
                    // Hashtable hashSessEmpOutlet = new Hashtable(); 
                    Vector vSessEmployeeOutletPrev = new Vector();
                    
                    for (int idx = 0; idx <= itDate; idx++) {
                        Date selectedDate = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate() + idx));
                        Date dtPrevSelected = new Date(selectedDate.getTime() - 1000 * 60 * 60 * 24);
                        Vector listEmpOutlet = PstEmployeeOutlet.listEmployeeOutletJoin(0, 0, "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employee.getOID(), "eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM], selectedDate, selectedDate);
                        if (listEmpOutlet != null && listEmpOutlet.size() > 0) {
                            for (int idxOT = 0; idxOT < listEmpOutlet.size(); idxOT++) {
                                SessEmployeeOutlet sessEmployeeOutlet = (SessEmployeeOutlet) listEmpOutlet.get(idxOT);
                                long lDateSelected = selectedDate != null ? selectedDate.getTime() : 0;
                                //String dtSelected = employee.getOID()+"_"+lDateSelected+"_"+sessEmployeeOutlet.getEmployeeOutletId();
                                sessEmployeeOutlet.setLinkPopUpEditOutletMapping("javascript:cmdEditEmployeeMapping('" + lDateSelected + "','" + sessEmployeeOutlet.getEmployeeOutletId() + "')");
                                if (sessEmployeeOutlet.getEmployeeId() == employee.getOID()) {
                                    //rumus mendapatkan kolomnya
                                    //kenapa di bagi 3? karena masing" garis ada 8 jadi 8 x 3 = 24 jam
                                    // jam berapa/ 3 = misalkan startnya mulai jam 7 maka : 7/3 = 2 1/3 jadi kolomnya menjadi 2
                                    // lalu kolom = 2 - 8; hasilnya kolom = 6;
                                    /*int colom = Math.abs((sessEmployeeOutlet.getDtFrom().getHours()/3) - 8);
                                     int colomEnd = Math.abs((sessEmployeeOutlet.getDtTo().getHours()/3) - 8);
                                     int colomCenter = Math.abs((0/3) - 8);*/
                                    int colom = 0;//Math.abs((sessEmployeeOutlet.getDtFrom().getHours()/3) - 8);
                                    int colomEnd = 0;//Math.abs((sessEmployeeOutlet.getDtTo().getHours()/3) - 8);
                                    // int colomCenter = 0;//Math.abs((0/3) - 8); 
                                    boolean isDtCurrentCrossDay = false;
                                    boolean isDtPrevCrossDay = false;
                                    if (hashSchedule != null && hashSchedule.get("" + sessEmployeeOutlet.getEmployeeId()) != null) {
                                        EmployeeSchedule employeeSchedule = (EmployeeSchedule) hashSchedule.get(""+sessEmployeeOutlet.getEmployeeId());



                                        long oidSchedule = employeeSchedule.getEmpSchedulesId1st("" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "_" + sessEmployeeOutlet.getEmployeeId());
                                        //long oidSchedule2nd = employeeSchedule.getEmpSchedulesId2st(""+Formater.formatDate(selectedDate, "yyyy-MM-dd")+"_"+sessEmployeeOutlet.getEmployeeId()); 

                                        //Date TimeIn = null;
                                        //if(sessEmployeeOutlet.getTypeSchedule()==PstScheduleSymbol.SCHEDULE_TYPE_NORMAL){
                                        if (hashScheduleSymbol != null && hashScheduleSymbol.get("" + oidSchedule) != null) {
                                            ScheduleSymbol symbol = (ScheduleSymbol) hashScheduleSymbol.get("" + oidSchedule);
                                            if (symbol != null && symbol.getSymbol() != null && symbol.getTimeIn() != null && symbol.getTimeOut() != null) {
                                                sessEmployeeOutlet.setSymbolSchedule(symbol.getSymbol());
                                                sessEmployeeOutlet.setBreakIn(symbol.getBreakIn());
                                                sessEmployeeOutlet.setBreakOut(symbol.getBreakOut());
                                                sessEmployeeOutlet.setTimeIn(symbol.getTimeIn());
                                                sessEmployeeOutlet.setTimeOut(symbol.getTimeOut());
                                                colom = Math.abs((symbol.getTimeIn().getHours() / 3));
                                                colomEnd = Math.abs((symbol.getTimeOut().getHours() / 3)) == 0 ? 8 : Math.abs((symbol.getTimeOut().getHours() / 3));
                                                //hashSessEmpOutlet.put(Formater.formatDate(selectedDate, "yyyy-MM-dd")+"_"+sessEmployeeOutlet.getEmployeeId(), sessEmployeeOutlet); 
                                                String whereClause = selectedDate == null || sessEmployeeOutlet.getEmployeeId() == 0 ? "" : "'" + Formater.formatDate(selectedDate, "yyyy-MM-dd 23:59:59") + "'" + " >= " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= " + "'" + Formater.formatDate(selectedDate, "yyyy-MM-dd 00:00:00") + "'" + " AND " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=" + sessEmployeeOutlet.getEmployeeId();
                                                if (whereClause != null && whereClause.length() > 0) {
                                                    //vSessEmployeeOutletPrev = PstEmployeeOutlet.list(0, 1, whereClause, "");
                                                    vSessEmployeeOutletPrev = PstEmployeeOutlet.listEmployeeOutletJoin(0, 1, "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + sessEmployeeOutlet.getEmployeeId(), PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " ASC ", dtPrevSelected, dtPrevSelected);
                                                }
                                                if (symbol.getTimeIn().getTime() > symbol.getTimeOut().getTime() && (symbol.getTimeOut().getHours() == 0 && symbol.getTimeOut().getMinutes() == 0)) {
                                                    //isCrossDay=true;
                                                    //karena jika sudah cross otomatis sdh pasti belakangnya full
                                                    colom = 8;//Math.abs((symbol.getTimeOut().getHours()/3));
                                                    colomEnd = Math.abs((symbol.getTimeIn().getHours() / 3));

                                                } else if (symbol.getTimeIn().getTime() > symbol.getTimeOut().getTime()) {
                                                    //karena jika sudah cross otomatis sdh pasti belakangnya full
                                                    colom = 8;//Math.abs((symbol.getTimeOut().getHours()/3))==0?8:Math.abs((symbol.getTimeOut().getHours()/3));
                                                    isDtCurrentCrossDay = true;
                                                    colomEnd = Math.abs((symbol.getTimeIn().getHours() / 3));
                                                }

                                                long oidSchedulePrev = employeeSchedule.getEmpSchedulesId1st("" + Formater.formatDate(dtPrevSelected, "yyyy-MM-dd") + "_" + sessEmployeeOutlet.getEmployeeId());
                                                if (hashScheduleSymbol.get("" + oidSchedulePrev) != null) {
                                                    ScheduleSymbol prevSymbol = (ScheduleSymbol) hashScheduleSymbol.get("" + oidSchedulePrev);
                                                    if (prevSymbol != null && prevSymbol.getSymbol() != null && prevSymbol.getTimeIn() != null && prevSymbol.getTimeOut() != null) {
                                                        int widthSpacePemisah = Math.abs(((prevSymbol.getTimeOut().getHours() - symbol.getTimeIn().getHours()) / 3));
                                                        sessEmployeeOutlet.setWidthSpacePemisah(widthSpacePemisah);
                                                        if (prevSymbol.getTimeIn().getTime() > prevSymbol.getTimeOut().getTime() && (prevSymbol.getTimeOut().getHours() == 0 && prevSymbol.getTimeOut().getMinutes() == 0)) {
                                                            //masih belum cross day 
                                                        } else if (prevSymbol.getTimeIn().getTime() > prevSymbol.getTimeOut().getTime()) {
                                                            isDtPrevCrossDay = true;
                                                            if (vSessEmployeeOutletPrev != null && vSessEmployeeOutletPrev.size() > 0) {
                                                                if (vSessEmployeeOutletPrev.get(0) != null) {
                                                                    SessEmployeeOutlet prevSessEmployeeOutlet = (SessEmployeeOutlet) vSessEmployeeOutletPrev.get(0);
                                                                    sessEmployeeOutlet.setLocationColorPrev(prevSessEmployeeOutlet.getLocationColor());
                                                                    sessEmployeeOutlet.setLocationNamePrev(prevSessEmployeeOutlet.getLocationName());
                                                                    //sessEmployeeOutlet.setPositionKodePrev(prevSessEmployeeOutlet.getPositionKode());
                                                                    sessEmployeeOutlet.setPositionPrev(prevSessEmployeeOutlet.getPosition());
                                                                }

                                                            }
                                                            sessEmployeeOutlet.setPrevSymbolSchedule(prevSymbol.getSymbol());
                                                            sessEmployeeOutlet.setPrevTimeIn(prevSymbol.getTimeIn());
                                                            sessEmployeeOutlet.setPrevTimeOut(prevSymbol.getTimeOut());
                                                            int prevColom = Math.abs((prevSymbol.getTimeIn().getHours() / 3));
                                                            //int prevColom = Math.abs((prevSymbol.getTimeIn().getHours() / 3) - 8);
                                                            int prevColomEnd = Math.abs((prevSymbol.getTimeOut().getHours() / 3));
                                                            //int prevColomEnd = Math.abs((prevSymbol.getTimeOut().getHours() / 3) - 8);
                                                            sessEmployeeOutlet.setPrevColomStart(prevColom);
                                                            sessEmployeeOutlet.setPrevColomEnd(prevColomEnd);

                                                        }
                                                    }
                                                }
                                            }

                                        }
                                        //}
                         /*else{    
                                         if(hashScheduleSymbol!=null && hashScheduleSymbol.get(""+oidSchedule2nd)!=null){
                                         ScheduleSymbol symbol = (ScheduleSymbol)hashScheduleSymbol.get(""+oidSchedule2nd);
                                         if(symbol!=null && symbol.getSymbol()!=null){
                                         sessEmployeeOutlet.setSymbolSchedule(symbol.getSymbol()); 
                                         }
                               
                                         }
                                         }*/

                                    }
                                    //String dtColom = sessEmployeeOutlet.getObjTableOutlet(colom, colomEnd, sessEmployeeOutlet, false, false);
                                    String dtColom;
                                    if (vMode == 1){
                                        dtColom = sessEmployeeOutlet.getObjTableOutlet(colom, colomEnd, sessEmployeeOutlet, isDtCurrentCrossDay, isDtPrevCrossDay);
                                    } else {
                                        dtColom = sessEmployeeOutlet.getObjTableOutletStyleWithoutTime(colom, colomEnd, sessEmployeeOutlet, isDtCurrentCrossDay, isDtPrevCrossDay);
                                    }
                                    //String dtEndColom = sessEmployeeOutlet.getObjTableOutletEnd(colomEnd, sessEmployeeOutlet);
                                    //String dtColomCenter = sessEmployeeOutlet.getObjTableOutletStart(colomCenter,sessEmployeeOutlet,false);
                                    Date dtCompareStart = sessEmployeeOutlet.getDtFrom();
                                    dtCompareStart.setHours(0);
                                    dtCompareStart.setMinutes(0);
                                    dtCompareStart.setSeconds(0);
                                    Date dtCompareEnd = sessEmployeeOutlet.getDtTo();
                                    dtCompareEnd.setHours(0);
                                    dtCompareEnd.setMinutes(0);
                                    dtCompareEnd.setSeconds(0);
                                    if (selectedDate.compareTo(dtCompareStart) == 0 && (hashListCekDateSame.size() == 0 || hashListCekDateSame.containsKey(sessEmployeeOutlet.getDtFrom()) == false)) {
                                        ctControlList.addColoms("" + dtColom, "0", "0");
                                        hashListCekDateSame.put(sessEmployeeOutlet.getDtFrom(), sessEmployeeOutlet.getEmployeeId());
                                    } else if (selectedDate.compareTo(dtCompareEnd) == 0 && (hashListCekDateSame.size() == 0 || hashListCekDateSame.containsKey(sessEmployeeOutlet.getDtTo()) == false)) {
                                        ctControlList.addColoms("" + dtColom, "0", "0");
                                        hashListCekDateSame.put(sessEmployeeOutlet.getDtTo(), sessEmployeeOutlet.getEmployeeId());
                                    } else {
                                        if (hashListCekDateSame != null && hashListCekDateSame.size() > 0 || hashListCekDateSame.get(selectedDate) == null) {
                                            //long empHashCheck = (Long)hashListCekDateSame.get(selectedDate);
                                            if (hashListCekDateSame.containsKey(selectedDate) == false) { //&& empHashCheck==sessEmployeeOutlet.getEmployeeId()){ 
                                                ctControlList.addColoms("" + dtColom, "0", "0");
                                            }
                                        }

                                    }
                                }
                            }
                        } else if (hashCekTanggal.size() == 0 || hashCekTanggal.containsKey(selectedDate) == false) {
                            if (hashSchedule != null && hashSchedule.get("" + employee.getOID()) != null) {
                                EmployeeSchedule employeeSchedule = (EmployeeSchedule) hashSchedule.get("" + employee.getOID());
                                //Date dtPrevSelected = new Date(selectedDate.getTime() - 1000 * 60 * 60 * 24);
                                long oidSchedulePrev = employeeSchedule.getEmpSchedulesId1st("" + Formater.formatDate(dtPrevSelected, "yyyy-MM-dd") + "_" + employee.getOID());
                                long oidSchedule = employeeSchedule.getEmpSchedulesId1st("" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "_" + employee.getOID());
                                SessEmployeeOutlet sessEmployeeOutlet = new SessEmployeeOutlet();
                                long lDateSelected = selectedDate != null ? selectedDate.getTime() : 0;
                                sessEmployeeOutlet.setLinkPopUpEditOutletMapping("javascript:cmdEditEmployeeMapping('" + lDateSelected + "','" + sessEmployeeOutlet.getEmployeeOutletId() + "')");

                                boolean isDtPrevCrossDay = false;
                                ///tidak ada data
                                if (hashScheduleSymbol.get("" + oidSchedulePrev) != null && hashScheduleSymbol.get("" + oidSchedule) != null) {
                                    ScheduleSymbol prevSymbol = (ScheduleSymbol) hashScheduleSymbol.get("" + oidSchedulePrev);
                                    ScheduleSymbol symbol = (ScheduleSymbol) hashScheduleSymbol.get("" + oidSchedule);
                                    if (prevSymbol != null && prevSymbol.getSymbol() != null && prevSymbol.getTimeIn() != null && prevSymbol.getTimeOut() != null && symbol != null && symbol.getSymbol() != null && symbol.getTimeIn() != null && symbol.getTimeOut() != null) {
                                        int widthSpacePemisah = Math.abs(((prevSymbol.getTimeOut().getHours() - symbol.getTimeIn().getHours()) / 3));
                                        sessEmployeeOutlet.setWidthSpacePemisah(widthSpacePemisah);
                                        if (prevSymbol.getTimeIn().getTime() > prevSymbol.getTimeOut().getTime() && (prevSymbol.getTimeOut().getHours() == 0 && prevSymbol.getTimeOut().getMinutes() == 0)) {
                                            //masih belum cross day 
                                        } else if (prevSymbol.getTimeIn().getTime() > prevSymbol.getTimeOut().getTime()) {
                                            isDtPrevCrossDay = true;
                                            if (vSessEmployeeOutletPrev != null && vSessEmployeeOutletPrev.size() > 0) {
                                                if (vSessEmployeeOutletPrev.get(0) != null) {
                                                    SessEmployeeOutlet prevSessEmployeeOutlet = (SessEmployeeOutlet) vSessEmployeeOutletPrev.get(0);
                                                    sessEmployeeOutlet.setLocationColorPrev(prevSessEmployeeOutlet.getLocationColor());
                                                    sessEmployeeOutlet.setLocationNamePrev(prevSessEmployeeOutlet.getLocationName());
                                                    //sessEmployeeOutlet.setPositionKodePrev(prevSessEmployeeOutlet.getPositionKode());
                                                    sessEmployeeOutlet.setPositionPrev(prevSessEmployeeOutlet.getPosition());
                                                }

                                            }
                                            sessEmployeeOutlet.setPrevSymbolSchedule(prevSymbol.getSymbol());
                                            sessEmployeeOutlet.setPrevTimeIn(prevSymbol.getTimeIn());
                                            sessEmployeeOutlet.setPrevTimeOut(prevSymbol.getTimeOut());
                                            int prevColom = Math.abs((prevSymbol.getTimeIn().getHours() / 3) - 8);
                                            int prevColomEnd = Math.abs((prevSymbol.getTimeOut().getHours() / 3) - 8);
                                            sessEmployeeOutlet.setPrevColomStart(prevColom);
                                            sessEmployeeOutlet.setPrevColomEnd(prevColomEnd);

                                        }
                                    }
                                    if (hashListCekDateSame != null && hashListCekDateSame.size() > 0 && hashListCekDateSame.containsKey(dtPrevSelected)) {
                                        String dtColom;
                                        if(vMode == 1){
                                            dtColom = sessEmployeeOutlet.getObjTableOutlet(0, 0, sessEmployeeOutlet, false, isDtPrevCrossDay);
                                        } else {
                                            dtColom = sessEmployeeOutlet.getObjTableOutletStyleWithoutTime(0, 0, sessEmployeeOutlet, false, isDtPrevCrossDay);
                                        }
                                        
                                        //String dtEndColom = sessEmployeeOutlet.getObjTableOutletEnd(colomEnd, sessEmployeeOutlet);
                                        //String dtColomCenter = sessEmployeeOutlet.getObjTableOutletStart(colomCenter,sessEmployeeOutlet,false);
                                        ctControlList.addColoms("" + dtColom, "0", "0");
                                    } else {
                                        String dtColom;
                                        if (vMode == 1){
                                           dtColom = sessEmployeeOutlet.getObjTableOutlet(0, 0, sessEmployeeOutlet, false, false); 
                                        } else {
                                           dtColom = sessEmployeeOutlet.getObjTableOutletStyleWithoutTime(0, 0, sessEmployeeOutlet, false, false);
                                        }
                                        
                                        ctControlList.addColoms("" + dtColom, "0", "0");
                                    }
                                } else {
                                    String dtColom;
                                    if (vMode == 1){
                                        dtColom = sessEmployeeOutlet.getObjTableOutlet(0, 0, sessEmployeeOutlet, false, false);
                                    } else {
                                        dtColom = sessEmployeeOutlet.getObjTableOutletStyleWithoutTime(0, 0, sessEmployeeOutlet, false, false);
                                    }
                                     
                                    ctControlList.addColoms("" + dtColom, "0", "0");
                                }
                            }//update by devin 2014-04-28
                            else if (hashSchedule != null && hashSchedule.get("" + employee.getOID()) == null) {
                                ctControlList.addColoms("Period Can't be Set", "0", "0");
                            }
                            hashCekTanggal.put(selectedDate, true);
                        }

                        //rowx.add(EndColom);  
                    }


                    /*for(int idx=0;idx<=itDate;idx++){
                     Date selectedDate = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate() + idx));
                     //ctrlist.addHeader(Formater.formatDate(selectedDate, "MM dd,yyyy"),"12%", "2", "0");
                     rowx.add("-"); 
                     }*/


                    ctrlist.drawListRowJsVer2CoolspanRowsPan(outJsp, 0, ctControlList, x);
                    //ctrlist.drawListRowJsVer2(outJsp, 0, rowx, x);

                }

            } catch (Exception exc) {
                System.out.println(exc);
            }

            result = "";
            ctrlist.drawListEndTableJsVer2(outJsp);
        } else {
            result = "<i>Belum ada data dalam sistem ...</i>";
        }
        return result;
    }
%>
<%
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    String sourceOutlerSearch = FRMQueryString.requestString(request, "sourceOutlerSearch");
    long oidCompany = FRMQueryString.requestLong(request, "hidden_companyId");
    long oidDivision = FRMQueryString.requestLong(request, "hidden_divisionId");
    long oidSection = FRMQueryString.requestLong(request, "section");
    Date selectedDateFrom = FRMQueryString.requestDateVer3(request, "check_date_start");
    Date selectedDateTo = FRMQueryString.requestDateVer3(request, "check_date_finish");
    int iCommand = FRMQueryString.requestCommand(request);
    String tmpLocationSrc[] = request.getParameterValues("locationSearchId");
    String locationSearchId = "";
    if (tmpLocationSrc != null && tmpLocationSrc.length > 0) {
        for (int xs = 0; xs < tmpLocationSrc.length; xs++) {
            locationSearchId = locationSearchId + tmpLocationSrc[xs] + ",";
        }
        if (locationSearchId != null && locationSearchId.length() > 0) {
            locationSearchId = locationSearchId.substring(0, locationSearchId.length() - 1);

        }
    }
    //String locationSearchId = FRMQueryString.requestLong(request, "locationSearchId");
    long positionId = FRMQueryString.requestInt(request, "positionId");
    String empNumber = FRMQueryString.requestString(request, "emp_number");
    String fullName = FRMQueryString.requestString(request, "full_name");
    // McHen
    int viewMode = FRMQueryString.requestInt(request, "view_mode"); // mchen
    int chekedInculedeSearchDate = FRMQueryString.requestInt(request, "chekedDate");
    //int isChekedValue = FRMQueryString.requestInt(request, "activeInput");
    //long oidLocationEmployeeOutlet = FRMQueryString.requestInt(request, FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_LOCATION_ID]);
    //long oidPositionEmployeeOutlet = FRMQueryString.requestInt(request, FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_POSITION_ID]);
    //long oidScheduleEmployeeOutlet = FRMQueryString.requestLong(request, FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_SCHEDULE_SYMBOLE_ID]);
    //int typeSchedule = FRMQueryString.requestInt(request, FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_SCHEDULE_TYPE]);
    Date selectedDateFromEmployeeOutlet = FRMQueryString.requestDateVer3(request, FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_DATE_FROM]);
    Date selectedDateToEmployeeOutlet = FRMQueryString.requestDateVer3(request, FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_DATE_TO]);
    long oidEmployeeOutlet = FRMQueryString.requestLong(request, FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_OUTLET_EMPLOYEE_ID]);
    SessSearchEmployeeMapping sessSearchEmployeeMapping = new SessSearchEmployeeMapping();
    if (sourceOutlerSearch != null && sourceOutlerSearch.equalsIgnoreCase("outletedit")) {

        sessSearchEmployeeMapping.setOidDepartment(oidDepartment);
        sessSearchEmployeeMapping.setOidCompany(oidCompany);
        sessSearchEmployeeMapping.setOidDivision(oidDivision);
        sessSearchEmployeeMapping.setOidSection(oidSection);
        sessSearchEmployeeMapping.setSelectedDateFrom(selectedDateFrom);
        sessSearchEmployeeMapping.setSelectedDateTo(selectedDateTo);
        sessSearchEmployeeMapping.setLocationSearchId(locationSearchId);
        sessSearchEmployeeMapping.setPositionId(positionId);
        sessSearchEmployeeMapping.setEmpNumber(empNumber);
        sessSearchEmployeeMapping.setFullName(fullName);


        sessSearchEmployeeMapping.setSelectedDateFromEmployeeOutlet(selectedDateFromEmployeeOutlet);
        sessSearchEmployeeMapping.setSelectedDateToEmployeeOutlet(selectedDateToEmployeeOutlet);
        sessSearchEmployeeMapping.setOidEmployeeOutlet(oidEmployeeOutlet);
        session.putValue(SessSearchEmployeeMapping.SESS_SRC_EMPLOYEE_OUTLET, sessSearchEmployeeMapping);
    } else {
        try {
            if (iCommand == Command.REFRESH) {
                sessSearchEmployeeMapping = (SessSearchEmployeeMapping) session.getValue(SessSearchEmployeeMapping.SESS_SRC_EMPLOYEE_OUTLET);
                oidDepartment = sessSearchEmployeeMapping.getOidDepartment();
                oidCompany = sessSearchEmployeeMapping.getOidCompany();
                oidDivision = sessSearchEmployeeMapping.getOidDivision();
                oidSection = sessSearchEmployeeMapping.getOidSection();
                selectedDateFrom = sessSearchEmployeeMapping.getSelectedDateFrom();
                selectedDateTo = sessSearchEmployeeMapping.getSelectedDateTo();
                locationSearchId = sessSearchEmployeeMapping.getLocationSearchId();
                positionId = sessSearchEmployeeMapping.getPositionId();
                empNumber = sessSearchEmployeeMapping.getEmpNumber();
                fullName = sessSearchEmployeeMapping.getFullName();


                selectedDateFromEmployeeOutlet = sessSearchEmployeeMapping.getSelectedDateFromEmployeeOutlet();
                selectedDateToEmployeeOutlet = sessSearchEmployeeMapping.getSelectedDateToEmployeeOutlet();
                oidEmployeeOutlet = sessSearchEmployeeMapping.getOidEmployeeOutlet();
            }
        } catch (Exception exc) {
        }

    }
    //update by devin 2014-04-23

    String whereClause = " (1=1)";
    if (oidCompany != 0) {
        whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + oidCompany;
    }
    if (oidDivision != 0) {
        whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + oidDivision;
    }
    if (oidDepartment != 0) {
        whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDepartment;
    }
    if (oidSection != 0) {
        whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + oidSection;
    }

    if (positionId != 0) {
        whereClause = whereClause + " AND eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_POSITION_ID] + " = " + positionId;
    }

    if (locationSearchId != null && locationSearchId.length() > 0) {
        whereClause = whereClause + " AND eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " IN ( " + locationSearchId + ")";
    }

    if (chekedInculedeSearchDate != 0 && selectedDateFrom != null && selectedDateTo != null) {
        if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
            Date tempFromDate = selectedDateFrom;
            Date tempToDate = selectedDateTo;
            selectedDateFrom = tempToDate;
            selectedDateTo = tempFromDate;
        }
        whereClause = whereClause + " AND \"" + Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:59") + "\" >= eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + ">= \"" + Formater.formatDate(selectedDateFrom, "yyyy-MM-dd HH:mm:59") + "\"";
    }

    if (selectedDateFrom != null && selectedDateTo != null) {
        whereClause = whereClause + "  AND (( " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                + " = " + PstEmployee.YES_RESIGN + " AND " + "" + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                + " BETWEEN \"" + Formater.formatDate(selectedDateFrom, "yyyy-MM-dd  00:00:00") + "\"" + " AND " + "\"" + Formater.formatDate(selectedDateTo, "yyyy-MM-dd  23:59:59") + "\""
                + " ) OR (" + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                + " = " + PstEmployee.NO_RESIGN + "))";
    }
    if (empNumber != null && empNumber.length() > 0) {
        Vector vectName = logicParser(empNumber);
        whereClause = whereClause + " AND ";
        if (vectName != null && vectName.size() > 0) {
            //whereClause = whereClause + " AND (";

            whereClause = whereClause + " (";
            for (int i = 0; i < vectName.size(); i++) {
                String str = (String) vectName.get(i);
                if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                    whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                            + " LIKE '%" + str.trim() + "%' ";
                } else {
                    whereClause = whereClause + str.trim();
                }
            }
            whereClause = whereClause + ")";
        }
    }
    if (fullName != null && fullName.length() > 0) {
        Vector vectName = logicParser(fullName);
        whereClause = whereClause + " AND ";
        if (vectName != null && vectName.size() > 0) {
            //whereClause = whereClause + " AND (";

            whereClause = whereClause + " (";
            for (int i = 0; i < vectName.size(); i++) {
                String str = (String) vectName.get(i);
                if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                    whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                            + " LIKE '%" + str.trim() + "%' ";
                } else {
                    whereClause = whereClause + str.trim();
                }
            }
            whereClause = whereClause + ")";
        }
    }
    Vector listEmployee = PstEmployee.listJoinLocationOutlet(0, 0, whereClause, "HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);


    String sEMployeeId = PstEmployee.getSEmployeeIdOutlet(0, 0, whereClause, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
    //String whereClauseEmpOutlet = sEMployeeId != null && sEMployeeId.length() > 0 ? PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + " IN(" + sEMployeeId + ")" : "(1=1)";
    //Vector listEmpOutlet = PstEmployeeOutlet.listEmployeeOutletJoin(0, 0,whereClauseEmpOutlet, "emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM],selectedDateFrom,selectedDateTo); 
     /* Control LOcation */
    Hashtable hashTblPeriod = PstPeriod.hashlistTblPeriod(0, 0, "", "");
    Hashtable hashScheduleSymbol = PstScheduleSymbol.getHashTblScheduleVer2(0, 0, "", "");
    Date dtPrevSelected = new Date((selectedDateFrom != null ? selectedDateFrom.getTime() : new Date().getTime()) - 1000 * 60 * 60 * 24);
    Date nextPrevSelected = new Date((selectedDateTo != null ? selectedDateTo.getTime() : new Date().getTime()) + 1000 * 60 * 60 * 24);
    Hashtable hashSchedule = PstEmpSchedule.getSchedule(dtPrevSelected, nextPrevSelected, sEMployeeId, hashTblPeriod);
    CtrlEmployeeOutlet ctrlEmployeeOutlet = new CtrlEmployeeOutlet(request);
    FrmEmployeeOutlet frmEmployeeOutlet = ctrlEmployeeOutlet.getForm();
    int iErrCode = ctrlEmployeeOutlet.action(iCommand, oidEmployeeOutlet, hashSchedule, hashScheduleSymbol);
    EmployeeOutlet objEmployeeOutlet = ctrlEmployeeOutlet.getEmployeeOutlet();
    String msg = ctrlEmployeeOutlet.getMessage();

%>  
<html>
    <head>
        <title>Harisma - Employee Outlet</title>
        <%@ include file = "../../main/konfigurasi_jquery.jsp" %>    
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
            <!-- #EndEditable --> 
            <link rel="stylesheet" href="../../styles/main.css" type="text/css">
                <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
                    <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
                    <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
                    <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
                    <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>

                    <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
                    <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
                    <script type="text/javascript">

                        function changeHashOnLoad() {
                            window.location.href += "#";
                            setTimeout("changeHashAgain()", "50");
                        }

                        function changeHashAgain() {
                            window.location.href += "1";
                        }

                        var storedHash = window.location.hash;
                        window.setInterval(function() {
                            if (window.location.hash != storedHash) {
                                window.location.hash = storedHash;
                            }
                        }, 50);

                    </script>
                    <SCRIPT language=JavaScript>


                        var showMode = 'table-cell';
                        if (document.all)
                            showMode = 'block';
                        function disablefield(btn) {
                            btn = document.forms['<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>'].elements[btn];
                            cells = document.getElementsByName('t' + btn.name);

                            mode = btn.checked ? showMode : 'none';

                            for (j = 0; j < cells.length; j++)
                                cells[j].style.display = mode;

                            if (document.getElementById('active').checked == 1) {
                                //document.getElementById('locationHideen').disabled = '';
                                // document.getElementById('positionHideen').disabled = '';
                                // document.getElementById('scheduleHideen').disabled='';
                                //document.getElementById('inputanActive').style.display='block';
                            } else {
                                //document.getElementById('locationHideen').disabled = 'disabled';
                                // document.getElementById('positionHideen').disabled = 'disabled';
                                // document.getElementById('scheduleHideen').disabled='disabled';
                                //document.getElementById('inputanActive').style.display="none";
                            }
                            //alert(document.getElementById('inputanActive'));
                        }
                        function SetAllCheckBoxes(FormName, FieldName, CheckValue)
                        {
                            if (!document.forms[FormName])
                                return;
                            var objCheckBoxes = document.forms[FormName].elements[FieldName];
                            //alert(objCheckBoxes);
                            if (!objCheckBoxes)
                                return;
                            var countCheckBoxes = objCheckBoxes.length;
                            if (!countCheckBoxes)
                                objCheckBoxes.checked = CheckValue;
                            else
                                // set the check value for all check boxes
                                for (var i = 0; i < countCheckBoxes; i++)
                                    objCheckBoxes[i].checked = CheckValue;
                            //alert(objCheckBoxes[i]);
                        }
                        function searchEmployeeOutlet() {
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.command.value = "<%=Command.NONE%>";
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.action = "outlet.jsp";
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.submit();
                        }
                        function cmdRefresh() {
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.command.value = "<%=Command.REFRESH%>";
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.sourceOutlerSearch.value = "";
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.action = "outlet_edit.jsp";
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.target = "";
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.submit();
                        }
                        function cmdSave() {
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.command.value = "<%=Command.SAVE%>";
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.action = "outlet_edit.jsp";
                            document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.submit();

                        }

                        var chekTidakAdaDipilih = true;
                        function setSelected() {


                            if (!document.forms["<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>"])
                                return;
                            if (!document.forms["<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>"].elements["userSelect"])
                                return;
                            if (document.forms["<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>"].elements["userSelect"].length < 1)
                                return;
                            var parentCheckboxes = document.forms["<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>"].elements["userSelect"];

                            chekTidakAdaDipilih = true;
                            if (parentCheckboxes != null && parentCheckboxes.length > 0) {
                                for (count = 0; count < parentCheckboxes.length; count++) {
                                    if (parentCheckboxes[count].checked) {
                                        chekTidakAdaDipilih = false;
                                        return;
                                    }
                                }
                            }
                            if (chekTidakAdaDipilih) {
                                alert("Employee is not selected");
                            }

                        }



                        function cmdEditEmployeeMapping(selectedDate, outletEmployeeId) {
                            // alert(selectedDate);
                            // alert(outletEmployeeId);
                            setSelected();
                            if (chekTidakAdaDipilih == false) {
                                window.open("about:blank", "outletmapping", "status=no,toolbar=no,menubar=no,resizable=yes,scrollbars=yes,location=no");
                                //alert("Hi");
                                //popup.focus();
                                //popup.document.write("<p>This is 'myWindow'</p>");
                                window.focus();
                                document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.command.value = "<%=Command.EDIT%>";
                                //alert("1");
                                document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.source.value = "outlet_mapping";
                                //alert("2");
                                document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.selectedDateCheckBox.value = selectedDate;
                                document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.selectedOutletMappingCheckBox.value = outletEmployeeId;
                                //alert("2");
                                document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.action = "<%=approot%>/employee/outlet/outlet_mapping_edit.jsp";
                                //alert("3");                            
                                document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.target = "outletmapping";
                                //alert("4");

                                document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.submit();
                                document.<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>.target = "_self";
                            }
                        }
                        function hideObjectForEmployee() {
                        }

                        function hideObjectForLockers() {
                        }

                        function hideObjectForCanteen() {
                        }

                        function hideObjectForClinic() {
                        }

                        function hideObjectForMasterdata() {
                        }

                    </SCRIPT>
                    <!-- #EndEditable --> 
                    </head> 
                    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">

                        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
                            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
                            <%@include file="../../styletemplate/template_header.jsp" %>
                            <%} else {%>
                            <tr>
                                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">

                                    <%@ include file = "../../main/header.jsp" %>

                                </td>
                            </tr>
                            <tr>
                                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
                                    <%@ include file = "../../main/mnmain.jsp" %>
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
                                                                    Outlet &gt; Employee Outlet
                                                                </strong></font>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                                                <tr>
                                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                                            <tr>
                                                                                <td valign="top">
                                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                                        <tr>
                                                                                            <td valign="top">
                                                                                                <form name="<%=FrmEmployeeOutlet.FRM_EMPLOYEE_OUTLET%>" action="" method="post">
                                                                                                    <input type="hidden" name="command" value=""></input>
                                                                                                    <input type="hidden" name="view_mode" value="<%=viewMode%>" />
                                                                                                    <input type="hidden" name="sourceOutlerSearch" value="<%=sourceOutlerSearch%>"></input>

                                                                                                    <input type="hidden" name="source" value=""></input>
                                                                                                    <input type="hidden" name="selectedDateCheckBox" value=""></input>
                                                                                                    <input type="hidden" name="selectedOutletMappingCheckBox" value=""></input>
                                                                                                    <input type="hidden" name="emp_number" value="<%=empNumber%>"></input>
                                                                                                    <input type="hidden" name="full_name" value="<%=fullName%>"></input>
                                                                                                    <input type="hidden" name="hidden_companyId" value="<%=oidCompany%>"></input>
                                                                                                    <input type="hidden" name="hidden_divisionId" value="<%=oidDivision%>"></input>
                                                                                                    <input type="hidden" name="department" value="<%=oidDepartment%>"></input>
                                                                                                    <input type="hidden" name="section" value="<%=oidSection%>"></input>
                                                                                                    <%=ControlDatePopup.writeDateTimeDisabled("check_date_start", selectedDateFrom)%>
                                                                                                    <%=ControlDatePopup.writeDateTimeDisabled("check_date_finish", selectedDateTo)%>
                                                                                                    <input type="hidden" name="positionId" value="<%=positionId%>"></input> 

                                                                                                    <table  width="100%">
                                                                                                        <tr>
                                                                                                            <td valign="top">
                                                                                                                <fieldset>
                                                                                                                    <legend>Parameter Search:</legend>
                                                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2" >
                                                                                                                        <tr>
                                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Payrol Num </div></td>
                                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                                <input class="masterTooltip" type="text" size="40" name="emp_number"  disabled value="<%=empNumber%>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()"></td>
                                                                                                                            <td width="5%" nowrap="nowrap"> Full Name </td>
                                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                                <input class="masterTooltip" type="text" size="50" disabled name="full_name"  value="<%=fullName%>" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()">                                                         </td>
                                                                                                                        </tr>

                                                                                                                        <tr>
                                                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Company </div></td>
                                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                                <%
                                                                                                                                    Vector comp_value = new Vector(1, 1);
                                                                                                                                    Vector comp_key = new Vector(1, 1);
                                                                                                                                    String whereComp = "";
                                                                                                                                    Vector div_value = new Vector(1, 1);
                                                                                                                                    Vector div_key = new Vector(1, 1);

                                                                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                                                    if (processDependOnUserDept) {
                                                                                                                                        if (emplx.getOID() > 0) {
                                                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                                                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                                                comp_value.add("0");
                                                                                                                                                comp_key.add("select ...");

                                                                                                                                                div_value.add("0");
                                                                                                                                                div_key.add("select ...");

                                                                                                                                                dept_value.add("0");
                                                                                                                                                dept_key.add("select ...");
                                                                                                                                            } else {
                                                                                                                                                Position position = null;
                                                                                                                                                try {
                                                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                                } catch (Exception exc) {
                                                                                                                                                }
                                                                                                                                                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                                                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                                                                                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                                                                                                                    // comp_value.add("0");
                                                                                                                                                    // comp_key.add("select ...");

                                                                                                                                                    //div_value.add("0");
                                                                                                                                                    //div_key.add("select ...");

                                                                                                                                                    dept_value.add("0");
                                                                                                                                                    dept_key.add("select ...");

                                                                                                                                                    whereComp = whereComp != null && whereComp.length() > 0 ? whereComp + " AND (" + whereDiv + ")" : whereDiv;

                                                                                                                                                } else {

                                                                                                                                                    String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                                                                                                                            + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                                                                                                                    try {
                                                                                                                                                        String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                                                        Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                                                        int grpIdx = -1;
                                                                                                                                                        int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                                                                                                                        int countIdx = 0;
                                                                                                                                                        int MAX_LOOP = 10;
                                                                                                                                                        int curr_loop = 0;
                                                                                                                                                        do { // find group department belonging to curretn user base in departmentOid
                                                                                                                                                            curr_loop++;
                                                                                                                                                            String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                                                String comp = grp[g];
                                                                                                                                                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                                                    grpIdx = countIdx;   // A ha .. found here 
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                            countIdx++;
                                                                                                                                                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                                                                                                                        // compose where clause
                                                                                                                                                        if (grpIdx >= 0) {
                                                                                                                                                            String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                                                String comp = grp[g];
                                                                                                                                                                whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    } catch (Exception exc) {
                                                                                                                                                        System.out.println(" Parsing Join Dept" + exc);

                                                                                                                                                    }
                                                                                                                                                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);

                                                                                                                                                    whereComp = whereComp != null && whereComp.length() > 0 ? whereComp + " AND (" + whereClsDep + ")" : whereClsDep;

                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    } else {
                                                                                                                                        comp_value.add("0");
                                                                                                                                        comp_key.add("select ...");

                                                                                                                                        div_value.add("0");
                                                                                                                                        div_key.add("select ...");

                                                                                                                                        dept_value.add("0");
                                                                                                                                        dept_key.add("select ...");
                                                                                                                                    }
                                                                                                                                    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
                                                                                                                                    String prevCompany = "";
                                                                                                                                    String prevDivision = "";



                                                                                                                                    long prevCompanyTmp = 0;
                                                                                                                                    for (int i = 0; i < listCostDept.size(); i++) {
                                                                                                                                        Department dept = (Department) listCostDept.get(i);
                                                                                                                                        if (prevCompany.equals(dept.getCompany())) {
                                                                                                                                            if (prevDivision.equals(dept.getDivision())) {
                                                                                                                                                //if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                                                                                                                                                dept_key.add(dept.getDepartment());
                                                                                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                //}
                                                                                                                                            } else {
                                                                                                                                                div_key.add(dept.getDivision());
                                                                                                                                                div_value.add("" + dept.getDivisionId());
                                                                                                                                                if (dept_key != null && dept_key.size() == 0) {
                                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                }
                                                                                                                                                prevDivision = dept.getDivision();
                                                                                                                                            }
                                                                                                                                        } else {
                                                                                                                                            String chkAdaDiv = "";
                                                                                                                                            if (div_key != null && div_key.size() > 0) {
                                                                                                                                                chkAdaDiv = (String) div_key.get(0);
                                                                                                                                            }
                                                                                                                                            if ((div_key != null && div_key.size() == 0) || (chkAdaDiv.equalsIgnoreCase("select ..."))) {
                                                                                                                                                if (prevCompanyTmp != dept.getCompanyId()) {
                                                                                                                                                    comp_key.add(dept.getCompany());
                                                                                                                                                    comp_value.add("" + dept.getCompanyId());
                                                                                                                                                    prevCompanyTmp = dept.getCompanyId();
                                                                                                                                                }
                                                                                                                                                //untuk karyawan admin yg hanya bisa akses departement tertentu (ketika di awal)
                                                                                                                                                ////update
                                                                                                                                                if (processDependOnUserDept) {
                                                                                                                                                    if (emplx.getOID() > 0) {
                                                                                                                                                        if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                                                            if (oidCompany != 0) {
                                                                                                                                                                div_key.add(dept.getDivision());
                                                                                                                                                                div_value.add("" + dept.getDivisionId());

                                                                                                                                                                dept_key.add(dept.getDepartment());
                                                                                                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                                prevCompany = dept.getCompany();
                                                                                                                                                                prevDivision = dept.getDivision();
                                                                                                                                                            }
                                                                                                                                                        } else {
                                                                                                                                                            Position position = null;
                                                                                                                                                            try {
                                                                                                                                                                position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                                            } catch (Exception exc) {
                                                                                                                                                            }
                                                                                                                                                            if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                                                                if (oidCompany != 0) {
                                                                                                                                                                    div_key.add(dept.getDivision());
                                                                                                                                                                    div_value.add("" + dept.getDivisionId());

                                                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                                    prevCompany = dept.getCompany();
                                                                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                                                                } //update by satrya 2013-09-19
                                                                                                                                                                else if ((div_key != null && div_key.size() == 0) || (chkAdaDiv.equalsIgnoreCase("select ..."))) {
                                                                                                                                                                    div_key.add(dept.getDivision());
                                                                                                                                                                    div_value.add("" + dept.getDivisionId());

                                                                                                                                                                    //update by satrya 2013-09-19
                                                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));

                                                                                                                                                                    prevCompany = dept.getCompany();
                                                                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                                                                }

                                                                                                                                                            } else {

                                                                                                                                                                div_key.add(dept.getDivision());
                                                                                                                                                                div_value.add("" + dept.getDivisionId());

                                                                                                                                                                dept_key.add(dept.getDepartment());
                                                                                                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                                prevCompany = dept.getCompany();
                                                                                                                                                                prevDivision = dept.getDivision();
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                } else {
                                                                                                                                                    if (oidCompany != 0) {
                                                                                                                                                        div_key.add(dept.getDivision());
                                                                                                                                                        div_value.add("" + dept.getDivisionId());

                                                                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                        prevCompany = dept.getCompany();
                                                                                                                                                        prevDivision = dept.getDivision();
                                                                                                                                                    }
                                                                                                                                                }

                                                                                                                                            } else {
                                                                                                                                                if (prevCompanyTmp != dept.getCompanyId()) {
                                                                                                                                                    comp_key.add(dept.getCompany());
                                                                                                                                                    comp_value.add("" + dept.getCompanyId());
                                                                                                                                                    prevCompanyTmp = dept.getCompanyId();
                                                                                                                                                }

                                                                                                                                            }

                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                %>
                                                                                                                                <%= ControlCombo.draw("hidden_companyId", "formElemen", null, "" + oidCompany, comp_value, comp_key, "disabled=\"disabled\"")%> </td> 
                                                                                                                            <td width="5%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                                <%
                                                                                                                                    if (oidCompany == 0) {
                                                                                                                                        oidDivision = 0;
                                                                                                                                    }

                                                                                                                                    if (oidCompany != 0) {
                                                                                                                                        whereComp = "(" + (whereComp != null && whereComp.length() == 0 ? "1=1" : whereComp) + ") AND " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "=" + oidCompany;
                                                                                                                                        listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
                                                                                                                                        prevCompany = "";
                                                                                                                                        prevDivision = "";

                                                                                                                                        div_value = new Vector(1, 1);
                                                                                                                                        div_key = new Vector(1, 1);

                                                                                                                                        dept_value = new Vector(1, 1);
                                                                                                                                        dept_key = new Vector(1, 1);

                                                                                                                                        prevCompanyTmp = 0;
                                                                                                                                        long tmpFirstDiv = 0;

                                                                                                                                        if (processDependOnUserDept) {
                                                                                                                                            if (emplx.getOID() > 0) {
                                                                                                                                                if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                                                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                                                    comp_value.add("0");
                                                                                                                                                    comp_key.add("select ...");

                                                                                                                                                    div_value.add("0");
                                                                                                                                                    div_key.add("select ...");

                                                                                                                                                    dept_value.add("0");
                                                                                                                                                    dept_key.add("select ...");
                                                                                                                                                } else {
                                                                                                                                                    Position position = null;
                                                                                                                                                    try {
                                                                                                                                                        position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                                    } catch (Exception exc) {
                                                                                                                                                    }
                                                                                                                                                    if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                                                        dept_value.add("0");
                                                                                                                                                        dept_key.add("select ...");

                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        } else {
                                                                                                                                            comp_value.add("0");
                                                                                                                                            comp_key.add("select ...");

                                                                                                                                            div_value.add("0");
                                                                                                                                            div_key.add("select ...");

                                                                                                                                            dept_value.add("0");
                                                                                                                                            dept_key.add("select ...");
                                                                                                                                        }
                                                                                                                                        long prevDivTmp = 0;
                                                                                                                                        for (int i = 0; i < listCostDept.size(); i++) {
                                                                                                                                            Department dept = (Department) listCostDept.get(i);
                                                                                                                                            if (prevCompany.equals(dept.getCompany())) {
                                                                                                                                                if (prevDivision.equals(dept.getDivision())) {
                                                                                                                                                    //update
                                                                                                                                                    if (oidDivision != 0) {
                                                                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                    }
                                                                                                                                                } else {
                                                                                                                                                    div_key.add(dept.getDivision());
                                                                                                                                                    div_value.add("" + dept.getDivisionId());
                                                                                                                                                    if (dept_key != null && dept_key.size() == 0) {
                                                                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                    }
                                                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                                                }
                                                                                                                                            } else {
                                                                                                                                                String chkAdaDiv = "";
                                                                                                                                                if (div_key != null && div_key.size() > 0) {
                                                                                                                                                    chkAdaDiv = (String) div_key.get(0);
                                                                                                                                                }
                                                                                                                                                if ((div_key != null && div_key.size() == 0) || (chkAdaDiv.equalsIgnoreCase("select ..."))) {
                                                                                                                                                    if (prevDivTmp != dept.getDivisionId()) {
                                                                                                                                                        div_key.add(dept.getDivision());
                                                                                                                                                        div_value.add("" + dept.getDivisionId());
                                                                                                                                                        prevDivTmp = dept.getDivisionId();
                                                                                                                                                    }

                                                                                                                                                    tmpFirstDiv = dept.getDivisionId();
                                                                                                                                                    prevCompany = dept.getCompany();
                                                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                                                }
                                                                                                                                                String chkAdaDpt = "";
                                                                                                                                                if (whereComp != null && whereComp.length() > 0) {
                                                                                                                                                    chkAdaDpt = "(" + (whereComp != null && whereComp.length() == 0 ? "1=1" : whereComp) + ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                                }
                                                                                                                                                Vector listCheckAdaDept = PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt);
                                                                                                                                                if ((listCheckAdaDept == null || listCheckAdaDept.size() == 0)) {

                                                                                                                                                    if (processDependOnUserDept) {
                                                                                                                                                        if (emplx.getOID() > 0) {
                                                                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                                                            } else {
                                                                                                                                                                Position position = null;
                                                                                                                                                                try {
                                                                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                                                } catch (Exception exc) {
                                                                                                                                                                }

                                                                                                                                                                oidDivision = tmpFirstDiv;

                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    } else {
                                                                                                                                                        oidDivision = tmpFirstDiv;

                                                                                                                                                    }

                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                %>
                                                                                                                                <%= ControlCombo.draw("hidden_divisionId", "formElemen", null, "" + oidDivision, div_value, div_key, "disabled=\"disabled\"")%> </td>
                                                                                                                        </tr>
                                                                                                                        <tr>
                                                                                                                            <td width="6%" align="right" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                                                                                            <td width="30%" nowrap="nowrap"> :
                                                                                                                                <%

                                                                                                                                    //update by satrya 2013-08-13
                                                                                                                                    //jika user memilih select kembali
                                                                                                                                    if (oidDepartment == 0) {
                                                                                                                                        oidSection = 0;
                                                                                                                                    }
                                                                                                                                    if (oidDivision != 0) {
                                                                                                                                        if (whereComp != null && whereComp.length() > 0) {
                                                                                                                                            whereComp = "(" + whereComp + ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                        }

                                                                                                                                        listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
                                                                                                                                        prevCompany = "";
                                                                                                                                        prevDivision = "";

                                                                                                                                        div_value = new Vector(1, 1);
                                                                                                                                        div_key = new Vector(1, 1);

                                                                                                                                        dept_value = new Vector(1, 1);
                                                                                                                                        dept_key = new Vector(1, 1);

                                                                                                                                        prevCompanyTmp = 0;

                                                                                                                                        if (processDependOnUserDept) {
                                                                                                                                            if (emplx.getOID() > 0) {
                                                                                                                                                if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                                                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                                                    comp_value.add("0");
                                                                                                                                                    comp_key.add("select ...");

                                                                                                                                                    div_value.add("0");
                                                                                                                                                    div_key.add("select ...");

                                                                                                                                                    dept_value.add("0");
                                                                                                                                                    dept_key.add("select ...");
                                                                                                                                                } else {
                                                                                                                                                    Position position = null;
                                                                                                                                                    try {
                                                                                                                                                        position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                                    } catch (Exception exc) {
                                                                                                                                                    }
                                                                                                                                                    if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                                                        //div_value.add("0");
                                                                                                                                                        //div_key.add("select ...");

                                                                                                                                                        dept_value.add("0");
                                                                                                                                                        dept_key.add("select ...");

                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        } else {
                                                                                                                                            comp_value.add("0");
                                                                                                                                            comp_key.add("select ...");

                                                                                                                                            div_value.add("0");
                                                                                                                                            div_key.add("select ...");

                                                                                                                                            dept_value.add("0");
                                                                                                                                            dept_key.add("select ...");
                                                                                                                                        }

                                                                                                                                        for (int i = 0; i < listCostDept.size(); i++) {
                                                                                                                                            Department dept = (Department) listCostDept.get(i);
                                                                                                                                            if (prevCompany.equals(dept.getCompany())) {
                                                                                                                                                if (prevDivision.equals(dept.getDivision())) {
                                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                } else {
                                                                                                                                                    div_key.add(dept.getDivision());
                                                                                                                                                    div_value.add("" + dept.getDivisionId());
                                                                                                                                                    if (dept_key != null && dept_key.size() == 0) {
                                                                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                    }
                                                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                                                }
                                                                                                                                            } else {
                                                                                                                                                String chkAdaDiv = "";
                                                                                                                                                if (div_key != null && div_key.size() > 0) {
                                                                                                                                                    chkAdaDiv = (String) div_key.get(0);
                                                                                                                                                }
                                                                                                                                                if ((div_key != null && div_key.size() == 0) || (chkAdaDiv.equalsIgnoreCase("select ..."))) {
                                                                                                                                                    comp_key.add(dept.getCompany());
                                                                                                                                                    comp_value.add("" + dept.getCompanyId());


                                                                                                                                                    div_key.add(dept.getDivision());
                                                                                                                                                    div_value.add("" + dept.getDivisionId());

                                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                                    prevCompany = dept.getCompany();
                                                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                                                } else {
                                                                                                                                                    if (prevCompanyTmp != dept.getCompanyId()) {
                                                                                                                                                        comp_key.add(dept.getCompany());
                                                                                                                                                        comp_value.add("" + dept.getCompanyId());
                                                                                                                                                        prevCompanyTmp = dept.getCompanyId();
                                                                                                                                                    }

                                                                                                                                                }

                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                %>
                                                                                                                                <%= ControlCombo.draw("department", "formElemen", null, "" + oidDepartment, dept_value, dept_key, "disabled=\"disabled\"")%> </td>
                                                                                                                            <td width="5%" align="left" nowrap valign="top"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                                <%

                                                                                                                                    Vector sec_value = new Vector(1, 1);
                                                                                                                                    Vector sec_key = new Vector(1, 1);
                                                                                                                                    sec_value.add("0");
                                                                                                                                    sec_key.add("select ...");

                                                                                                                                    //String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
                                                                                                                                    //Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
                                                                                                                                    String secWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                                                    Vector listSec = PstSection.list(0, 0, secWhere, " SECTION ");
                                                                                                                                    for (int i = 0; i < listSec.size(); i++) {
                                                                                                                                        Section sec = (Section) listSec.get(i);
                                                                                                                                        sec_key.add(sec.getSection());
                                                                                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                                                                                                    }
                                                                                                                                %>
                                                                                                                                <%=ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key, "disabled=\"disabled\"")%></td>
                                                                                                                        </tr>
                                                                                                                        <tr>
                                                                                                                            <td width="6%" align="right" nowrap><div align=left>Date</div></td>
                                                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                                                <input type="hidden" name="chekedDate" value="<%=chekedInculedeSearchDate%>"/>
                                                                                                                                <%out.println(selectedDateFrom != null ? Formater.formatDate(selectedDateFrom, "dd MMM yyyy HH:mm") : "");%> to 
                                                                                                                                <%out.println(selectedDateTo != null ? Formater.formatDate(selectedDateTo, "dd MMM yyyy HH:mm") : "");%>

                                                                                                                            </td>  

                                                                                                                            <td width="5%" align="right" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div></td>
                                                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                                                <%
                                                                                                                                    Vector position_value = new Vector(1, 1);
                                                                                                                                    Vector position_key = new Vector(1, 1);

                                                                                                                                    Vector listPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);
                                                                                                                                    if (listPosition != null && listPosition.size() > 0) {
                                                                                                                                        for (int r = 0; r < listPosition.size(); r++) {
                                                                                                                                            Position position = (Position) listPosition.get(r);
                                                                                                                                            position_key.add(position.getPosition());
                                                                                                                                            position_value.add(String.valueOf(position.getOID()));
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                %>
                                                                                                                                <%=ControlCombo.draw("positionId", "select...", "" + positionId, position_value, position_key, "disabled=\"disabled\"")%> </td>
                                                                                                                        </tr>
                                                                                                                        <tr>
                                                                                                                            <td width="6%" align="right" nowrap><div align=left>Location</div></td>
                                                                                                                            <td colspan="3" nowrap="nowrap">:
                                                                                                                                <%

                                                                                                                                %>
                                                                                                                                <%%>              
                                                                                                                                <%
                                                                                                                                    if (tmpLocationSrc != null && tmpLocationSrc.length > 0) {
                                                                                                                                        drawlistLocation(out, locationSearchId);
                                                                                                                                    } else {
                                                                                                                                %>
                                                                                                                                no selected location
                                                                                                                                <%                                                                                                                                        }
                                                                                                                                %>
                                                                                                                            </td>  
                                                                                                                        </tr>
                                                                                                                        <tr>
                                                                                                                            <td></td>
                                                                                                                            <td><table width="100%">
                                                                                                                                    <td width="16"><a href="javascript:searchEmployeeOutlet()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSearchOn.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                                                                                                    <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                                                    <td class="command" nowrap><a href="javascript:searchEmployeeOutlet()">Back To Paramater Search</a></td>

                                                                                                                                </table></td> 
                                                                                                                        </tr>

                                                                                                                    </table>
                                                                                                                </fieldset>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <td>
                                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="100%">

                                                                                                                    <tr>
                                                                                                                        <td>
                                                                                                                            <fieldset>
                                                                                                                                <%
                                                                                                                                    boolean isChecked = iErrCode > 0 || msg.length() > 0 ? true : false;
                                                                                                                                %>
                                                                                                                                <legend><input type="checkbox" name="activeInput" value='1'  id='active'  onClick='disablefield(this.name);'/>Show Input Form for Employee to Outlet Mapping:</legend>
                                                                                                                                <table name="tactiveInput" id="tactiveInput" <%=isChecked ? "" : "style=\"display:none\""%>> 
                                                                                                                                    <tr >
                                                                                                                                        <td width="5%" nowrap="nowrap"> Outlet </td>
                                                                                                                                        <td width="59%" nowrap="nowrap">:
                                                                                                                                            <%
                                                                                                                                                Vector listLocation = PstLocation.list(0, 0, "", PstLocation.fieldNames[PstLocation.FLD_NAME]);
                                                                                                                                                Vector valueLoc = new Vector(1, 1);
                                                                                                                                                Vector keyLoc = new Vector(1, 1);
                                                                                                                                                Vector colorLocations = new Vector(1, 1);
                                                                                                                                                if (listLocation != null && listLocation.size() > 0) {
                                                                                                                                                    for (int x = 0; x < listLocation.size(); x++) {
                                                                                                                                                        Location location = (Location) listLocation.get(x);
                                                                                                                                                        valueLoc.add("" + location.getOID());
                                                                                                                                                        keyLoc.add(location.getName());
                                                                                                                                                        colorLocations.add("#" + location.getColorLocation());
                                                                                                                                                    }
                                                                                                                                                }

                                                                                                                                                String colors = "#FFFFF";
                                                                                                                                                /* if (locationSearchId != 0) {
                                                                                                                                                 Location loc = new Location();
                                                                                                                                                 try {
                                                                                                                                                 loc = PstLocation.fetchExc(locationSearchId);
                                                                                                                                                 } catch (Exception exc) {
                                                                                                                                                 }
                                                                                                                                                 colors = "#" + loc.getColorLocation();
                                                                                                                                                 }*/
                                                                                                                                            %>
                                                                                                                                            <%=ControlCombo.drawComboBoxColor(FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_LOCATION_ID], "select...", "" + objEmployeeOutlet.getLocationId(), valueLoc, keyLoc, "id=\"locationHideen\" ", colors, colorLocations)%> 
                                                                                                                                            * <%=frmEmployeeOutlet.getErrorMsg(FrmEmployeeOutlet.FRM_FIELD_LOCATION_ID)%>
                                                                                                                                        </td>
                                                                                                                                    </tr>
                                                                                                                                    <tr>
                                                                                                                                        <td width="5%" align="right" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div></td>
                                                                                                                                        <td width="59%" nowrap="nowrap">:
                                                                                                                                            <%
                                                                                                                                                Vector positionNew_value = new Vector(1, 1);
                                                                                                                                                Vector positionNew_key = new Vector(1, 1);

                                                                                                                                                Vector listPositionNew = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);
                                                                                                                                                if (listPositionNew != null && listPositionNew.size() > 0) {
                                                                                                                                                    for (int r = 0; r < listPositionNew.size(); r++) {
                                                                                                                                                        Position position = (Position) listPositionNew.get(r);
                                                                                                                                                        positionNew_key.add(position.getPosition());
                                                                                                                                                        positionNew_value.add(String.valueOf(position.getOID()));
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            %>
                                                                                                                                            <%=ControlCombo.draw(FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_POSITION_ID], "select...", "" + objEmployeeOutlet.getPositionId(), positionNew_value, positionNew_key, "id=\"positionHideen\"")%> 
                                                                                                                                            * <%=frmEmployeeOutlet.getErrorMsg(FrmEmployeeOutlet.FRM_FIELD_POSITION_ID)%>
                                                                                                                                        </td>

                                                                                                                                    </tr>
                                                                                                                                    <!-- <tr>
                                                                                                                                         <td width="5%" align="right" nowrap><div align="left">Schedule </div></td>
                                                                                                                                         <td width="59%" nowrap="nowrap">:
                                                                                                                                    <%
                                                                                                                                        /*Vector schedule_value = new Vector(1, 1);
                                                                                                                                         Vector schedule_key = new Vector(1, 1);
                                                                                                                                         for (int r = 0; r < PstScheduleSymbol.fieldNamesTypeSchedule.length; r++) {
                                                                                                                                         schedule_key.add(PstScheduleSymbol.fieldNamesTypeSchedule[r]);
                                                                                                                                         schedule_value.add(""+r);
                                                                                                                                         }*/
                                                                                                                                    %>
                                                                                                                                    <%//=ControlCombo.draw(FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_SCHEDULE_TYPE], "select...", "" + PstScheduleSymbol.fieldNamesTypeSchedule[typeSchedule], schedule_value, schedule_key, "id=\"scheduleHideen\"")%> 
                                                                                                                                    * <%//=frmEmployeeOutlet.getErrorMsg(FrmEmployeeOutlet.FRM_FIELD_SCHEDULE_TYPE)%>
                                                                                                                                    
                                                                                                                                </td>
                                                                                                                            </tr>-->

                                                                                                                                    <tr>
                                                                                                                                        <td width="6%" align="right" nowrap><div align=left>Date</div></td>
                                                                                                                                        <td width="30%" nowrap="nowrap">:
                                                                                                                                            <%
                                                                                                                                                Date st = new Date();
                                                                                                                                                st.setHours(0);
                                                                                                                                                st.setMinutes(0);
                                                                                                                                                st.setSeconds(0);
                                                                                                                                                Date end = new Date();
                                                                                                                                                end.setHours(23);
                                                                                                                                                end.setMinutes(59);
                                                                                                                                                end.setSeconds(59);
                                                                                                                                                String ctrTimeStart = ControlDate.drawTime(FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_DATE_FROM], selectedDateFromEmployeeOutlet != null ? selectedDateFromEmployeeOutlet : st, "elemenForm", 24, 0, 0);
                                                                                                                                                String ctrTimeEnd = ControlDate.drawTime(FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_DATE_TO], selectedDateToEmployeeOutlet != null ? iCommand == Command.NONE ? end : selectedDateToEmployeeOutlet : end, "elemenForm", 24, 0, 0);
                                                                                                                                            %>
                                                                                                                                            <%=ControlDate.drawDateWithStyle(FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_DATE_FROM], selectedDateFromEmployeeOutlet != null ? selectedDateFromEmployeeOutlet : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                                                                                                                                            to <%=ControlDate.drawDateWithStyle(FrmEmployeeOutlet.fieldNames[FrmEmployeeOutlet.FRM_FIELD_DATE_TO], selectedDateToEmployeeOutlet != null ? selectedDateToEmployeeOutlet : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td> 
                                                                                                                                    </tr>

                                                                                                                                    <tr>
                                                                                                                                        <td colspan="2"><table width="100%">
                                                                                                                                                <td width="16"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSave.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Employee Outlet"></a></td>
                                                                                                                                                <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                                                                <td class="command" nowrap><a href="javascript:cmdSave()">Save Input</a></td>
                                                                                                                                            </table>
                                                                                                                                        </td> 
                                                                                                                                    </tr>
                                                                                                                                </table>

                                                                                                                            </fieldset>
                                                                                                                        </td>
                                                                                                                    </tr>
                                                                                                                </table>
                                                                                                            </td>
                                                                                                        </tr>



                                                                                                    </table>
                                                                                                    <%if (iCommand == Command.LIST || iCommand == Command.REFRESH || iErrCode > 0 || msg.length() > 0) {%>
                                                                                                    <table width="100%">
                                                                                                        <%if (msg != null && msg.length() > 0) {%>
                                                                                                        <tr>
                                                                                                            <td bgcolor="#FFFF00"><img src="<%=approot%>/images/info3.png"><b> Message : </b> <%=msg%> </td>
                                                                                                        </tr>
                                                                                                        <%}%>
                                                                                                        <tr>
                                                                                                            <td>
                                                                                                                <%=drawList(out, listEmployee, selectedDateFrom, selectedDateTo, hashScheduleSymbol, hashSchedule, viewMode)%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                    </table>
                                                                                                    <%}%>
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
                        </table>
                        <script type="text/javascript">
                        $(document).ready(function() {
                            gridviewScroll();
                        });
                            <%
                                int freesize = 2;

                            %>
                        function gridviewScroll() {
                            gridView1 = $('#GridView1').gridviewScroll({
                                width: 1100,
                                height: 150,
                                railcolor: "##33AAFF",
                                barcolor: "#CDCDCD",
                                barhovercolor: "#606060",
                                bgcolor: "##33AAFF",
                                freezesize: <%=freesize%>,
                                arrowsize: 30,
                                varrowtopimg: "<%=approot%>/images/arrowvt.png",
                                varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                                harrowleftimg: "<%=approot%>/images/arrowhl.png",
                                harrowrightimg: "<%=approot%>/images/arrowhr.png",
                                headerrowcount: 2,
                                railsize: 16,
                                barsize: 15
                            });
                        }
                        </script>

                        <script type="text/javascript">
                            $(document).ready(function() {
                                gridviewScroll1();
                            });
                            <%
                               freesize = 3;
                               ControlList ctrlist = new ControlList();
                               ctrlist.setIdStlyeFreezing("#GridView2");
                            %>
                            function gridviewScroll1() {
                                gridView1 = $('<%=ctrlist.getIdStlyeFreezing()%>').gridviewScroll({
                                    width: 1300,
                                    height: 200,
                                    railcolor: "##33AAFF",
                                    barcolor: "#CDCDCD",
                                    barhovercolor: "#606060",
                                    bgcolor: "##33AAFF",
                                    freezesize: <%=freesize%>,
                                    arrowsize: 30,
                                    varrowtopimg: "<%=approot%>/images/arrowvt.png",
                                    varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                                    harrowleftimg: "<%=approot%>/images/arrowhl.png",
                                    harrowrightimg: "<%=approot%>/images/arrowhr.png",
                                    headerrowcount: 1,
                                    railsize: 16,
                                    barsize: 15
                                });
                            }
                        </script>

                    </body>
                    <!-- #BeginEditable "script" --> 
                    <!-- #EndEditable --> <!-- #EndTemplate -->
                    </html>

