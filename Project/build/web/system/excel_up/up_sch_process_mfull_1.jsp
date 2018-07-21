<%@ page language = "java" %>

<%@ page import="java.util.*"%>
<%@ page import="java.lang.System"%>

<%@ page import="java.io.*"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.ByteArrayInputStream"%>

<%@ page import="org.apache.poi.hssf.usermodel.*" %>

<%@ page import="com.dimata.util.Excel"%>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="com.dimata.util.blob.TextLoader" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.form.*" %>

<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ page import="com.dimata.harisma.entity.leave.PstDpApplication" %>
<%@ page import="com.dimata.harisma.entity.leave.PstLeaveApplication" %>
<%@ page import="com.dimata.harisma.form.attendance.CtrlDpStockManagement" %>

<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>

<%
            int NUM_HEADER = 3;
            int NUM_CELL = -1;
            String SPIT_SHIFT_SEPARATOR = "/";

// deklarasi jumlah rows yang akan di hapus untuk header dan footer
            int ROWS_NUM_HEADER_DEL = 4;
            int ROWS_NUM_FOOTER_DEL = 9;

// index for header value
            int IDX_TOP = 0;
            int IDX_DEP = 2;
            int IDX_PER = 4;
            int IDX_CRT = 6;

            String topHeader = "";
            String depHeader = "";
            String creatorHeader = "";
            String periodHeader = "";

            int iCommand = FRMQueryString.requestCommand(request);
            long periodId = FRMQueryString.requestLong(request, "period_id");

            int intAdvanceDp = 100;
            String msgString = "";
            boolean noErrWithLeaveStock = true;
            Hashtable hashEmployeeError = new Hashtable();
            Hashtable hashLeaveDataError = new Hashtable();
            if (iCommand == Command.SAVE) {
                // jika periodId == 0, maka tampilkan pesan agar period schedule dipilih
                if (periodId == 0) {
                    msgString = "<div class=\"errfont\">Please choose the Period of Working Schedule !</div>";
                } // jika period != 0, maka proses import data schedule dari file excel
                else {
                    // mengambil data dari form dengan querystring dan simpan ke array of data String
                    String[] employeeId = request.getParameterValues("employee_id");
                    String[] employeeNum = request.getParameterValues("employee_num");
                    String[] d1 = request.getParameterValues("D1");
                    String[] d2 = request.getParameterValues("D2");
                    String[] d3 = request.getParameterValues("D3");
                    String[] d4 = request.getParameterValues("D4");
                    String[] d5 = request.getParameterValues("D5");
                    String[] d6 = request.getParameterValues("D6");
                    String[] d7 = request.getParameterValues("D7");
                    String[] d8 = request.getParameterValues("D8");
                    String[] d9 = request.getParameterValues("D9");
                    String[] d10 = request.getParameterValues("D10");
                    String[] d11 = request.getParameterValues("D11");
                    String[] d12 = request.getParameterValues("D12");
                    String[] d13 = request.getParameterValues("D13");
                    String[] d14 = request.getParameterValues("D14");
                    String[] d15 = request.getParameterValues("D15");
                    String[] d16 = request.getParameterValues("D16");
                    String[] d17 = request.getParameterValues("D17");
                    String[] d18 = request.getParameterValues("D18");
                    String[] d19 = request.getParameterValues("D19");
                    String[] d20 = request.getParameterValues("D20");
                    String[] d21 = request.getParameterValues("D21");
                    String[] d22 = request.getParameterValues("D22");
                    String[] d23 = request.getParameterValues("D23");
                    String[] d24 = request.getParameterValues("D24");
                    String[] d25 = request.getParameterValues("D25");
                    String[] d26 = request.getParameterValues("D26");
                    String[] d27 = request.getParameterValues("D27");
                    String[] d28 = request.getParameterValues("D28");
                    String[] d29 = request.getParameterValues("D29");
                    String[] d30 = request.getParameterValues("D30");
                    String[] d31 = request.getParameterValues("D31");

                    String[] d2nd1 = request.getParameterValues("D2ND1");
                    String[] d2nd2 = request.getParameterValues("D2ND2");
                    String[] d2nd3 = request.getParameterValues("D2ND3");
                    String[] d2nd4 = request.getParameterValues("D2ND4");
                    String[] d2nd5 = request.getParameterValues("D2ND5");
                    String[] d2nd6 = request.getParameterValues("D2ND6");
                    String[] d2nd7 = request.getParameterValues("D2ND7");
                    String[] d2nd8 = request.getParameterValues("D2ND8");
                    String[] d2nd9 = request.getParameterValues("D2ND9");
                    String[] d2nd10 = request.getParameterValues("D2ND10");
                    String[] d2nd11 = request.getParameterValues("D2ND11");
                    String[] d2nd12 = request.getParameterValues("D2ND12");
                    String[] d2nd13 = request.getParameterValues("D2ND13");
                    String[] d2nd14 = request.getParameterValues("D2ND14");
                    String[] d2nd15 = request.getParameterValues("D2ND15");
                    String[] d2nd16 = request.getParameterValues("D2ND16");
                    String[] d2nd17 = request.getParameterValues("D2ND17");
                    String[] d2nd18 = request.getParameterValues("D2ND18");
                    String[] d2nd19 = request.getParameterValues("D2ND19");
                    String[] d2nd20 = request.getParameterValues("D2ND20");
                    String[] d2nd21 = request.getParameterValues("D2ND21");
                    String[] d2nd22 = request.getParameterValues("D2ND22");
                    String[] d2nd23 = request.getParameterValues("D2ND23");
                    String[] d2nd24 = request.getParameterValues("D2ND24");
                    String[] d2nd25 = request.getParameterValues("D2ND25");
                    String[] d2nd26 = request.getParameterValues("D2ND26");
                    String[] d2nd27 = request.getParameterValues("D2ND27");
                    String[] d2nd28 = request.getParameterValues("D2ND28");
                    String[] d2nd29 = request.getParameterValues("D2ND29");
                    String[] d2nd30 = request.getParameterValues("D2ND30");
                    String[] d2nd31 = request.getParameterValues("D2ND31");

                    String[] cat1 = request.getParameterValues("CAT1");
                    String[] cat2 = request.getParameterValues("CAT2");
                    String[] cat3 = request.getParameterValues("CAT3");
                    String[] cat4 = request.getParameterValues("CAT4");
                    String[] cat5 = request.getParameterValues("CAT5");
                    String[] cat6 = request.getParameterValues("CAT6");
                    String[] cat7 = request.getParameterValues("CAT7");
                    String[] cat8 = request.getParameterValues("CAT8");
                    String[] cat9 = request.getParameterValues("CAT9");
                    String[] cat10 = request.getParameterValues("CAT10");
                    String[] cat11 = request.getParameterValues("CAT11");
                    String[] cat12 = request.getParameterValues("CAT12");
                    String[] cat13 = request.getParameterValues("CAT13");
                    String[] cat14 = request.getParameterValues("CAT14");
                    String[] cat15 = request.getParameterValues("CAT15");
                    String[] cat16 = request.getParameterValues("CAT16");
                    String[] cat17 = request.getParameterValues("CAT17");
                    String[] cat18 = request.getParameterValues("CAT18");
                    String[] cat19 = request.getParameterValues("CAT19");
                    String[] cat20 = request.getParameterValues("CAT20");
                    String[] cat21 = request.getParameterValues("CAT21");
                    String[] cat22 = request.getParameterValues("CAT22");
                    String[] cat23 = request.getParameterValues("CAT23");
                    String[] cat24 = request.getParameterValues("CAT24");
                    String[] cat25 = request.getParameterValues("CAT25");
                    String[] cat26 = request.getParameterValues("CAT26");
                    String[] cat27 = request.getParameterValues("CAT27");
                    String[] cat28 = request.getParameterValues("CAT28");
                    String[] cat29 = request.getParameterValues("CAT29");
                    String[] cat30 = request.getParameterValues("CAT30");
                    String[] cat31 = request.getParameterValues("CAT31");

                    // iterasi sebanyak record employee schedule dalam file excel
                    // untuk melakukan pengecekan data schedule sehingga bisa di-"insert" atau di-"update"
                    for (int e = 0; e < employeeId.length; e++) {

                        String where = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId +
                                " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                                " = " + employeeId[e];
                        Vector vcheck = PstEmpSchedule.list(0, 0, where, "");

                        // perlakuan khusus untuk tanggal 29, 30 dan 31 karena jumlah hari pada tiap2 bulan bervariasi antara 28 - 31
                        if (d29[e].equals("null")) {
                            d29[e] = "0";
                        }

                        if (d30[e].equals("null")) {
                            d30[e] = "0";
                        }

                        if (d31[e].equals("null")) {
                            d31[e] = "0";
                        }

                        if (d2nd29[e].equals("null")) {
                            d2nd29[e] = "0";
                        }

                        if (d2nd30[e].equals("null")) {
                            d2nd30[e] = "0";
                        }

                        if (d2nd31[e].equals("null")) {
                            d2nd31[e] = "0";
                        }

                        if (cat29[e].equals("null")) {
                            cat29[e] = "0";
                        }

                        if (cat30[e].equals("null")) {
                            cat30[e] = "0";
                        }

                        if (cat31[e].equals("null")) {
                            cat31[e] = "0";
                        }

                        // add schedule category to vector, used to check status leave (DP, AL dan LL)
                        Vector vectSchldCat = new Vector(1, 1);
                        vectSchldCat.add(String.valueOf(cat1[e]));
                        vectSchldCat.add(String.valueOf(cat2[e]));
                        vectSchldCat.add(String.valueOf(cat3[e]));
                        vectSchldCat.add(String.valueOf(cat4[e]));
                        vectSchldCat.add(String.valueOf(cat5[e]));
                        vectSchldCat.add(String.valueOf(cat6[e]));
                        vectSchldCat.add(String.valueOf(cat7[e]));
                        vectSchldCat.add(String.valueOf(cat8[e]));
                        vectSchldCat.add(String.valueOf(cat9[e]));
                        vectSchldCat.add(String.valueOf(cat10[e]));
                        vectSchldCat.add(String.valueOf(cat11[e]));
                        vectSchldCat.add(String.valueOf(cat12[e]));
                        vectSchldCat.add(String.valueOf(cat13[e]));
                        vectSchldCat.add(String.valueOf(cat14[e]));
                        vectSchldCat.add(String.valueOf(cat15[e]));
                        vectSchldCat.add(String.valueOf(cat16[e]));
                        vectSchldCat.add(String.valueOf(cat17[e]));
                        vectSchldCat.add(String.valueOf(cat18[e]));
                        vectSchldCat.add(String.valueOf(cat19[e]));
                        vectSchldCat.add(String.valueOf(cat20[e]));
                        vectSchldCat.add(String.valueOf(cat21[e]));
                        vectSchldCat.add(String.valueOf(cat22[e]));
                        vectSchldCat.add(String.valueOf(cat23[e]));
                        vectSchldCat.add(String.valueOf(cat24[e]));
                        vectSchldCat.add(String.valueOf(cat25[e]));
                        vectSchldCat.add(String.valueOf(cat26[e]));
                        vectSchldCat.add(String.valueOf(cat27[e]));
                        vectSchldCat.add(String.valueOf(cat28[e]));
                        vectSchldCat.add(String.valueOf(cat29[e]));
                        vectSchldCat.add(String.valueOf(cat30[e]));
                        vectSchldCat.add(String.valueOf(cat31[e]));

                        // get total DP stock per this employee
                        PstDpStockManagement objPstDpStockManagement = new PstDpStockManagement();

                        long empId = (employeeId[e].equals("null")) ? 0 : Long.parseLong("" + employeeId[e]);

                        int intDpStockAmount = objPstDpStockManagement.getDpStockAmountPerEmployee(empId);

                        // get total AL stock per this employee
                        PstAlStockManagement objPstAlStockManagement = new PstAlStockManagement();
                        int intAlStockAmount = objPstAlStockManagement.getAlStockAmountPerEmployee(empId);

                        // get total LL stock per this employee
                        PstLLStockManagement objPstLLStockManagement = new PstLLStockManagement();
                        int intLlStockAmount = objPstLLStockManagement.getLlStockAmountPerEmployee(empId);

                        // check leave schedule selected and compare with its appropriate type (DP to DP, AL to AL and LL to LL)			
                        PstEmpSchedule objPstEmpSchedule = new PstEmpSchedule();
                        Vector vectErr = objPstEmpSchedule.checkScheduleLeave(vectSchldCat, intDpStockAmount, intAdvanceDp, intAlStockAmount, intLlStockAmount);
                        boolean checkLeaveScheduleOk = false;
                        if (vectErr != null && vectErr.size() > 0) {
                            int intDpError = Integer.parseInt(String.valueOf(vectErr.get(0)));
                            int intAlError = Integer.parseInt(String.valueOf(vectErr.get(1)));
                            int intLlError = Integer.parseInt(String.valueOf(vectErr.get(2)));

                            String strIndexScheduleError = "";
                            if (intDpError > 0) {
                                strIndexScheduleError = String.valueOf(intDpError);
                            }

                            /*				
                            // karena AL dan LL bisa ngutang, uncomment jika tidak bisa ngutang 				
                            else
                            {
                            if(intAlError > 0)
                            {
                            strIndexScheduleError = String.valueOf(intAlError);
                            }
                            else
                            {
                            if(intLlError > 0)
                            {
                            strIndexScheduleError = String.valueOf(intLlError);
                            }					
                            }				
                            }
                             */

                            hashLeaveDataError.put(String.valueOf(employeeNum[e]), strIndexScheduleError);

                            if (!(strIndexScheduleError != null && strIndexScheduleError.length() > 0)) {
                                checkLeaveScheduleOk = true;
                            } else {
                                hashEmployeeError.put(String.valueOf(employeeNum[e]), String.valueOf(employeeNum[e]));
                            }
                        }



                        // if check leave(DP, AL dan LL) schedule success		
                        noErrWithLeaveStock = noErrWithLeaveStock && checkLeaveScheduleOk;
                        if (checkLeaveScheduleOk) {
                            // jika schedule utk employee ada dalam db, maka lakukan proses "update"
                            if (vcheck.size() > 0) {
                                // update
                                try {
                                    EmpSchedule empSchedule = (EmpSchedule) vcheck.get(0);

                                    empSchedule.setEmployeeId(Long.parseLong((employeeId[e].equals("null")) ? "0" : "" + employeeId[e]));

                                    Date currDate = new Date();
                                    int dt = currDate.getDate();
                                    Period p = PstPeriod.fetchExc(periodId);
                                    if ((p.getStartDate().before(currDate) || p.getStartDate().equals(currDate)) && (p.getEndDate().after(currDate) || p.getEndDate().equals(currDate))) {
                                        dt = currDate.getDate();
                                    } //jika periode nya yang akan datang, update semua
                                    else if (p.getStartDate().after(currDate)) {
                                        dt = 1;
                                    } //jika periode nya sudah lewat, jangan melakukan update
                                    else if (p.getEndDate().before(currDate)) {
                                        dt = 32;
                                    }

                                    if (empSchedule.getEmployeeId() != 0) {
                                        empSchedule.setPeriodId(periodId);
                                        if (dt == 1) {
                                            empSchedule.setD1(d1[e].equals("null") ? 0 : Long.parseLong("" + d1[e]));
                                        }
                                        if (dt <= 2) {
                                            empSchedule.setD2(d2[e].equals("null") ? 0 : Long.parseLong("" + d2[e]));
                                        }
                                        if (dt <= 3) {
                                            empSchedule.setD3(d3[e].equals("null") ? 0 : Long.parseLong("" + d3[e]));
                                        }
                                        if (dt <= 4) {
                                            empSchedule.setD4(d4[e].equals("null") ? 0 : Long.parseLong("" + d4[e]));
                                        }
                                        if (dt <= 5) {
                                            empSchedule.setD5(d5[e].equals("null") ? 0 : Long.parseLong("" + d5[e]));
                                        }
                                        if (dt <= 6) {
                                            empSchedule.setD6(d6[e].equals("null") ? 0 : Long.parseLong("" + d6[e]));
                                        }
                                        if (dt <= 7) {
                                            empSchedule.setD7(d7[e].equals("null") ? 0 : Long.parseLong("" + d7[e]));
                                        }
                                        if (dt <= 8) {
                                            empSchedule.setD8(d8[e].equals("null") ? 0 : Long.parseLong("" + d8[e]));
                                        }
                                        if (dt <= 9) {
                                            empSchedule.setD9(d9[e].equals("null") ? 0 : Long.parseLong("" + d9[e]));
                                        }
                                        if (dt <= 10) {
                                            empSchedule.setD10(d10[e].equals("null") ? 0 : Long.parseLong("" + d10[e]));
                                        }
                                        if (dt <= 11) {
                                            empSchedule.setD11(d11[e].equals("null") ? 0 : Long.parseLong("" + d11[e]));
                                        }
                                        if (dt <= 12) {
                                            empSchedule.setD12(d12[e].equals("null") ? 0 : Long.parseLong("" + d12[e]));
                                        }
                                        if (dt <= 13) {
                                            empSchedule.setD13(d13[e].equals("null") ? 0 : Long.parseLong("" + d13[e]));
                                        }
                                        if (dt <= 14) {
                                            empSchedule.setD14(d14[e].equals("null") ? 0 : Long.parseLong("" + d14[e]));
                                        }
                                        if (dt <= 15) {
                                            empSchedule.setD15(d15[e].equals("null") ? 0 : Long.parseLong("" + d15[e]));
                                        }
                                        if (dt <= 16) {
                                            empSchedule.setD16(d16[e].equals("null") ? 0 : Long.parseLong("" + d16[e]));
                                        }
                                        if (dt <= 17) {
                                            empSchedule.setD17(d17[e].equals("null") ? 0 : Long.parseLong("" + d17[e]));
                                        }
                                        if (dt <= 18) {
                                            empSchedule.setD18(d18[e].equals("null") ? 0 : Long.parseLong("" + d18[e]));
                                        }
                                        if (dt <= 19) {
                                            empSchedule.setD19(d19[e].equals("null") ? 0 : Long.parseLong("" + d19[e]));
                                        }
                                        if (dt <= 20) {
                                            empSchedule.setD20(d20[e].equals("null") ? 0 : Long.parseLong("" + d20[e]));
                                        }
                                        if (dt <= 21) {
                                            empSchedule.setD21(d21[e].equals("null") ? 0 : Long.parseLong("" + d21[e]));
                                        }
                                        if (dt <= 22) {
                                            empSchedule.setD22(d22[e].equals("null") ? 0 : Long.parseLong("" + d22[e]));
                                        }
                                        if (dt <= 23) {
                                            empSchedule.setD23(d23[e].equals("null") ? 0 : Long.parseLong("" + d23[e]));
                                        }
                                        if (dt <= 24) {
                                            empSchedule.setD24(d24[e].equals("null") ? 0 : Long.parseLong("" + d24[e]));
                                        }
                                        if (dt <= 25) {
                                            empSchedule.setD25(d25[e].equals("null") ? 0 : Long.parseLong("" + d25[e]));
                                        }
                                        if (dt <= 26) {
                                            empSchedule.setD26(d26[e].equals("null") ? 0 : Long.parseLong("" + d26[e]));
                                        }
                                        if (dt <= 27) {
                                            empSchedule.setD27(d27[e].equals("null") ? 0 : Long.parseLong("" + d27[e]));
                                        }
                                        if (dt <= 28) {
                                            empSchedule.setD28(d28[e].equals("null") ? 0 : Long.parseLong("" + d28[e]));
                                        }
                                        if (dt <= 29) {
                                            empSchedule.setD29(d29[e].equals("null") ? 0 : Long.parseLong("" + d29[e]));
                                        }
                                        if (dt <= 30) {
                                            empSchedule.setD30(d30[e].equals("null") ? 0 : Long.parseLong("" + d30[e]));
                                        }
                                        if (dt <= 31) {
                                            empSchedule.setD31(d31[e].equals("null") ? 0 : Long.parseLong("" + d31[e]));
                                        }
                                        if (dt == 1) {
                                            empSchedule.setD2nd1(d2nd1[e].equals("null") ? 0 : Long.parseLong("" + d2nd1[e]));
                                        }
                                        if (dt <= 2) {
                                            empSchedule.setD2nd2(d2nd2[e].equals("null") ? 0 : Long.parseLong("" + d2nd2[e]));
                                        }
                                        if (dt <= 3) {
                                            empSchedule.setD2nd3(d2nd3[e].equals("null") ? 0 : Long.parseLong("" + d2nd3[e]));
                                        }
                                        if (dt <= 4) {
                                            empSchedule.setD2nd4(d2nd4[e].equals("null") ? 0 : Long.parseLong("" + d2nd4[e]));
                                        }
                                        if (dt <= 5) {
                                            empSchedule.setD2nd5(d2nd5[e].equals("null") ? 0 : Long.parseLong("" + d2nd5[e]));
                                        }
                                        if (dt <= 6) {
                                            empSchedule.setD2nd6(d2nd6[e].equals("null") ? 0 : Long.parseLong("" + d2nd6[e]));
                                        }
                                        if (dt <= 7) {
                                            empSchedule.setD2nd7(d2nd7[e].equals("null") ? 0 : Long.parseLong("" + d2nd7[e]));
                                        }
                                        if (dt <= 8) {
                                            empSchedule.setD2nd8(d2nd8[e].equals("null") ? 0 : Long.parseLong("" + d2nd8[e]));
                                        }
                                        if (dt <= 9) {
                                            empSchedule.setD2nd9(d2nd9[e].equals("null") ? 0 : Long.parseLong("" + d2nd9[e]));
                                        }
                                        if (dt <= 10) {
                                            empSchedule.setD2nd10(d2nd10[e].equals("null") ? 0 : Long.parseLong("" + d2nd10[e]));
                                        }
                                        if (dt <= 11) {
                                            empSchedule.setD2nd11(d2nd11[e].equals("null") ? 0 : Long.parseLong("" + d2nd11[e]));
                                        }
                                        if (dt <= 12) {
                                            empSchedule.setD2nd12(d2nd12[e].equals("null") ? 0 : Long.parseLong("" + d2nd12[e]));
                                        }
                                        if (dt <= 13) {
                                            empSchedule.setD2nd13(d2nd13[e].equals("null") ? 0 : Long.parseLong("" + d2nd13[e]));
                                        }
                                        if (dt <= 14) {
                                            empSchedule.setD2nd14(d2nd14[e].equals("null") ? 0 : Long.parseLong("" + d2nd14[e]));
                                        }
                                        if (dt <= 15) {
                                            empSchedule.setD2nd15(d2nd15[e].equals("null") ? 0 : Long.parseLong("" + d2nd15[e]));
                                        }
                                        if (dt <= 16) {
                                            empSchedule.setD2nd16(d2nd16[e].equals("null") ? 0 : Long.parseLong("" + d2nd16[e]));
                                        }
                                        if (dt <= 17) {
                                            empSchedule.setD2nd17(d2nd17[e].equals("null") ? 0 : Long.parseLong("" + d2nd17[e]));
                                        }
                                        if (dt <= 18) {
                                            empSchedule.setD2nd18(d2nd18[e].equals("null") ? 0 : Long.parseLong("" + d2nd18[e]));
                                        }
                                        if (dt <= 19) {
                                            empSchedule.setD2nd19(d2nd19[e].equals("null") ? 0 : Long.parseLong("" + d2nd19[e]));
                                        }
                                        if (dt <= 20) {
                                            empSchedule.setD2nd20(d2nd20[e].equals("null") ? 0 : Long.parseLong("" + d2nd20[e]));
                                        }
                                        if (dt <= 21) {
                                            empSchedule.setD2nd21(d2nd21[e].equals("null") ? 0 : Long.parseLong("" + d2nd21[e]));
                                        }
                                        if (dt <= 22) {
                                            empSchedule.setD2nd22(d2nd22[e].equals("null") ? 0 : Long.parseLong("" + d2nd22[e]));
                                        }
                                        if (dt <= 23) {
                                            empSchedule.setD2nd23(d2nd23[e].equals("null") ? 0 : Long.parseLong("" + d2nd23[e]));
                                        }
                                        if (dt <= 24) {
                                            empSchedule.setD2nd24(d2nd24[e].equals("null") ? 0 : Long.parseLong("" + d2nd24[e]));
                                        }
                                        if (dt <= 25) {
                                            empSchedule.setD2nd25(d2nd25[e].equals("null") ? 0 : Long.parseLong("" + d2nd25[e]));
                                        }
                                        if (dt <= 26) {
                                            empSchedule.setD2nd26(d2nd26[e].equals("null") ? 0 : Long.parseLong("" + d2nd26[e]));
                                        }
                                        if (dt <= 27) {
                                            empSchedule.setD2nd27(d2nd27[e].equals("null") ? 0 : Long.parseLong("" + d2nd27[e]));
                                        }
                                        if (dt <= 28) {
                                            empSchedule.setD2nd28(d2nd28[e].equals("null") ? 0 : Long.parseLong("" + d2nd28[e]));
                                        }
                                        if (dt <= 29) {
                                            empSchedule.setD2nd29(d2nd29[e].equals("null") ? 0 : Long.parseLong("" + d2nd29[e]));
                                        }
                                        if (dt <= 30) {
                                            empSchedule.setD2nd30(d2nd30[e].equals("null") ? 0 : Long.parseLong("" + d2nd30[e]));
                                        }
                                        if (dt <= 31) {
                                            empSchedule.setD2nd31(d2nd31[e].equals("null") ? 0 : Long.parseLong("" + d2nd31[e]));
                                        }

                                        EmpSchedule empBeforeUpdate = PstEmpSchedule.fetchExc(empSchedule.getOID());
                                        PstEmpSchedule.updateExc(empSchedule);

                                        //System.out.println(".::JSP - updateDp&LeaveApplication before : " + (dtBefore).getTime());                                             
                                        PstDpApplication objPstDpApplication = new PstDpApplication();
                                        long generateDpResult = objPstDpApplication.updateDpApplication(empSchedule);

                                        PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication();
                                        long generateLeaveResult = objPstLeaveApplication.updateLeaveApplication(empSchedule);
                                        //System.out.println(".::JSP - updateDpobjPstLeaveApplicationApplication after  : " + (dtBefore).getTime());                                             												

                                        // proses import presence
                                        // proses ini dilakukan jika terlambat insert schedule ke HARISMA
                                        // atau update schedule untuk masing-masing employee
                                        // add by edhy                                  
                                        PstPresence.importPresenceTriggerByImportEmpScheduleExcel(empBeforeUpdate, empSchedule);

                                        // --- di pakai untuk update dp ---  
                                        CtrlDpStockManagement ctrlDpStMng = new CtrlDpStockManagement(request);
                                        ctrlDpStMng.generateDpStock(empBeforeUpdate, empSchedule, periodId, empSchedule.getEmployeeId());

                                    }
                                } catch (Exception exc) {
                                    msgString = msgString + "<div class=\"errfont\">Can't save data row " + (e + 1) + "</div>";
                                }
                            } // jika schedule utk employee blm ada dalam db, maka lakukan proses "insert"			
                            else {
                                try {
                                    EmpSchedule empSchedule = new EmpSchedule();

                                    empSchedule.setEmployeeId(Long.parseLong((employeeId[e].equals("null")) ? "0" : "" + employeeId[e]));

                                    if (empSchedule.getEmployeeId() != 0) {
                                        empSchedule.setPeriodId(periodId);
                                        empSchedule.setD1(d1[e].equals("null") ? 0 : Long.parseLong("" + d1[e]));
                                        empSchedule.setD2(d2[e].equals("null") ? 0 : Long.parseLong("" + d2[e]));
                                        empSchedule.setD3(d3[e].equals("null") ? 0 : Long.parseLong("" + d3[e]));
                                        empSchedule.setD4(d4[e].equals("null") ? 0 : Long.parseLong("" + d4[e]));
                                        empSchedule.setD5(d5[e].equals("null") ? 0 : Long.parseLong("" + d5[e]));
                                        empSchedule.setD6(d6[e].equals("null") ? 0 : Long.parseLong("" + d6[e]));
                                        empSchedule.setD7(d7[e].equals("null") ? 0 : Long.parseLong("" + d7[e]));
                                        empSchedule.setD8(d8[e].equals("null") ? 0 : Long.parseLong("" + d8[e]));
                                        empSchedule.setD9(d9[e].equals("null") ? 0 : Long.parseLong("" + d9[e]));
                                        empSchedule.setD10(d10[e].equals("null") ? 0 : Long.parseLong("" + d10[e]));
                                        empSchedule.setD11(d11[e].equals("null") ? 0 : Long.parseLong("" + d11[e]));
                                        empSchedule.setD12(d12[e].equals("null") ? 0 : Long.parseLong("" + d12[e]));
                                        empSchedule.setD13(d13[e].equals("null") ? 0 : Long.parseLong("" + d13[e]));
                                        empSchedule.setD14(d14[e].equals("null") ? 0 : Long.parseLong("" + d14[e]));
                                        empSchedule.setD15(d15[e].equals("null") ? 0 : Long.parseLong("" + d15[e]));
                                        empSchedule.setD16(d16[e].equals("null") ? 0 : Long.parseLong("" + d16[e]));
                                        empSchedule.setD17(d17[e].equals("null") ? 0 : Long.parseLong("" + d17[e]));
                                        empSchedule.setD18(d18[e].equals("null") ? 0 : Long.parseLong("" + d18[e]));
                                        empSchedule.setD19(d19[e].equals("null") ? 0 : Long.parseLong("" + d19[e]));
                                        empSchedule.setD20(d20[e].equals("null") ? 0 : Long.parseLong("" + d20[e]));
                                        empSchedule.setD21(d21[e].equals("null") ? 0 : Long.parseLong("" + d21[e]));
                                        empSchedule.setD22(d22[e].equals("null") ? 0 : Long.parseLong("" + d22[e]));
                                        empSchedule.setD23(d23[e].equals("null") ? 0 : Long.parseLong("" + d23[e]));
                                        empSchedule.setD24(d24[e].equals("null") ? 0 : Long.parseLong("" + d24[e]));
                                        empSchedule.setD25(d25[e].equals("null") ? 0 : Long.parseLong("" + d25[e]));
                                        empSchedule.setD26(d26[e].equals("null") ? 0 : Long.parseLong("" + d26[e]));
                                        empSchedule.setD27(d27[e].equals("null") ? 0 : Long.parseLong("" + d27[e]));
                                        empSchedule.setD28(d28[e].equals("null") ? 0 : Long.parseLong("" + d28[e]));
                                        empSchedule.setD29(d29[e].equals("null") ? 0 : Long.parseLong("" + d29[e]));
                                        empSchedule.setD30(d30[e].equals("null") ? 0 : Long.parseLong("" + d30[e]));
                                        empSchedule.setD31(d31[e].equals("null") ? 0 : Long.parseLong("" + d31[e]));
                                        empSchedule.setD2nd1(d2nd1[e].equals("null") ? 0 : Long.parseLong("" + d2nd1[e]));
                                        empSchedule.setD2nd2(d2nd2[e].equals("null") ? 0 : Long.parseLong("" + d2nd2[e]));
                                        empSchedule.setD2nd3(d2nd3[e].equals("null") ? 0 : Long.parseLong("" + d2nd3[e]));
                                        empSchedule.setD2nd4(d2nd4[e].equals("null") ? 0 : Long.parseLong("" + d2nd4[e]));
                                        empSchedule.setD2nd5(d2nd5[e].equals("null") ? 0 : Long.parseLong("" + d2nd5[e]));
                                        empSchedule.setD2nd6(d2nd6[e].equals("null") ? 0 : Long.parseLong("" + d2nd6[e]));
                                        empSchedule.setD2nd7(d2nd7[e].equals("null") ? 0 : Long.parseLong("" + d2nd7[e]));
                                        empSchedule.setD2nd8(d2nd8[e].equals("null") ? 0 : Long.parseLong("" + d2nd8[e]));
                                        empSchedule.setD2nd9(d2nd9[e].equals("null") ? 0 : Long.parseLong("" + d2nd9[e]));
                                        empSchedule.setD2nd10(d2nd10[e].equals("null") ? 0 : Long.parseLong("" + d2nd10[e]));
                                        empSchedule.setD2nd11(d2nd11[e].equals("null") ? 0 : Long.parseLong("" + d2nd11[e]));
                                        empSchedule.setD2nd12(d2nd12[e].equals("null") ? 0 : Long.parseLong("" + d2nd12[e]));
                                        empSchedule.setD2nd13(d2nd13[e].equals("null") ? 0 : Long.parseLong("" + d2nd13[e]));
                                        empSchedule.setD2nd14(d2nd14[e].equals("null") ? 0 : Long.parseLong("" + d2nd14[e]));
                                        empSchedule.setD2nd15(d2nd15[e].equals("null") ? 0 : Long.parseLong("" + d2nd15[e]));
                                        empSchedule.setD2nd16(d2nd16[e].equals("null") ? 0 : Long.parseLong("" + d2nd16[e]));
                                        empSchedule.setD2nd17(d2nd17[e].equals("null") ? 0 : Long.parseLong("" + d2nd17[e]));
                                        empSchedule.setD2nd18(d2nd18[e].equals("null") ? 0 : Long.parseLong("" + d2nd18[e]));
                                        empSchedule.setD2nd19(d2nd19[e].equals("null") ? 0 : Long.parseLong("" + d2nd19[e]));
                                        empSchedule.setD2nd20(d2nd20[e].equals("null") ? 0 : Long.parseLong("" + d2nd20[e]));
                                        empSchedule.setD2nd21(d2nd21[e].equals("null") ? 0 : Long.parseLong("" + d2nd21[e]));
                                        empSchedule.setD2nd22(d2nd22[e].equals("null") ? 0 : Long.parseLong("" + d2nd22[e]));
                                        empSchedule.setD2nd23(d2nd23[e].equals("null") ? 0 : Long.parseLong("" + d2nd23[e]));
                                        empSchedule.setD2nd24(d2nd24[e].equals("null") ? 0 : Long.parseLong("" + d2nd24[e]));
                                        empSchedule.setD2nd25(d2nd25[e].equals("null") ? 0 : Long.parseLong("" + d2nd25[e]));
                                        empSchedule.setD2nd26(d2nd26[e].equals("null") ? 0 : Long.parseLong("" + d2nd26[e]));
                                        empSchedule.setD2nd27(d2nd27[e].equals("null") ? 0 : Long.parseLong("" + d2nd27[e]));
                                        empSchedule.setD2nd28(d2nd28[e].equals("null") ? 0 : Long.parseLong("" + d2nd28[e]));
                                        empSchedule.setD2nd29(d2nd29[e].equals("null") ? 0 : Long.parseLong("" + d2nd29[e]));
                                        empSchedule.setD2nd30(d2nd30[e].equals("null") ? 0 : Long.parseLong("" + d2nd30[e]));
                                        empSchedule.setD2nd31(d2nd31[e].equals("null") ? 0 : Long.parseLong("" + d2nd31[e]));

                                        long oid = PstEmpSchedule.insertExc(empSchedule);
                                        empSchedule.setOID(oid);

                                        //System.out.println(".::JSP - generateDp&LeaveApplication before : " + (dtBefore).getTime());                                             
                                        PstDpApplication objPstDpApplication = new PstDpApplication();
                                        long generateDpResult = objPstDpApplication.generateDpApplication(empSchedule);

                                        PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication();
                                        long generateLeaveResult = objPstLeaveApplication.generateLeaveApplication(empSchedule);
                                        //System.out.println(".::JSP - generateDp&LeaveApplication after  : " + (dtBefore).getTime());                                             												

                                        // proses import presence
                                        // proses ini dilakukan jika terlambat insert schedule ke HARISMA  
                                        // atau update schedule untuk masing-masing employee
                                        // add by edhy                                  
                                        EmpSchedule objEmpSchedulePrev = new EmpSchedule();
                                        objEmpSchedulePrev.setOID(empSchedule.getOID());
                                        objEmpSchedulePrev.setPeriodId(empSchedule.getPeriodId());
                                        objEmpSchedulePrev.setEmployeeId(empSchedule.getEmployeeId());
                                        PstPresence.importPresenceTriggerByImportEmpScheduleExcel(objEmpSchedulePrev, empSchedule);

                                        // --- di pakai untuk insert dp ---
                                        CtrlDpStockManagement ctrlDpStMng = new CtrlDpStockManagement(request);
                                        ctrlDpStMng.generateDpStock(new EmpSchedule(), empSchedule, periodId, empSchedule.getEmployeeId());

                                    }

                                } catch (Exception exc) {
                                    msgString = msgString + "<div class=\"errfont\">Can't save data row " + (e + 1) + "</div>";
                                }
                            }
                        }
                    }

                    if ((msgString == null || msgString.length() < 1) && noErrWithLeaveStock) {
                        msgString = "<div class=\"msginfo\">Data have been saved</div>";
                    } else {
                        msgString = "<div class=\"errfont\">Some data have been saved, but one or more with the <b>red highlight</b> cannot saved because its <b>leave stock is empty or not enough</b> for this schedule...</div>";
                    }
                }
            }

