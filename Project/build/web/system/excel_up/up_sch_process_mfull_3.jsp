
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
<%@ page import="com.dimata.harisma.session.leave.*" %>

<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ page import="com.dimata.harisma.entity.leave.PstDpApplication" %>
<%@ page import="com.dimata.harisma.entity.leave.PstLeaveApplication" %>
<%@ page import="com.dimata.harisma.form.attendance.CtrlDpStockManagement" %>
<%@ page import="com.dimata.harisma.session.attendance.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>

<%
            int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));

            int NUM_DATE = 31;      
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

            //int intAdvanceDp = 100;
            String msgString = "";
            boolean noErrWithLeaveStock = true;
            Hashtable hashEmployeeError = new Hashtable();
            Hashtable hashLeaveDataError = new Hashtable();
            
            if (iCommand == Command.SAVE){
                // jika periodId == 0, maka tampilkan pesan agar period schedule dipilih
                if (periodId == 0) {
                    msgString = "<div class=\"errfont\">Please choose the Period of Working Schedule !</div>";
                
                }else {// jika period != 0, maka proses import data schedule dari file excel
                    
                    // mengambil data dari form dengan querystring dan simpan ke array of data String
                    String[] employeeId = request.getParameterValues("employee_id");
                    //String[] employeeNum = request.getParameterValues("employee_num");
                    
                    // create code pengambilan hidden data dari form yang di create dynamic
                    // sesuaikan dengan awal mulai periode
                    Vector vDCode = new Vector();
                    Vector vD2Code = new Vector();
                    Vector vCCode = new Vector();
                    
                    for(int c=31-startDatePeriod+2;c<=31;c++){  
                        vDCode.add("D"+c);      // mulai dari start Date Periode ( contoh tgl. 1 , tgl. 20 sampai tgl. 31
                        vD2Code.add("D2ND"+c);
                        vCCode.add("CAT"+c);
                    }
                    for(int c=1;c<(31-startDatePeriod+2);c++){
                        vDCode.add("D"+c);  // lanjutkan dari tanggal 1 sampai satu tanggal sebelum start periode
                        vD2Code.add("D2ND"+c);
                        vCCode.add("CAT"+c);
                    }
                    
                    String[] d1 = request.getParameterValues((String) vDCode.get(1-1));
                    String[] d2 = request.getParameterValues((String) vDCode.get(2-1));
                    String[] d3 = request.getParameterValues((String) vDCode.get(3-1));
                    String[] d4 = request.getParameterValues((String) vDCode.get(4-1));
                    String[] d5 = request.getParameterValues((String) vDCode.get(5-1));
                    String[] d6 = request.getParameterValues((String) vDCode.get(6-1));
                    String[] d7 = request.getParameterValues((String) vDCode.get(7-1));
                    String[] d8 = request.getParameterValues((String) vDCode.get(8-1));
                    String[] d9 = request.getParameterValues((String) vDCode.get(9-1));
                    String[] d10 = request.getParameterValues((String) vDCode.get(10-1));
                    String[] d11 = request.getParameterValues((String) vDCode.get(11-1));
                    String[] d12 = request.getParameterValues((String) vDCode.get(12-1));
                    String[] d13 = request.getParameterValues((String) vDCode.get(13-1));
                    String[] d14 = request.getParameterValues((String) vDCode.get(14-1));
                    String[] d15 = request.getParameterValues((String) vDCode.get(15-1));
                    String[] d16 = request.getParameterValues((String) vDCode.get(16-1));
                    String[] d17 = request.getParameterValues((String) vDCode.get(17-1));
                    String[] d18 = request.getParameterValues((String) vDCode.get(18-1));
                    String[] d19 = request.getParameterValues((String) vDCode.get(19-1));
                    String[] d20 = request.getParameterValues((String) vDCode.get(20-1));
                    String[] d21 = request.getParameterValues((String) vDCode.get(21-1));
                    String[] d22 = request.getParameterValues((String) vDCode.get(22-1));
                    String[] d23 = request.getParameterValues((String) vDCode.get(23-1));
                    String[] d24 = request.getParameterValues((String) vDCode.get(24-1));
                    String[] d25 = request.getParameterValues((String) vDCode.get(25-1));
                    String[] d26 = request.getParameterValues((String) vDCode.get(26-1));
                    String[] d27 = request.getParameterValues((String) vDCode.get(27-1));
                    String[] d28 = request.getParameterValues((String) vDCode.get(28-1));
                    String[] d29 = request.getParameterValues((String) vDCode.get(29-1));
                    String[] d30 = request.getParameterValues((String) vDCode.get(30-1));
                    String[] d31 = request.getParameterValues((String) vDCode.get(31-1));

                    String[] d2nd1 = request.getParameterValues((String) vD2Code.get(1-1));
                    String[] d2nd2 = request.getParameterValues((String) vD2Code.get(2-1));
                    String[] d2nd3 = request.getParameterValues((String) vD2Code.get(3-1));
                    String[] d2nd4 = request.getParameterValues((String) vD2Code.get(4-1));
                    String[] d2nd5 = request.getParameterValues((String) vD2Code.get(5-1));
                    String[] d2nd6 = request.getParameterValues((String) vD2Code.get(6-1));
                    String[] d2nd7 = request.getParameterValues((String) vD2Code.get(7-1));
                    String[] d2nd8 = request.getParameterValues((String) vD2Code.get(8-1));
                    String[] d2nd9 = request.getParameterValues((String) vD2Code.get(9-1));
                    String[] d2nd10 = request.getParameterValues((String) vD2Code.get(10-1));
                    String[] d2nd11 = request.getParameterValues((String) vD2Code.get(11-1));
                    String[] d2nd12 = request.getParameterValues((String) vD2Code.get(12-1));
                    String[] d2nd13 = request.getParameterValues((String) vD2Code.get(13-1));
                    String[] d2nd14 = request.getParameterValues((String) vD2Code.get(14-1));
                    String[] d2nd15 = request.getParameterValues((String) vD2Code.get(15-1));
                    String[] d2nd16 = request.getParameterValues((String) vD2Code.get(16-1));
                    String[] d2nd17 = request.getParameterValues((String) vD2Code.get(17-1));
                    String[] d2nd18 = request.getParameterValues((String) vD2Code.get(18-1));
                    String[] d2nd19 = request.getParameterValues((String) vD2Code.get(19-1));
                    String[] d2nd20 = request.getParameterValues((String) vD2Code.get(20-1));
                    String[] d2nd21 = request.getParameterValues((String) vD2Code.get(21-1));
                    String[] d2nd22 = request.getParameterValues((String) vD2Code.get(22-1));
                    String[] d2nd23 = request.getParameterValues((String) vD2Code.get(23-1));
                    String[] d2nd24 = request.getParameterValues((String) vD2Code.get(24-1));
                    String[] d2nd25 = request.getParameterValues((String) vD2Code.get(25-1));
                    String[] d2nd26 = request.getParameterValues((String) vD2Code.get(26-1));
                    String[] d2nd27 = request.getParameterValues((String) vD2Code.get(27-1));
                    String[] d2nd28 = request.getParameterValues((String) vD2Code.get(28-1));
                    String[] d2nd29 = request.getParameterValues((String) vD2Code.get(29-1));
                    String[] d2nd30 = request.getParameterValues((String) vD2Code.get(30-1));
                    String[] d2nd31 = request.getParameterValues((String) vD2Code.get(31-1));

                    String[] cat1 = request.getParameterValues((String) vCCode.get(1-1));
                    String[] cat2 = request.getParameterValues((String) vCCode.get(2-1));
                    String[] cat3 = request.getParameterValues((String) vCCode.get(3-1));
                    String[] cat4 = request.getParameterValues((String) vCCode.get(4-1));
                    String[] cat5 = request.getParameterValues((String) vCCode.get(5-1));
                    String[] cat6 = request.getParameterValues((String) vCCode.get(6-1));
                    String[] cat7 = request.getParameterValues((String) vCCode.get(7-1));
                    String[] cat8 = request.getParameterValues((String) vCCode.get(8-1));
                    String[] cat9 = request.getParameterValues((String) vCCode.get(9-1));
                    String[] cat10 = request.getParameterValues((String) vCCode.get(10-1));
                    String[] cat11 = request.getParameterValues((String) vCCode.get(11-1));
                    String[] cat12 = request.getParameterValues((String) vCCode.get(12-1));
                    String[] cat13 = request.getParameterValues((String) vCCode.get(13-1));
                    String[] cat14 = request.getParameterValues((String) vCCode.get(14-1));
                    String[] cat15 = request.getParameterValues((String) vCCode.get(15-1));
                    String[] cat16 = request.getParameterValues((String) vCCode.get(16-1));
                    String[] cat17 = request.getParameterValues((String) vCCode.get(17-1));
                    String[] cat18 = request.getParameterValues((String) vCCode.get(18-1));
                    String[] cat19 = request.getParameterValues((String) vCCode.get(19-1));
                    String[] cat20 = request.getParameterValues((String) vCCode.get(20-1));
                    String[] cat21 = request.getParameterValues((String) vCCode.get(21-1));
                    String[] cat22 = request.getParameterValues((String) vCCode.get(22-1));
                    String[] cat23 = request.getParameterValues((String) vCCode.get(23-1));
                    String[] cat24 = request.getParameterValues((String) vCCode.get(24-1));
                    String[] cat25 = request.getParameterValues((String) vCCode.get(25-1));
                    String[] cat26 = request.getParameterValues((String) vCCode.get(26-1));
                    String[] cat27 = request.getParameterValues((String) vCCode.get(27-1));
                    String[] cat28 = request.getParameterValues((String) vCCode.get(28-1));
                    String[] cat29 = request.getParameterValues((String) vCCode.get(29-1));
                    String[] cat30 = request.getParameterValues((String) vCCode.get(30-1));
                    String[] cat31 = request.getParameterValues((String) vCCode.get(31-1));

                    Vector specialSchedule = new Vector();
                    
                    specialSchedule  = SessLeaveApplication.getSpecialUnpaidLeave();
                    // iterasi sebanyak record employee schedule dalam file excel
                    // untuk melakukan pengecekan data schedule sehingga bisa di-"insert" atau di-"update"
                    
                    for (int e = 0; (e < employeeId.length) && ( e<5000); e++) {

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

                        /* Add schedule category to vector, used to check status leave (DP, AL dan LL) */
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
                       
                        long empId = (employeeId[e].equals("null")) ? 0 : Long.parseLong("" + employeeId[e]);
                        
                        boolean checkLeaveScheduleOk = true;
                        	
                        noErrWithLeaveStock = noErrWithLeaveStock && checkLeaveScheduleOk;
                        
                        //if (checkLeaveScheduleOk || true){
                            checkLeaveScheduleOk = true;
                            
                            if (checkLeaveScheduleOk){
                                // jika schedule utk employee ada dalam db, maka lakukan proses "update"
                            if (vcheck.size() > 0) {
                                //update
                                try {
                                    
                                    EmpSchedule empSchedule = (EmpSchedule) vcheck.get(0);

                                    //empSchedule.setEmployeeId(Long.parseLong((employeeId[e].equals("null")) ? "0" : "" + employeeId[e]));

                                    EmpSchedule objEmpSchedule = new EmpSchedule();
                                    
                                    try{
                                        objEmpSchedule = PstEmpSchedule.fetchExc(empSchedule.getOID());
                                    }catch(Exception E){
                                        System.out.println("[exception] "+E.toString());
                                    }
                                    
                                    Date currDate = new Date();
                                    int dt = currDate.getDate();
                                    Period p = PstPeriod.fetchExc(periodId);
                                    dt = 1; //sementara schedule bisa diupdate semua walau sudah lewat periode
                                    
                                    if (empSchedule.getEmployeeId() != 0){                                        
                                        
                                        empSchedule.setPeriodId(periodId);
                                        
                                        if (dt == 1) {

                                            boolean scheduleLeave = false;

                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD1(),specialSchedule); 
                                            }catch(Exception E){
                                                System.out.println("[exception] "+E.toString());
                                            }
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD1(objEmpSchedule.getD1());
                                            }else{
                                                empSchedule.setD1(d1[e].equals("null") ? 0 : Long.parseLong("" + d1[e]));                                                                                        
                                            }
                                        }
                                        if (dt <= 2) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2(),specialSchedule); 
                                            }catch(Exception E){
                                                System.out.println("[exception] "+E.toString());
                                            }
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2(objEmpSchedule.getD2());
                                            }else{
                                                empSchedule.setD2(d2[e].equals("null") ? 0 : Long.parseLong("" + d2[e]));
                                            }
                                        }
                                        if (dt <= 3) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD3(),specialSchedule); 
                                            }catch(Exception E){
                                                System.out.println("[exception] "+E.toString());
                                            }
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD3(objEmpSchedule.getD3());
                                            }else{                                            
                                                empSchedule.setD3(d3[e].equals("null") ? 0 : Long.parseLong("" + d3[e]));
                                            }
                                            
                                        }
                                        if (dt <= 4) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD4(),specialSchedule); 
                                            }catch(Exception E){
                                                System.out.println("[exception] "+E.toString());
                                            }
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD4(objEmpSchedule.getD4());
                                            }else{                                            
                                                empSchedule.setD4(d4[e].equals("null") ? 0 : Long.parseLong("" + d4[e]));
                                            }
                                        }
                                        if (dt <= 5) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD5(),specialSchedule); 
                                            }catch(Exception E){
                                                System.out.println("[exception] "+E.toString());
                                            }
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD5(objEmpSchedule.getD5());
                                            }else{
                                                empSchedule.setD5(d5[e].equals("null") ? 0 : Long.parseLong("" + d5[e]));
                                            }
                                        }
                                        if (dt <= 6) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD6(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD6(objEmpSchedule.getD6());
                                            }else{
                                                empSchedule.setD6(d6[e].equals("null") ? 0 : Long.parseLong("" + d6[e]));
                                            }
                                        }
                                        if (dt <= 7) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD7(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD7(objEmpSchedule.getD7());
                                            }else{                                            
                                                empSchedule.setD7(d7[e].equals("null") ? 0 : Long.parseLong("" + d7[e]));
                                            }
                                        }
                                        if (dt <= 8) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD8(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD8(objEmpSchedule.getD8());
                                            }else{
                                                empSchedule.setD8(d8[e].equals("null") ? 0 : Long.parseLong("" + d8[e]));
                                            }
                                        }
                                        if (dt <= 9) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD9(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD9(objEmpSchedule.getD9());
                                            }else{
                                            empSchedule.setD9(d9[e].equals("null") ? 0 : Long.parseLong("" + d9[e]));
                                            }
                                        }
                                        if (dt <= 10) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD10(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD10(objEmpSchedule.getD10());
                                            }else{
                                                empSchedule.setD10(d10[e].equals("null") ? 0 : Long.parseLong("" + d10[e]));
                                            }
                                        }
                                        if (dt <= 11) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD11(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD11(objEmpSchedule.getD11());
                                            }else{
                                                empSchedule.setD11(d11[e].equals("null") ? 0 : Long.parseLong("" + d11[e]));
                                            }
                                        }
                                        if (dt <= 12) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD12(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD12(objEmpSchedule.getD12());
                                            }else{
                                                empSchedule.setD12(d12[e].equals("null") ? 0 : Long.parseLong("" + d12[e]));
                                            }
                                        }
                                        if (dt <= 13) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD13(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD13(objEmpSchedule.getD13());
                                            }else{
                                                empSchedule.setD13(d13[e].equals("null") ? 0 : Long.parseLong("" + d13[e]));
                                            }    
                                        }
                                        if (dt <= 14) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD14(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD14(objEmpSchedule.getD14());
                                            }else{                                             
                                              empSchedule.setD14(d14[e].equals("null") ? 0 : Long.parseLong("" + d14[e]));
                                            }    
                                        }
                                        if (dt <= 15) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD15(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD15(objEmpSchedule.getD15());
                                            }else{                                             
                                                empSchedule.setD15(d15[e].equals("null") ? 0 : Long.parseLong("" + d15[e]));
                                            }
                                        }
                                        if (dt <= 16) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD16(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD16(objEmpSchedule.getD16());
                                            }else{
                                                empSchedule.setD16(d16[e].equals("null") ? 0 : Long.parseLong("" + d16[e]));
                                            }
                                        }
                                        if (dt <= 17) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD17(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD17(objEmpSchedule.getD17());
                                            }else{
                                                empSchedule.setD17(d17[e].equals("null") ? 0 : Long.parseLong("" + d17[e]));
                                            }    
                                        }
                                        if (dt <= 18) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD18(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD18(objEmpSchedule.getD18());
                                            }else{
                                                empSchedule.setD18(d18[e].equals("null") ? 0 : Long.parseLong("" + d18[e]));
                                            }    
                                        }
                                        if (dt <= 19) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD19(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD19(objEmpSchedule.getD19());
                                            }else{
                                                empSchedule.setD19(d19[e].equals("null") ? 0 : Long.parseLong("" + d19[e]));
                                            }
                                        }
                                        if (dt <= 20) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD20(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD20(objEmpSchedule.getD20());
                                            }else{
                                                empSchedule.setD20(d20[e].equals("null") ? 0 : Long.parseLong("" + d20[e]));
                                            }
                                        }
                                        if (dt <= 21) {
                                            
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD21(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD21(objEmpSchedule.getD21());
                                            }else{
                                                empSchedule.setD21(d21[e].equals("null") ? 0 : Long.parseLong("" + d21[e]));
                                            }
                                        }
                                        if (dt <= 22) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD22(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD22(objEmpSchedule.getD22());
                                            }else{
                                                empSchedule.setD22(d22[e].equals("null") ? 0 : Long.parseLong("" + d22[e]));
                                            }
                                        }
                                        if (dt <= 23) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD23(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD23(objEmpSchedule.getD23());
                                            }else{
                                                empSchedule.setD23(d23[e].equals("null") ? 0 : Long.parseLong("" + d23[e]));
                                            }    
                                        }
                                        if (dt <= 24) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD24(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD24(objEmpSchedule.getD24());
                                            }else{
                                                empSchedule.setD24(d24[e].equals("null") ? 0 : Long.parseLong("" + d24[e]));
                                            }
                                        }
                                        if (dt <= 25) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD25(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD25(objEmpSchedule.getD25());
                                            }else{
                                                empSchedule.setD25(d25[e].equals("null") ? 0 : Long.parseLong("" + d25[e]));
                                            }
                                        }
                                        if (dt <= 26) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD26(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD26(objEmpSchedule.getD26());
                                            }else{
                                                empSchedule.setD26(d26[e].equals("null") ? 0 : Long.parseLong("" + d26[e]));
                                            }
                                        }
                                        if (dt <= 27) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD27(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD27(objEmpSchedule.getD27());
                                            }else{
                                                empSchedule.setD27(d27[e].equals("null") ? 0 : Long.parseLong("" + d27[e]));
                                            }
                                        }
                                        if (dt <= 28) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD28(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD28(objEmpSchedule.getD28());
                                            }else{
                                                empSchedule.setD28(d28[e].equals("null") ? 0 : Long.parseLong("" + d28[e]));
                                            }
                                        }
                                        if (dt <= 29) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD29(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD29(objEmpSchedule.getD29());
                                            }else{
                                                empSchedule.setD29(d29[e].equals("null") ? 0 : Long.parseLong("" + d29[e]));
                                            }
                                        }
                                        if (dt <= 30) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD30(),specialSchedule); 
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD30(objEmpSchedule.getD30());
                                            }else{
                                                empSchedule.setD30(d30[e].equals("null") ? 0 : Long.parseLong("" + d30[e]));
                                            }
                                        }
                                        if (dt <= 31) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD31(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD31(objEmpSchedule.getD31());
                                            }else{
                                                empSchedule.setD31(d31[e].equals("null") ? 0 : Long.parseLong("" + d31[e]));
                                            }
                                        }
                                        if (dt == 1) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd1(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd1(objEmpSchedule.getD2nd1());
                                            }else{
                                                empSchedule.setD2nd1(d2nd1[e].equals("null") ? 0 : Long.parseLong("" + d2nd1[e]));
                                            }    
                                        }
                                        if (dt <= 2) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd2(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd2(objEmpSchedule.getD2nd2());
                                            }else{
                                                empSchedule.setD2nd2(d2nd2[e].equals("null") ? 0 : Long.parseLong("" + d2nd2[e]));
                                            }
                                        }
                                        if (dt <= 3) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd3(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd3(objEmpSchedule.getD2nd3());
                                            }else{
                                                empSchedule.setD2nd3(d2nd3[e].equals("null") ? 0 : Long.parseLong("" + d2nd3[e]));
                                            }   
                                        }
                                        if (dt <= 4) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd4(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd4(objEmpSchedule.getD2nd4());
                                            }else{
                                                empSchedule.setD2nd4(d2nd4[e].equals("null") ? 0 : Long.parseLong("" + d2nd4[e]));
                                            }
                                        }
                                        if (dt <= 5) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd5(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd5(objEmpSchedule.getD2nd5());
                                            }else{
                                                empSchedule.setD2nd5(d2nd5[e].equals("null") ? 0 : Long.parseLong("" + d2nd5[e]));
                                            }
                                        }
                                        if (dt <= 6) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd6(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd6(objEmpSchedule.getD2nd6());
                                            }else{
                                                empSchedule.setD2nd6(d2nd6[e].equals("null") ? 0 : Long.parseLong("" + d2nd6[e]));
                                            }
                                        }
                                        if (dt <= 7) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd7(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd7(objEmpSchedule.getD2nd7());
                                            }else{
                                                empSchedule.setD2nd7(d2nd7[e].equals("null") ? 0 : Long.parseLong("" + d2nd7[e]));
                                            }
                                        }
                                        if (dt <= 8) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd8(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd8(objEmpSchedule.getD2nd8());
                                            }else{
                                                empSchedule.setD2nd8(d2nd8[e].equals("null") ? 0 : Long.parseLong("" + d2nd8[e]));
                                            }
                                        }
                                        if (dt <= 9) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd9(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd9(objEmpSchedule.getD2nd9());
                                            }else{
                                                empSchedule.setD2nd9(d2nd9[e].equals("null") ? 0 : Long.parseLong("" + d2nd9[e]));
                                            }    
                                        }
                                        if (dt <= 10) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd10(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd10(objEmpSchedule.getD2nd10());
                                            }else{
                                                empSchedule.setD2nd10(d2nd10[e].equals("null") ? 0 : Long.parseLong("" + d2nd10[e]));
                                            }
                                        }
                                        if (dt <= 11) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd11(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd11(objEmpSchedule.getD2nd11());
                                            }else{
                                                empSchedule.setD2nd11(d2nd11[e].equals("null") ? 0 : Long.parseLong("" + d2nd11[e]));
                                            }    
                                        }
                                        if (dt <= 12) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd12(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd12(objEmpSchedule.getD2nd12());
                                            }else{
                                                empSchedule.setD2nd12(d2nd12[e].equals("null") ? 0 : Long.parseLong("" + d2nd12[e]));
                                            }
                                        }
                                        if (dt <= 13) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd13(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd13(objEmpSchedule.getD2nd13());
                                            }else{
                                                empSchedule.setD2nd13(d2nd13[e].equals("null") ? 0 : Long.parseLong("" + d2nd13[e]));
                                            }
                                        }
                                        if (dt <= 14) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd14(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd14(objEmpSchedule.getD2nd14());
                                            }else{
                                                empSchedule.setD2nd14(d2nd14[e].equals("null") ? 0 : Long.parseLong("" + d2nd14[e]));
                                            }        
                                        }
                                        if (dt <= 15) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd15(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd15(objEmpSchedule.getD2nd15());
                                            }else{
                                                empSchedule.setD2nd15(d2nd15[e].equals("null") ? 0 : Long.parseLong("" + d2nd15[e]));
                                            }
                                        }
                                        if (dt <= 16) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd16(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd16(objEmpSchedule.getD2nd16());
                                            }else{
                                                empSchedule.setD2nd16(d2nd16[e].equals("null") ? 0 : Long.parseLong("" + d2nd16[e]));
                                            }
                                        }
                                        if (dt <= 17) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd17(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd17(objEmpSchedule.getD2nd17());
                                            }else{
                                                empSchedule.setD2nd17(d2nd17[e].equals("null") ? 0 : Long.parseLong("" + d2nd17[e]));
                                            }
                                        }
                                        if (dt <= 18) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd18(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd18(objEmpSchedule.getD2nd18());
                                            }else{
                                                empSchedule.setD2nd18(d2nd18[e].equals("null") ? 0 : Long.parseLong("" + d2nd18[e]));
                                            }
                                        }
                                        if (dt <= 19) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd19(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd19(objEmpSchedule.getD2nd19());
                                            }else{
                                                empSchedule.setD2nd19(d2nd19[e].equals("null") ? 0 : Long.parseLong("" + d2nd19[e]));
                                            }    
                                        }
                                        if (dt <= 20) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd20(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd20(objEmpSchedule.getD2nd20());
                                            }else{
                                                empSchedule.setD2nd20(d2nd20[e].equals("null") ? 0 : Long.parseLong("" + d2nd20[e]));
                                            }
                                        }
                                        if (dt <= 21) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd21(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd21(objEmpSchedule.getD2nd21());
                                            }else{
                                                empSchedule.setD2nd21(d2nd21[e].equals("null") ? 0 : Long.parseLong("" + d2nd21[e]));
                                            }
                                        }
                                        if (dt <= 22) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd22(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd22(objEmpSchedule.getD2nd22());
                                            }else{
                                                empSchedule.setD2nd22(d2nd22[e].equals("null") ? 0 : Long.parseLong("" + d2nd22[e]));
                                            }
                                        }
                                        if (dt <= 23) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd23(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd23(objEmpSchedule.getD2nd23());
                                            }else{
                                                empSchedule.setD2nd23(d2nd23[e].equals("null") ? 0 : Long.parseLong("" + d2nd23[e]));
                                            }
                                        }
                                        if (dt <= 24) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd24(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd24(objEmpSchedule.getD2nd24());
                                            }else{
                                                empSchedule.setD2nd24(d2nd24[e].equals("null") ? 0 : Long.parseLong("" + d2nd24[e]));
                                            }
                                        }
                                        if (dt <= 25) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd25(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd25(objEmpSchedule.getD2nd25());
                                            }else{
                                                empSchedule.setD2nd25(d2nd25[e].equals("null") ? 0 : Long.parseLong("" + d2nd25[e]));
                                            }
                                        }
                                        if (dt <= 26) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd26(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd26(objEmpSchedule.getD2nd26());
                                            }else{
                                                empSchedule.setD2nd26(d2nd26[e].equals("null") ? 0 : Long.parseLong("" + d2nd26[e]));
                                            }    
                                        }
                                        if (dt <= 27) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd27(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd27(objEmpSchedule.getD2nd27());
                                            }else{
                                                empSchedule.setD2nd27(d2nd27[e].equals("null") ? 0 : Long.parseLong("" + d2nd27[e]));
                                            }     
                                        }
                                        if (dt <= 28) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd28(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd28(objEmpSchedule.getD2nd28());
                                            }else{
                                                empSchedule.setD2nd28(d2nd28[e].equals("null") ? 0 : Long.parseLong("" + d2nd28[e]));
                                            }    
                                        }
                                        if (dt <= 29) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd29(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd29(objEmpSchedule.getD2nd29());
                                            }else{
                                                empSchedule.setD2nd29(d2nd29[e].equals("null") ? 0 : Long.parseLong("" + d2nd29[e]));
                                            }
                                        }
                                        if (dt <= 30) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd30(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd30(objEmpSchedule.getD2nd30());
                                            }else{
                                                empSchedule.setD2nd30(d2nd30[e].equals("null") ? 0 : Long.parseLong("" + d2nd30[e]));
                                            }
                                        }
                                        if (dt <= 31) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd31(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd31(objEmpSchedule.getD2nd31());
                                            }else{
                                                empSchedule.setD2nd31(d2nd31[e].equals("null") ? 0 : Long.parseLong("" + d2nd31[e]));
                                            }
                                        }

                                        EmpSchedule empBeforeUpdate = PstEmpSchedule.fetchExc(empSchedule.getOID());
                                        
                                        PstEmpSchedule.updateExc(empSchedule);
                                                       
                                        PstPresence.importPresenceTriggerByImportEmpScheduleExcel(empBeforeUpdate, empSchedule);

                                   
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

                                                                        
                                        EmpSchedule objEmpSchedulePrev = new EmpSchedule();
                                        objEmpSchedulePrev.setOID(empSchedule.getOID());
                                        objEmpSchedulePrev.setPeriodId(empSchedule.getPeriodId());
                                        objEmpSchedulePrev.setEmployeeId(empSchedule.getEmployeeId());
                                        
                                        PstPresence.importPresenceTriggerByImportEmpScheduleExcel(objEmpSchedulePrev, empSchedule);

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
            Vector listPeriod = PstPeriod.list(0, 1000, "", "START_DATE DESC");
            Vector periodKey = new Vector(1, 1);
            Vector periodValue = new Vector(1, 1);
            for (int p = 0; p < listPeriod.size(); p++) {
                Period period = (Period) listPeriod.get(p);

                // uncoment this for filter period from now up
                //if (period.getEndDate().after(new Date())) {
                    periodKey.add(period.getPeriod());
                    periodValue.add("" + period.getOID());
                //}
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
                
                int maxSize = v.size();
                // get header (title dokumen) yang dalam hal ini WORKING SCHEDULE dst ...
                Vector headers = new Vector(1, 1);
                int num = 0;//ROWS_NUM_HEADER_DEL * numcol;
                int iC=0;
                int iEndHeader=0;
                int loopBreaker=0;
                int maxLoop = 10000;
                
                do{ // cari header
                    if (v.get(iC) != null) {
                        headers.add((String) v.get(iC));
                        num=num+1;
                    }                    
                    iEndHeader++;
                    iC++;
                    loopBreaker++;
                } while((iC<maxSize) && (num!=7) && loopBreaker<maxLoop);
                
                /*
                for (int i = 0; i < num; i++) {
                    if (v.get(i) != null) {
                        headers.add((String) v.get(i));
                    }
                }*/
                // remove header(title dokumen) dari vector v
                
                int x = 0;
                iEndHeader++;
                loopBreaker=0;
                while ((x < iEndHeader ) && (loopBreaker<maxLoop)) {
                    try {
                        v.remove(0);
                        x = x + 1;
                    } catch (Exception e) {
                        System.out.println("exc when delete header : " + e.toString());
                    }
                    loopBreaker++;
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
                numcol= NUM_DATE+2;
                x = v.size() - (ROWS_NUM_FOOTER_DEL * numcol);
                int sizeDef = x - 1;
                loopBreaker=0;
                while ((x < v.size()) && (loopBreaker<maxLoop)){
                    try {
                        v.remove(v.size() - 1);
                    } catch (Exception e) {
                        System.out.println("Exception when delete approval & symbol (footer) : " + e.toString());
                    }
                    loopBreaker++;
                }

                // Tampilkan data dengan list berupa table
                boolean useDayHeader = true;
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

                    // menampilkan  header day
                    // cari awal header day
                    iC=0;
                    maxSize=v.size();
                    int startDay=0;
                    loopBreaker=0;
                    do{  
                        if(v.get(iC)!=null){
                            String strTemp = (String)v.get(iC);
                            if(strTemp.trim().equals("PAYROLL")){
                                startDay=iC+1;
                            }
                        }
                        iC++;    loopBreaker++;
                      } while ((iC < maxSize) && (startDay==0) && (loopBreaker<maxLoop));
                    
                    if (useDayHeader) {
                        // untuk header (day symbol)																
                        drawList.append("\n\t<tr class=\"listheader\">");
                        for (int i = 0; i < NUM_DATE; i++) {
                                String dayCode = "";
                                try{
                                dayCode = (String.valueOf(v.elementAt(startDay+i))).trim();
                                } catch (Exception exc){
                                    
                                }
                                        
                            //if ((i % numcol) > 1) {
                                drawList.append("\n\t\t<td align=\"center\" width=\"" + width + "%\" class=\"tableheader\">" + dayCode+ "</td>");
                            //}
                            //v.remove(startDay);
                        }
                        drawList.append("\n\t</tr>");
                    } else {
                        // untuk header (day symbol)																
                        //for (int i = 0; i < NUM_DATE;  i++) {
                         //   v.remove(startDay);
                        //}
                    }

                    // untuk header (date symbol)	
                    iC=0;
                    maxSize=v.size();
                    int startDate=0;
                    loopBreaker=0;
                    do{  
                        if(v.get(iC)!=null){
                            String strTemp = (String)v.get(iC);
                            if(strTemp.trim().equals("NUMBER")){
                                startDate=iC+1;
                            }
                        }
                        iC++;    
                        loopBreaker++;
                      } while ((iC < maxSize) && (startDate==0) && (loopBreaker<maxLoop));
                    
                    
                    drawList.append("\n\t<tr class=\"listheader\">");
                    for (int h = 0; h < NUM_DATE; h++) {
                        //if ((h % numcol) > 1) {
                                String dateNumber = "";
                                try{
                                dateNumber = (String.valueOf(v.elementAt(startDate+h))).trim();
                                dateNumber=dateNumber.substring(0, dateNumber.indexOf("."));
                                } catch (Exception exc){
                                    
                                }
                            //String dt = String.valueOf(v.elementAt(numcol));
                            drawList.append("\n\t\t<td align=\"center\" width=\"" + width + "%\" class=\"tableheader\">" + dateNumber + "</td>");
                        //}
                       // v.remove(startDate);
                    }
                    drawList.append("\n\t</tr>\n\t<tr>");

                    Hashtable hashSchedule = new Hashtable();
                    hashSchedule.put(" ", "0");

                    Hashtable hashCategory = new Hashtable();
                    hashCategory.put(" ", "0");

                    Vector listSchSymbol = PstScheduleSymbol.listScheduleSymbolAndCategory();
                    if (listSchSymbol != null && listSchSymbol.size() > 0){
                        int intListSchSymbol = listSchSymbol.size();
                        for (int ls = 0; ls < intListSchSymbol; ls++) {
                            try {
                                Vector vectSchldCat = (Vector) listSchSymbol.get(ls);
                                ScheduleSymbol schSymbol = (ScheduleSymbol) vectSchldCat.get(0);
                                ScheduleCategory schCategory = (ScheduleCategory) vectSchldCat.get(1);

                                hashSchedule.put(schSymbol.getSymbol(), String.valueOf(schSymbol.getOID()));
                                hashCategory.put(schSymbol.getSymbol(), String.valueOf(schCategory.getCategoryType()));
                            } catch (Exception exc) {
                                out.println("Error symbol on : ls = " + ls);
                            }
                        }
                    }

                    Hashtable hashPayroll = new Hashtable();
                    Hashtable hashEmpNum = new Hashtable();
                    Hashtable hashEmpName = new Hashtable();
                    String whereC = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0";
                    Vector listEmployee = PstEmployee.list(0, 0, whereC, "");

                    for (int e = 0; e < listEmployee.size(); e++) {
                        Employee employee = (Employee) listEmployee.get(e);
                        hashPayroll.put(employee.getEmployeeNum(), String.valueOf(employee.getOID()));
                        hashEmpNum.put(employee.getEmployeeNum(), employee.getEmployeeNum());
                        hashEmpName.put(employee.getEmployeeNum(), employee.getFullName());
                    }

                    // inisialisalsi proses utk memberi warna row yg error
                    int inIterasi = 33;	 // di set 33 karena maksimum sisa hasil bagi dengan jumlah kolom (33) adalah 32														
                    int idxScheduleErr = 0;

                    String scdSymbol = "";
                    
                    int startEmployeeRow=startDate+NUM_DATE;
                    
                    /*for( int i =0; i<startEmployeeRow;i++ ){
                     v.remove(i); // delete all cell up to before employee name on schedule     
                    }*/
                    
                    int iCell =0;
                    String strNameOnList ="";

                    for (int i = startEmployeeRow; i < v.size(); i++){
                        
                        //variabel yang akan menghandle nama schedule
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

                        boolean scheduleOff = false;
                        
                        if (hashSchedule.get(tmp1stSchd.trim()) == null) {
                            sch = "?";
                        } else {
                            scheduleOff = SessEmpSchedule.getStatusSchedule(Long.parseLong(hashSchedule.get(tmp1stSchd.trim()).toString()));
                            if(scheduleOff == false){
                                sch = tmp1stSchd.trim();
                            }else{
                                sch = "?";
                            }
                        }

                        boolean scheduleOff2nd = false;
                        
                        if (hashSchedule.get(tmp2ndSchd.trim()) == null) {
                            ndsch = "?";
                        } else {
                            scheduleOff2nd = SessEmpSchedule.getStatusSchedule(Long.parseLong(hashSchedule.get(tmp2ndSchd.trim()).toString()));
                            if(scheduleOff2nd == false){
                                ndsch = tmp2ndSchd.trim();
                            }else{
                                ndsch = "?";
                            }
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

                        switch (((iCell + 1) % numcol)){
                            case 1:
                                System.out.println(iCell+" ; "+numcol);
                                strNameOnList =(""+v.elementAt(i)).trim();
                                drawList.append("\n\t\t<td " + clsHtml + ">" + ((iCell / numcol)+1) + "</td><td " + clsHtml + " width=\"" + width + "%\">" + strNameOnList + "</td>");
                                break;

                            case 2:
                                String payroll = "";

                                String dataPayroll = (String) v.elementAt(i);
                                int idx = dataPayroll.indexOf("'");
                                if (idx > -1) {
                                    dataPayroll = dataPayroll.substring(2, dataPayroll.length());
                                }
                                
                                idx = dataPayroll.indexOf(".0"); 
                                if (idx > 0) {
                                    dataPayroll = dataPayroll.substring(0, idx);
                                }                                
                                
                                boolean matchPayroll = false;
                                String strEmpName ="";
                                if (hashPayroll.get("" + dataPayroll) == null) {
                                    payroll = "?";
                                } else {
                                    payroll = "" + dataPayroll.trim();
                                    strEmpName = ((String) (hashEmpName.get(payroll)==null ? "" : hashEmpName.get(payroll))).trim();
                                    if(strEmpName.compareToIgnoreCase(strNameOnList)==0) { matchPayroll=true;}
                                    
                                }
                                String st1 = (matchPayroll?"":"bgcolor=\"#FFFF00\"");
                                String st2 = (matchPayroll?"":"<strong><font color=\"#FF0000\">");
                                String st3 = (matchPayroll?"": (" ("+strEmpName+") </font></strong>"));
                                
                                drawList.append("\n\t\t<td " + clsHtml +" " + st1+ " width=\"" + width + "%\">");
                                drawList.append("<input type=\"hidden\" name=\"employee_id\" value=\"" + hashPayroll.get(dataPayroll) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"employee_num\" value=\"" + hashEmpNum.get(dataPayroll) + "\">");
                                drawList.append(st2+payroll +st3+ "</td>");
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
                        if (((iCell + 1) % numcol) == 0) {
                            drawList.append("\n\t</tr> " + ((i != v.size() - 1) ? "\n\t<tr>" : "\n</table>"));
                        }
                        iCell++;    
                    }

                    drawList.append("<br>" +
                            "\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">");
                    
                    if (iCommand == Command.SAVE && (msgString != null && msgString.length() > 0)){
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