// mencari data periode schedule untuk proses di combobox
            Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
            Vector periodKey = new Vector(1, 1);
            Vector periodValue = new Vector(1, 1);
            for (int p = 0; p < listPeriod.size(); p++) {
                Period period = (Period) listPeriod.get(p);

                if (period.getEndDate().after(new Date())) {
                    periodKey.add(period.getPeriod());
                    periodValue.add("" + period.getOID());
                }
            }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Working Schedule</title>
        <script language="JavaScript">
            function cmdSave()
            {
                document.frmupload.command.value="<%=Command.SAVE%>";
                document.frmupload.action="up_sch_process_mfull.jsp";
                document.frmupload.submit();
            }
        </script>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
            function MM_swapImgRestore() 
            { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
            }
            
            function MM_preloadImages() 
            { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
            }
            
            function MM_findObj(n, d) 
            { //v4.0
                var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                if(!x && document.getElementById) x=document.getElementById(n); return x;
            }
            
            function MM_swapImage() 
            { //v3.0
                var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
            }
        </SCRIPT>
        <!-- #EndEditable -->
    </head> 
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
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
                                                    Uploader > Working Schedule
                                                    <!-- #EndEditable --> 
                                            </strong></font> 
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td class="tablecolor"> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                         <%
            TextLoader uploader = new TextLoader();
            FileOutputStream fOut = null;
            ByteArrayInputStream inStream = null;
            StringBuffer drawList = new StringBuffer();
            Vector v = new Vector();

            // jumlah kolom schedule (employee name, payroll number, 31 column for date schedule 
            int numcol = 33;

            try {

                // jika command==SAVE, maka ambil data dari SESSION yang ada, simpan ke vector "v"
                if (iCommand == Command.SAVE) {
                    Vector vector = (Vector) session.getValue("WORK_SCHEDULE");
                    v = (Vector) vector.clone();
                } // jika command!=SAVE, maka proses import dari file excel, simpan ke vector "v"
                else {
                    uploader.uploadText(config, request, response);
                    Object obj = uploader.getTextFile("file");
                    byte byteText[] = null;
                    byteText = (byte[]) obj;
                    inStream = new ByteArrayInputStream(byteText);
                    Excel tp = new Excel();
                    Vector vector = tp.ReadStream((InputStream) inStream, NUM_HEADER, NUM_CELL);

                    numcol = tp.getNumberOfColumn();
                    //System.out.println("Jumlah kolom file excel : "+numcol);

                    // proses data vector hasil parsing ke SESSION
                    if (session.getValue("WORK_SCHEDULE") != null) {
                        session.removeValue("WORK_SCHEDULE");
                    }
                    session.putValue("WORK_SCHEDULE", vector);

                    v = (Vector) vector.clone();
                }


                /**
                 * Algoritma : 
                 * 	- Ambil data header atau title dokumen (4 baris teratas), simpan dalam vector. Dari vector 
                 *	  kemudian simpan ke variabel yang bersesuaian.
                 *  - Bersihkan data schedule dengan data2 yang tidak perlu (title dokumen bagian atas dan 
                 *    simbol schedule bagian bawah).  
                 *	- Kemudian baru parsing data schedule yang ada.
                 */
                // get header (title dokumen) yang dalam hal ini WORKING SCHEDULE dst ...
                Vector headers = new Vector(1, 1);
                int num = ROWS_NUM_HEADER_DEL * numcol;
                for (int i = 0; i < num; i++) {
                    if (v.get(i) != null) {
                        headers.add((String) v.get(i));
                    }
                }

                // remove header(title dokumen) dari vector v
                int x = 0;
                while (x < num) {
                    try {
                        v.remove(0);
                        x = x + 1;
                    } catch (Exception e) {
                        System.out.println("exc when delete header : " + e.toString());
                    }
                }

                // ambil value dari header
                if (headers != null && headers.size() > 0) {
                    try {
                        topHeader = (String) headers.get(IDX_TOP);
                    } catch (Exception exc) {
                        topHeader = "Please specify schedule title correctly and on the right cell";
                    }
                    try {
                        depHeader = (String) headers.get(IDX_DEP);
                    } catch (Exception exc) {
                        depHeader = "Please specify department correctly and on the right cell";
                    }
                    try {
                        creatorHeader = (String) headers.get(IDX_CRT);
                    } catch (Exception exc) {
                        creatorHeader = "Please specify creator of schedule correctly and on the right cell";
                    }
                    try {
                        periodHeader = (String) headers.get(IDX_PER);
                    } catch (Exception exc) {
                        periodHeader = "Please specify period schedule correctly and on the right cell";
                    }
                }

                // hapus approval & symbol (sembilan rows/baris paling bawah)
                x = v.size() - (ROWS_NUM_FOOTER_DEL * numcol);
                int sizeDef = x - 1;
                while (x < v.size()) {
                    try {
                        v.remove(v.size() - 1);
                    } catch (Exception e) {
                        System.out.println("Exception when delete approval & symbol (footer) : " + e.toString());
                    }
                }



                // Tampilkan data dengan list berupa table
                boolean useDayHeader = false;
                drawList.append("<form name=\"frmupload\" method=\"post\" action=\"\">" +
                        "\n<input type=\"hidden\" name=\"command\" value=\"" + iCommand + "\">");

                if (v.size() > 0) {
                    drawList.append("\n<table cellpadding=\"2\" cellspacing=\"2\" border=\"0\">" +
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"3\"><B><font size=\"3\">" + topHeader + "</font></B></td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"2\"><B>DEPARTMENT</B></td>" +
                            "\n\t\t<td>" + depHeader + "</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"2\"><B>CREATED BY</B></td>" +
                            "\n\t\t<td> " + creatorHeader + "</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td>&nbsp;</td>" +
                            "\n\t\t<td></td>" +
                            "\n\t\t<td></td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td><li></td>" +
                            "\n\t\t<td colspan=\"2\">Choose the Period of Working Schedule</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td>&nbsp;</td>" +
                            "\n\t\t<td>Period</td>" +
                            "\n\t\t<td>" + ControlCombo.draw("period_id", "formElemen", "select...", "" + periodId, periodValue, periodKey) + "</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td><li></td>" +
                            "\n\t\t<td colspan=\"2\">List of Working Schedule</td>" +
                            "\n\t</tr>" +
                            "\n</table>" +
                            "\n<table cellpadding=\"1\" cellspacing=\"1\" border=\"0\" width=\"100%\" class=\"listgen\">" +
                            "\n\t<tr>" +
                            "\n\t\t<td width=\"1%\" " + (useDayHeader ? "rowspan=\"3\"" : "rowspan=\"2\"") + " class=\"tableheader\">No</td>" +
                            "\n\t\t<td width=\"15%\" " + (useDayHeader ? "rowspan=\"3\"" : "rowspan=\"2\"") + " class=\"tableheader\">Employee</td>" +
                            "\n\t\t<td width=\"8%\" " + (useDayHeader ? "rowspan=\"3\"" : "rowspan=\"2\"") + " class=\"tableheader\">Payroll</td>" +
                            "\n\t\t<td width=\"77%\" colspan=\"" + (numcol - 2) + "\" align=\"center\" class=\"tableheader\">Date</td>" +
                            "\n\t</tr>");


                    // nilai 77 ==> persentase lebar kolom yang masih tersedia untuk schedule
                    double width = 77 / (new Double(numcol)).doubleValue();

                    if (useDayHeader) {
                        // untuk header (day symbol)																
                        drawList.append("\n\t<tr class=\"listheader\">");
                        for (int i = numcol; i < (numcol + numcol); i++) {
                            if ((i % numcol) > 1) {
                                drawList.append("\n\t\t<td align=\"center\" width=\"" + width + "%\" class=\"tableheader\">" + (String.valueOf(v.elementAt(numcol))).substring(0, 1) + "</td>");
                            }
                            v.remove(numcol);
                        }
                        drawList.append("\n\t</tr>");
                    } else {
                        // untuk header (day symbol)																
                        for (int i = numcol; i < (numcol + numcol); i++) {
                            v.remove(numcol);
                        }
                    }


                    // untuk header (date symbol)	
                    drawList.append("\n\t<tr class=\"listheader\">");
                    for (int h = numcol; h < (numcol + numcol); h++) {
                        if ((h % numcol) > 1) {
                            String dt = String.valueOf(v.elementAt(numcol));
                            drawList.append("\n\t\t<td align=\"center\" width=\"" + width + "%\" class=\"tableheader\">" + dt.substring(0, dt.indexOf(".")) + "</td>");
                        }
                        v.remove(numcol);
                    }
                    drawList.append("\n\t</tr>\n\t<tr>");


                    Hashtable hashSchedule = new Hashtable();
                    hashSchedule.put(" ", "0");

                    Hashtable hashCategory = new Hashtable();
                    hashCategory.put(" ", "0");

                    Vector listSchSymbol = PstScheduleSymbol.listScheduleSymbolAndCategory();
                    if (listSchSymbol != null && listSchSymbol.size() > 0) {
                        int intListSchSymbol = listSchSymbol.size();
                        for (int ls = 0; ls < intListSchSymbol; ls++) {
                            try {
                                Vector vectSchldCat = (Vector) listSchSymbol.get(ls);
                                ScheduleSymbol schSymbol = (ScheduleSymbol) vectSchldCat.get(0);
                                ScheduleCategory schCategory = (ScheduleCategory) vectSchldCat.get(1);

                                hashSchedule.put(schSymbol.getSymbol(), String.valueOf(schSymbol.getOID()));
                                hashCategory.put(schSymbol.getSymbol(), String.valueOf(schCategory.getCategoryType()));
                            } catch (Exception exc) {
                                out.println("Error symbol on : ls=" + ls);
                            }
                        }
                    }


                    Hashtable hashPayroll = new Hashtable();
                    Hashtable hashEmpNum = new Hashtable();
                    String whereC = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0";
                    Vector listEmployee = PstEmployee.list(0, 0, whereC, "");

                    for (int e = 0; e < listEmployee.size(); e++) {
                        Employee employee = (Employee) listEmployee.get(e);
                        hashPayroll.put(employee.getEmployeeNum(), String.valueOf(employee.getOID()));
                        hashEmpNum.put(employee.getEmployeeNum(), employee.getEmployeeNum());
                    }

                    // inisialisalsi proses utk memberi warna row yg error
                    int inIterasi = 33;	 // di set 33 karena maksimum sisa hasil bagi dengan jumlah kolom (33) adalah 32														
                    int idxScheduleErr = 0;

                    String scdSymbol = "";
                    for (int i = numcol; i < v.size(); i++) {
                        // variabel yang akan menghandle nama schedule
                        String sch = "";
                        String ndsch = "";
                        String writeSchd = "";

                        String tmp1stSchd = "";
                        String tmp2ndSchd = "";

                        scdSymbol = String.valueOf(v.elementAt(i));

                        int indexSeparator = scdSymbol.indexOf(SPIT_SHIFT_SEPARATOR);
                        if (indexSeparator > 0) {
                            tmp1stSchd = scdSymbol.substring(0, indexSeparator);
                            tmp2ndSchd = scdSymbol.substring(indexSeparator + 1);
                        } else {
                            tmp1stSchd = scdSymbol;
                        }


                        if (hashSchedule.get(tmp1stSchd.trim()) == null) {
                            sch = "?";
                        } else {
                            sch = tmp1stSchd.trim();
                        }

                        if (hashSchedule.get(tmp2ndSchd.trim()) == null) {
                            ndsch = "?";
                        } else {
                            ndsch = tmp2ndSchd.trim();
                        }

                        writeSchd = sch;
                        if (!ndsch.equals("?")) {
                            writeSchd = writeSchd + "/" + ndsch;
                        }


                        // mencari rows data yang tidak bisa diproses karena ada DP, AL atau LL yg ga sesuai
                        boolean importScheduleErr = false;
                        String clsHtml = "class=\"listgensell\"";
                        if (i < (v.size() - 1)) {
                            importScheduleErr = hashEmployeeError.containsValue(v.elementAt(i + 1));
                            if (importScheduleErr) {
                                inIterasi = 0;
                            } else {
                                inIterasi++;
                            }
                        }

                        if (inIterasi <= 32) // karena dalam satu baris terdiri dari 33 kolom, maka indexnya dari 0 - 32
                        {
                            clsHtml = "class=\"listgensellerror\"";
                        }


                        // menambahkan warna / sign utk index schedule leave yg error disesuaikan dengan DP, AL dan LL																																
                        int idxWillColored = 0;
                        String clsHtmlIdxScheduleErrPre = "";
                        String clsHtmlIdxScheduleErrPost = "";
                        String strTemp = "0";
                        if (hashLeaveDataError.get(v.elementAt(i)) != null && String.valueOf(hashLeaveDataError.get(v.elementAt(i))).length() > 0) {
                            strTemp = String.valueOf(hashLeaveDataError.get(v.elementAt(i)));
                            if (Integer.parseInt(strTemp) > 0) {
                                idxScheduleErr = Integer.parseInt(strTemp);
                            }
                        }

                        // dikuranghi 3 karena index date schedule mulai pada "sisa hasil bagi==3"			
                        if (idxScheduleErr > 0 && idxScheduleErr == (((i + 1) % numcol) - 3)) {
                            idxScheduleErr = 0;
                            clsHtmlIdxScheduleErrPre = "<span class=\"errfont\">";
                            clsHtmlIdxScheduleErrPost = "</span>";
                        }


                        switch (((i + 1) % numcol)) {
                            case 1:
                                drawList.append("\n\t\t<td " + clsHtml + ">" + ((i / numcol)) + "</td><td " + clsHtml + " width=\"" + width + "%\">" + v.elementAt(i) + "</td>");
                                break;

                            case 2:
                                String payroll = "";

                                String dataPayroll = (String) v.elementAt(i);
                                int idx = dataPayroll.indexOf("'");
                                if (idx > -1) {
                                    dataPayroll = dataPayroll.substring(2, dataPayroll.length());
                                }

                                if (hashPayroll.get("" + dataPayroll) == null) {
                                    payroll = "?";
                                } else {
                                    payroll = "" + dataPayroll;
                                }
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\">");
                                drawList.append("<input type=\"hidden\" name=\"employee_id\" value=\"" + hashPayroll.get(v.elementAt(i)) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"employee_num\" value=\"" + hashEmpNum.get(v.elementAt(i)) + "\">");
                                drawList.append(payroll + "</td>");
                                break;

                            case 3:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D1\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND1\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT1\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 4:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D2\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND2\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT2\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 5:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D3\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND3\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT3\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 6:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D4\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND4\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT4\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 7:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D5\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND5\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT5\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 8:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D6\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND6\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT6\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 9:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D7\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND7\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT7\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 10:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D8\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND8\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT8\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 11:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D9\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND9\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT9\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 12:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D10\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND10\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT10\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 13:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D11\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND11\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT11\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 14:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D12\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND12\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT12\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 15:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D13\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND13\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT13\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 16:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D14\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND14\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT14\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 17:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D15\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND15\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT15\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 18:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D16\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND16\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT16\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 19:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D17\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND17\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT17\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 20:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D18\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND18\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT18\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 21:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D19\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND19\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT19\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 22:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D20\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND20\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT20\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 23:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D21\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND21\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT21\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 24:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D22\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND22\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT22\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 25:
                                drawList.append("\n\t\t<td  " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D23\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND23\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT23\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 26:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D24\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND24\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT24\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 27:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D25\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND25\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT25\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 28:
                                drawList.append("\n\t\t<td  " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D26\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND26\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT26\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 29:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D27\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND27\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT27\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 30:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D28\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND28\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT28\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 31:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D29\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND29\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT29\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 32:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D30\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND30\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT30\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 0:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D31\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND31\" value=\"" + hashSchedule.get(ndsch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT31\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            default:
                                break;
                        }

                        if (((i + 1) % numcol) == 0) {
                            drawList.append("\n\t</tr> " + ((i != v.size() - 1) ? "\n\t<tr>" : "\n</table>"));
                        }
                    }


                    drawList.append("<br>" +
                            "\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">");
                    if (iCommand == Command.SAVE && (msgString != null && msgString.length() > 0)) {
                        drawList.append("\n\t<tr>" +
                                "\n\t\t<td colspan=\"4\">" + msgString + "</td>" +
                                "\n\t</tr>" +
                                "\n\t<tr>" +
                                "\n\t\t<td colspan=\"4\">&nbsp;</td>" +
                                "\n\t</tr>");
                    }

                    /*
                    else
                    {
                    drawList.append("\n\t<tr>"+
                    "\n\t\t<td colspan=\"4\">"+
                    "\n\t\t<table width=\"100%\" border=\"0\">"+
                    "\n\t\t\t<tr>"+ 
                    "\n\t\t\t\t<td class=\"errfont\">* This process will take a few minutes <b>only if</b> employee presence data for each of this schedule already exist ...</td>"+
                    "\n\t\t\t</tr>"+
                    "\n\t\t\t<tr>"+
                    "\n\t\t\t\t<td>&nbsp;&nbsp;<span class=\"errfont\">Another process automatically run with, are : </span></td>"+
                    "\n\t\t\t</tr>"+
                    "\n\t\t\t<tr>"+ 
                    "\n\t\t\t\t<td class=\"errfont\">&nbsp;&nbsp;&nbsp;&nbsp;- <b><i>Import and check presence data</i></b> appropriate to this schedule.</td>"+
                    "\n\t\t\t</tr>"+
                    "\n\t\t\t<tr>"+ 
                    "\n\t\t\t\t<td class=\"errfont\">&nbsp;&nbsp;&nbsp;&nbsp;- <b><i>Analyze absenteeism</i></b> based on imported presence data.</td>"+
                    "\n\t\t\t</tr>"+
                    "\n\t\t\t<tr>"+ 
                    "\n\t\t\t<td class=\"errfont\">&nbsp;&nbsp;&nbsp;&nbsp;- <b><i>Analyze lateness</i></b> based on imported presence data.</td>"+
                    "\n\t\t\t\t</tr>"+
                    "\n\t\t\t</table>"+
                    "\n\t\t</td>"+
                    "\n\t</tr>"+
                    "\n\t<tr>"+
                    "\n\t\t<td colspan=\"4\">&nbsp;</td>"+
                    "\n\t</tr>");																																			
                    }
                     */


                    //if (iCommand != Command.SAVE) {
                    drawList.append("\n\t<tr>" +
                            "\n\t\t<td width=\"1%\"><img src=\"" + approot + "/images/spacer.gif\" width=\"4\" height=\"4\"></td>" +
                            "\n\t\t<td width=\"5%\"><a href=\"javascript:cmdSave()\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image300','','" + approot + "/images/BtnSaveOn.jpg',1)\"><img name=\"Image300\" border=\"0\" src=\"" + approot + "/images/BtnSave.jpg\" width=\"24\" height=\"24\" alt=\"Save\"></a></td>" +
                            "\n\t\t<td width=\"1%\"><img src=\"" + approot + "/images/spacer.gif\" width=\"4\" height=\"4\"></td>" +
                            "\n\t\t<td width=\"93%\"nowrap> <a href=\"javascript:cmdSave()\" class=\"command\">Save Working Schedule</a></td>" +
                            "\n\t</tr>");
                    //   }
                    drawList.append("\n</table>");
                }
                drawList.append("</form>");
                if (iCommand != Command.SAVE) {
                    inStream.close();
                }
            } catch (Exception e) {
                System.out.println("---======---\nError : " + e);
            }
            if (drawList != null && drawList.length() > 0) {
                                %>
                                <%=drawList%> 
                                                                                <% } %><br><br>
                                                                                <!-- #EndEditable --> 
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
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
