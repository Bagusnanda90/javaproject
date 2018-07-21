
<%@page import="org.apache.poi.poifs.filesystem.POIFSFileSystem"%>
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
            Period periodX = (Period) session.getValue("PERIOD_UPLOAD_EXCEL");
            int diffDay=31;
           
            int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
           
            if(periodX!=null && periodX.getOID()!=0){
                try{
                    diffDay = PstPresence.DATEDIFF(periodX.getEndDate(),periodX.getStartDate()); 
                }catch(Exception exc){
                    System.out.println("Exception"+exc);
                    diffDay = 31;
                }
                 
            }
            //mencari last date for month
int startDatePeriodX = periodX==null || periodX.getOID()==0 ?  Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD"))) : periodX.getStartDate().getDate() ; 
Date dtCalc = new Date();
Calendar calendar = Calendar.getInstance();
if(periodX!=null && periodX.getStartDate()!=null){
    dtCalc = (Date)periodX.getStartDate().clone();
}

//(Date)srcTransaction.getStartDate().clone();
calendar.setTime(dtCalc);
int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//mencari tanggal terakhir

         int NUM_HEADER = 2;
	int NUM_CELL = 0;//22;
        int ROW_PERIOD =2;
        
        
        int ROW_DEPARTEMENT =1;
        int COL_DEPARTEMENT_MIN = 0;
        int COL_DEPARTEMENT_MAX = 1;
        
        int COL_PERIOD_MIN = 0;
        int COL_PERIOD_MAX = 1;
         int ROW_CREATE_BY =3;
         int COL_CREATE_BY_MIN = 0;
         int COL_CREATE_BY_MAX = 1;
       
         int ROW_SCH =6;
         int COL_SCH_MIN=2;
         int COL_SCH_MAX=diffDay+2;// +2 artinya di tambah Name dan payrol
       
        
         int ROW_SCH_DATE=5;
         int COL_SCHDATE_MIN=2;
         int COL_SCHDATE_MAX=diffDay+2;// +2 artinya di tambah Name dan payrol
    
         int ROW_EMPLOYEE =7;
         int COL_EMPLOYEE_MIN=0;
         int COL_EMPLOYEE_MAX=diffDay+2;// +2 artinya di tambah Name dan payrol
         
         //int ROW_WORKSCHNAME_MIN =0;
        //int ROW_PAYROLL_NUMBER =1; 
        int COL_EMP_SCH=2; 
        // int ROW_EMPNUMB_MIN =1;
        //int ROW_EMPNUMB_MAX =1; 
        int COL_EMPNUMB=1; 

         //   int NUM_DATE = diffDay;      
           // int NUM_HEADER = 3;
           // int NUM_CELL = -1;
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
            String depName = "";
            String creatorHeader = "";
            String periodHeader = "";
             String periodName = "";
             String creatorName = "";
             String fullNameExcel="";

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
                    try{ 
                    // mengambil data dari form dengan querystring dan simpan ke array of data String
                    String[] employeeId = request.getParameterValues("employee_id");
                    //String[] employeeNum = request.getParameterValues("employee_num");
                    
                    // create code pengambilan hidden data dari form yang di create dynamic
                    // sesuaikan dengan awal mulai periode
                    Hashtable vDCode = new Hashtable();
                    //Vector vD2Code = new Vector();
                    Hashtable vCCode = new Hashtable();
                     				
               
                    vDCode.put(""+startDatePeriodX,"D"+startDatePeriodX);       // mulai dari start Date Periode ( contoh tgl. 1 , tgl. 20 sampai tgl. 31
                    //vD2Code.add("D2ND"+startDatePeriodX);
                    vCCode.put(""+startDatePeriodX,"CAT"+startDatePeriodX);
                  int startPeriod = startDatePeriodX;  
               for(int j=0; j<maxDay-1; j++){
			if(startPeriod == maxDay){
				startPeriod =1;
				  vDCode.put(""+startPeriod,"D"+startPeriod);      // mulai dari start Date Periode ( contoh tgl. 1 , tgl. 20 sampai tgl. 31
                                  //vD2Code.add("D2ND"+startPeriod);
                                  vCCode.put(""+startPeriod,"CAT"+startPeriod);
			}
			else{
				startPeriod =startPeriod+1;
				  vDCode.put(""+startPeriod,"D"+startPeriod);      // mulai dari start Date Periode ( contoh tgl. 1 , tgl. 20 sampai tgl. 31
                                  //vD2Code.add("D2ND"+startPeriod);
                                  vCCode.put(""+startPeriod,"CAT"+startPeriod);
			}
			
		}
                  
                  
                    String[] d1 = null;
                    String[] d2 = null;//request.getParameterValues((String) vDCode.get(2-1));
                    String[] d3 = null;//request.getParameterValues((String) vDCode.get(3-1));
                    String[] d4 = null;//request.getParameterValues((String) vDCode.get(4-1));
                    String[] d5 = null;//request.getParameterValues((String) vDCode.get(5-1));
                    String[] d6 = null;//request.getParameterValues((String) vDCode.get(6-1));
                    String[] d7 = null;//request.getParameterValues((String) vDCode.get(7-1));
                    String[] d8 = null;//request.getParameterValues((String) vDCode.get(8-1));
                    String[] d9 = null;//request.getParameterValues((String) vDCode.get(9-1));
                    String[] d10 = null;//request.getParameterValues((String) vDCode.get(10-1));
                    String[] d11 = null;//request.getParameterValues((String) vDCode.get(11-1));
                    String[] d12 = null;//request.getParameterValues((String) vDCode.get(12-1));
                    String[] d13 = null;//request.getParameterValues((String) vDCode.get(13-1));
                    String[] d14 = null;//request.getParameterValues((String) vDCode.get(14-1));
                    String[] d15 = null;//request.getParameterValues((String) vDCode.get(15-1));
                    String[] d16 = null;//request.getParameterValues((String) vDCode.get(16-1));
                    String[] d17 = null;//request.getParameterValues((String) vDCode.get(17-1));
                    String[] d18 = null;//request.getParameterValues((String) vDCode.get(18-1));
                    String[] d19 = null;//request.getParameterValues((String) vDCode.get(19-1));
                    String[] d20 = null;//request.getParameterValues((String) vDCode.get(20-1));
                    String[] d21 = null;//request.getParameterValues((String) vDCode.get(21-1));
                    String[] d22 = null;//request.getParameterValues((String) vDCode.get(22-1));
                    String[] d23 = null;//request.getParameterValues((String) vDCode.get(23-1));
                    String[] d24 = null;//request.getParameterValues((String) vDCode.get(24-1));
                    String[] d25 = null;//request.getParameterValues((String) vDCode.get(25-1));
                    String[] d26 = null;//request.getParameterValues((String) vDCode.get(26-1));
                    String[] d27 = null;//request.getParameterValues((String) vDCode.get(27-1));
                    String[] d28 = null;//request.getParameterValues((String) vDCode.get(28-1));
                    String[] d29 = null; 
                    String[] d30 = null;
                    String[] d31 = null;
                    
                    String[] cat1 = null;
                    String[] cat2 = null;//request.getParameterValues((String) vDCode.get(2-1));
                    String[] cat3 = null;//request.getParameterValues((String) vDCode.get(3-1));
                    String[] cat4 = null;//request.getParameterValues((String) vDCode.get(4-1));
                    String[] cat5 = null;//request.getParameterValues((String) vDCode.get(5-1));
                    String[] cat6 = null;//request.getParameterValues((String) vDCode.get(6-1));
                    String[] cat7 = null;//request.getParameterValues((String) vDCode.get(7-1));
                    String[] cat8 = null;//request.getParameterValues((String) vDCode.get(8-1));
                    String[] cat9 = null;//request.getParameterValues((String) vDCode.get(9-1));
                    String[] cat10 = null;//request.getParameterValues((String) vDCode.get(10-1));
                    String[] cat11 = null;//request.getParameterValues((String) vDCode.get(11-1));
                    String[] cat12 = null;//request.getParameterValues((String) vDCode.get(12-1));
                    String[] cat13 = null;//request.getParameterValues((String) vDCode.get(13-1));
                    String[] cat14 = null;//request.getParameterValues((String) vDCode.get(14-1));
                    String[] cat15 = null;//request.getParameterValues((String) vDCode.get(15-1));
                    String[] cat16 = null;//request.getParameterValues((String) vDCode.get(16-1));
                    String[] cat17 = null;//request.getParameterValues((String) vDCode.get(17-1));
                    String[] cat18 = null;//request.getParameterValues((String) vDCode.get(18-1));
                    String[] cat19 = null;//request.getParameterValues((String) vDCode.get(19-1));
                    String[] cat20 = null;//request.getParameterValues((String) vDCode.get(20-1));
                    String[] cat21 = null;//request.getParameterValues((String) vDCode.get(21-1));
                    String[] cat22 = null;//request.getParameterValues((String) vDCode.get(22-1));
                    String[] cat23 = null;//request.getParameterValues((String) vDCode.get(23-1));
                    String[] cat24 = null;//request.getParameterValues((String) vDCode.get(24-1));
                    String[] cat25 = null;//request.getParameterValues((String) vDCode.get(25-1));
                    String[] cat26 = null;//request.getParameterValues((String) vDCode.get(26-1));
                    String[] cat27 = null;//request.getParameterValues((String) vDCode.get(27-1));
                    String[] cat28 = null;//request.getParameterValues((String) vDCode.get(28-1));
                    String[] cat29 = null; 
                    String[] cat30 = null;
                    String[] cat31 = null; 
                    int start =startDatePeriodX-1;
                    int startPeriodDt = startDatePeriodX-1; 
                 for(int j=0; j<maxDay; j++){
			if(startPeriodDt == maxDay){
				startPeriodDt =1;
                                //start = startPeriodDt;
			}
			else{
				startPeriodDt =startPeriodDt+1;
			}
                        switch (startPeriodDt){  
                              case 1:
                                d1 = request.getParameterValues((String) vDCode.get(""+1)); 
                                cat1 = request.getParameterValues((String) vCCode.get(""+1));
                                break;

                            case 2:
                                d2 = request.getParameterValues((String) vDCode.get(""+2));
                                cat2 = request.getParameterValues((String) vCCode.get(""+2));
                                break;

                            case 3:
                                d3 = request.getParameterValues((String) vDCode.get(""+3));
                                cat3 = request.getParameterValues((String) vCCode.get(""+3));
                                break;

                            case 4:
                                d4 = request.getParameterValues((String) vDCode.get(""+4));
                                cat4 = request.getParameterValues((String) vCCode.get(""+4));
                                break;

                            case 5:
                                d5 = request.getParameterValues((String) vDCode.get(""+5));
                                cat5 = request.getParameterValues((String) vCCode.get(""+5));
                                break;

                            case 6:
                                d6 = request.getParameterValues((String) vDCode.get(""+6));
                                cat6 = request.getParameterValues((String) vCCode.get(""+6));
                                break;

                            case 7:
                                d7 = request.getParameterValues((String) vDCode.get(""+7));
                                cat7 = request.getParameterValues((String) vCCode.get(""+7));
                                break;

                            case 8:
                                d8 = request.getParameterValues((String) vDCode.get(""+8));
                                cat8 = request.getParameterValues((String) vCCode.get(""+8));
                                break;

                            case 9:
                                d9 = request.getParameterValues((String) vDCode.get(""+9));
                                cat9 = request.getParameterValues((String) vCCode.get(""+9));
                                break;

                            case 10:
                                d10 = request.getParameterValues((String) vDCode.get(""+10));
                                cat10 = request.getParameterValues((String) vCCode.get(""+10));
                                break;

                            case 11:
                                d11 = request.getParameterValues((String) vDCode.get(""+11));
                                cat11 = request.getParameterValues((String) vCCode.get(""+11));
                                break;

                            case 12:
                                d12 = request.getParameterValues((String) vDCode.get(""+12));
                                cat12 = request.getParameterValues((String) vCCode.get(""+12));
                                break;

                            case 13:
                                d13 = request.getParameterValues((String) vDCode.get(""+13));
                                cat13 = request.getParameterValues((String) vCCode.get(""+13));
                                break;

                            case 14:
                                d14 = request.getParameterValues((String) vDCode.get(""+14));
                                cat14 = request.getParameterValues((String) vCCode.get(""+14));
                                break;

                            case 15:
                                d15 = request.getParameterValues((String) vDCode.get(""+15));
                                cat15 = request.getParameterValues((String) vCCode.get(""+15));
                                break;

                            case 16:
                                d16 = request.getParameterValues((String) vDCode.get(""+16));
                                cat16 = request.getParameterValues((String) vCCode.get(""+16));
                                break;

                            case 17:
                                d17 = request.getParameterValues((String) vDCode.get(""+17));
                                cat17 = request.getParameterValues((String) vCCode.get(""+17));
                                break;

                            case 18:
                                d18 = request.getParameterValues((String) vDCode.get(""+18));
                                cat18 = request.getParameterValues((String) vCCode.get(""+18));
                                break;

                            case 19:
                                d19 = request.getParameterValues((String) vDCode.get(""+19));
                                cat19 = request.getParameterValues((String) vCCode.get(""+19));
                                break;

                            case 20:
                                d20 = request.getParameterValues((String) vDCode.get(""+20));
                                cat20 = request.getParameterValues((String) vCCode.get(""+20));
                                break;

                            case 21:
                                d21 = request.getParameterValues((String) vDCode.get(""+21));
                                cat21 = request.getParameterValues((String) vCCode.get(""+21));
                                break;

                            case 22:
                                d22 = request.getParameterValues((String) vDCode.get(""+22));
                                cat22 = request.getParameterValues((String) vCCode.get(""+22));
                                break;

                            case 23:
                                d23 = request.getParameterValues((String) vDCode.get(""+23));
                                cat23 = request.getParameterValues((String) vCCode.get(""+23));
                                break;

                            case 24:
                                d24 = request.getParameterValues((String) vDCode.get(""+24));
                                cat24 = request.getParameterValues((String) vCCode.get(""+24));
                                break;

                            case 25:
                                d25 = request.getParameterValues((String) vDCode.get(""+25));
                                cat25 = request.getParameterValues((String) vCCode.get(""+25));
                                break;

                            case 26:
                                d26 = request.getParameterValues((String) vDCode.get(""+26));
                                cat26 = request.getParameterValues((String) vCCode.get(""+26));
                                break;

                            case 27:
                                d27 = request.getParameterValues((String) vDCode.get(""+27));
                                cat27 = request.getParameterValues((String) vCCode.get(""+27));
                                break;

                            case 28:
                                d28 = request.getParameterValues((String) vDCode.get(""+28));
                                cat28 = request.getParameterValues((String) vCCode.get(""+28));
                                break;

                            case 29:
                             if(29  <= maxDay && 29 <= vDCode.size()){
                                d29 = request.getParameterValues((String) vDCode.get(""+29));
                                cat29 = request.getParameterValues((String) vCCode.get(""+29));
                              }
                                break;

                            case 30:
                             if(30  <= maxDay && 30 <= vDCode.size()){
                                d30 = request.getParameterValues((String) vDCode.get(""+30));
                                cat30 = request.getParameterValues((String) vCCode.get(""+30));
                             }
                                break;

                            case 31:
                              if(31  <= maxDay && 31 <= vDCode.size()){ 
                                d31 = request.getParameterValues((String) vDCode.get(""+31));
                                cat31 = request.getParameterValues((String) vCCode.get(""+31));
                              }
                                break;

                            default:
                                break;
                          }
			
		}   
                 
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
                  if(29  <= maxDay && 29 <= vDCode.size() && d29!=null){
                     if (d29[e].equals("null")) {
                            d29[e] = "0";
                        }
                  }
                  if(30  <= maxDay && 30 <= vDCode.size() && d30!=null){
                      if (d30[e].equals("null")) {
                            d30[e] = "0";
                        }
                    }
                 if(31 <= maxDay && 31 <= vDCode.size() && d31!=null){
                     if (d31[e].equals("null")) {
                            d31[e] = "0";
                        }
                    }
                  if(29  <= maxDay && 29 <= vDCode.size()){
                       if (cat29[e].equals("null")) {
                            cat29[e] = "0";
                        }
                  }
                  if(30  <= maxDay && 30 <= vDCode.size()){
                       if (cat30[e].equals("null")) {
                            cat30[e] = "0";
                        }
                    }
                 if(31 <= maxDay && 31 <= vDCode.size()){
                       if (cat31[e].equals("null")) {
                            cat31[e] = "0";
                        }
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
                        
                        
                  if(29  <= maxDay && 29 <= vCCode.size()){
                      vectSchldCat.add(String.valueOf(cat29[e]));
                  }
                  if(30  <= maxDay && 30 <= vCCode.size()){
                       vectSchldCat.add(String.valueOf(cat30[e]));
                    }
                 if(31 <= maxDay && 31 <= vCCode.size()){
                      vectSchldCat.add(String.valueOf(cat31[e]));
                    } 

                        long empId = (employeeId[e].equals("null")) ? 0 : Long.parseLong("" + employeeId[e]);
                        
                        boolean checkLeaveScheduleOk = true;
                        	
                        noErrWithLeaveStock = noErrWithLeaveStock && checkLeaveScheduleOk;
                        
                        //if (checkLeaveScheduleOk || true){
                            checkLeaveScheduleOk = true;
                            
                            if (checkLeaveScheduleOk){
                                // jika schedule utk employee ada dalam db, maka lakukan proses "update"
                            if (vcheck.size() > 0) {
                                //update
                                Employee empCheck = new Employee();//
                                try {
                                    //add by priska 20151112
                                    EmpSchedule empSchedule = (EmpSchedule) vcheck.get(0);
                                    try {
                                        empCheck = PstEmployee.fetchExc(empSchedule.getEmployeeId());//
                                    }catch(Exception E){
                                        System.out.println("[exception] "+E.toString());
                                    }
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
                                        
                                        //cek batasan hari ke masa lalu untuk bisa di edit atau tidak (melebihi batasan hari di position)
                                        EmpSchedule empScheduleBeforeUpdate = new EmpSchedule();
                                        try {
                                            empScheduleBeforeUpdate = PstEmpSchedule.fetchExc(empSchedule.getOID());
                                        } catch (Exception ex) {}
                                            Employee employee = new Employee();
                                        try {
                                            employee = PstEmployee.fetchExc(empScheduleBeforeUpdate.getEmployeeId());
                                        } catch (Exception ex) {}
                                            Position position = new Position();
                                        try {
                                            position = positionOfLoginUser;
                                        } catch (Exception ex) {}

                                        double Beforedays =  position.getDeadlineScheduleBefore()/24;
                                        Date deadDay = new Date();//Date today = new Date();
                                            //mencari batasan harinya (sebelumnya)
                                        deadDay.setHours(deadDay.getHours() - position.getDeadlineScheduleBefore());
                                        Period periodDead = PstPeriod.getPeriodBySelectedDate(deadDay);
                                       // EmpSchedule empScheduleBeforeUpdate = new EmpSchedule(); 
                                       // empScheduleBeforeUpdate = PstEmpSchedule.fetchExc(empSchedule.getOID());
                                        
                                        //dibuat agar beberapa hari sebelumnya tidak bisa dirubah
                            if ((periodDead != null) && (periodDead.getOID() == empScheduleBeforeUpdate.getPeriodId())){
                               int startDate = periodDead.getStartDate().getDate();
                               int endDate = periodDead.getEndDate().getDate();

                               Date startDateClone = (Date) periodDead.getStartDate().clone() ;
                               int nilai = 0;
                               do{
                                  // startDateClone.setDate(startDateClone.getDate()+1);
                                   if (startDateClone.getDate() == deadDay.getDate() || nilai == 1){
                                       //mencari harinya keberapa
                                              if (startDateClone.getDate() == 1){
                                                 empScheduleBeforeUpdate.setD1((d1==null?0:(d1[e].equals("null") ? 0 : Long.parseLong("" + d1[e]))));
                                              }else if (startDateClone.getDate() == 2){
                                                 empScheduleBeforeUpdate.setD2((d2==null?0:(d2[e].equals("null") ? 0 : Long.parseLong("" + d2[e]))));
                                              }else if (startDateClone.getDate() == 3){
                                                 empScheduleBeforeUpdate.setD3((d3==null?0:(d3[e].equals("null") ? 0 : Long.parseLong("" + d3[e]))));
                                              }else if (startDateClone.getDate() == 4){
                                                 empScheduleBeforeUpdate.setD4((d4==null?0:(d4[e].equals("null") ? 0 : Long.parseLong("" + d4[e]))));
                                              }else if (startDateClone.getDate() == 5){
                                                 empScheduleBeforeUpdate.setD5((d5==null?0:(d5[e].equals("null") ? 0 : Long.parseLong("" + d5[e]))));
                                              }else if (startDateClone.getDate() == 6){
                                                 empScheduleBeforeUpdate.setD6((d6==null?0:(d6[e].equals("null") ? 0 : Long.parseLong("" + d6[e]))));
                                              }else if (startDateClone.getDate() == 7){
                                                 empScheduleBeforeUpdate.setD7((d7==null?0:(d7[e].equals("null") ? 0 : Long.parseLong("" + d7[e]))));
                                              }else if (startDateClone.getDate() == 8){
                                                 empScheduleBeforeUpdate.setD8((d8==null?0:(d8[e].equals("null") ? 0 : Long.parseLong("" + d8[e]))));
                                              }else if (startDateClone.getDate() == 9){
                                                 empScheduleBeforeUpdate.setD9((d9==null?0:(d9[e].equals("null") ? 0 : Long.parseLong("" + d9[e]))));
                                              }else if (startDateClone.getDate() == 10){
                                                 empScheduleBeforeUpdate.setD10((d10==null?0:(d10[e].equals("null") ? 0 : Long.parseLong("" + d10[e]))));
                                              }else if (startDateClone.getDate() == 11){
                                                 empScheduleBeforeUpdate.setD11((d11==null?0:(d11[e].equals("null") ? 0 : Long.parseLong("" + d11[e]))));
                                              }else if (startDateClone.getDate() == 12){
                                                 empScheduleBeforeUpdate.setD12((d12==null?0:(d12[e].equals("null") ? 0 : Long.parseLong("" + d12[e]))));
                                              }else if (startDateClone.getDate() == 13){
                                                 empScheduleBeforeUpdate.setD13((d13==null?0:(d13[e].equals("null") ? 0 : Long.parseLong("" + d13[e]))));
                                              }else if (startDateClone.getDate() == 14){
                                                 empScheduleBeforeUpdate.setD14((d14==null?0:(d14[e].equals("null") ? 0 : Long.parseLong("" + d14[e]))));
                                              }else if (startDateClone.getDate() == 15){
                                                 empScheduleBeforeUpdate.setD15((d15==null?0:(d15[e].equals("null") ? 0 : Long.parseLong("" + d15[e]))));
                                              }else if (startDateClone.getDate() == 16){
                                                 empScheduleBeforeUpdate.setD16((d16==null?0:(d16[e].equals("null") ? 0 : Long.parseLong("" + d16[e]))));
                                              }else if (startDateClone.getDate() == 17){
                                                 empScheduleBeforeUpdate.setD17((d17==null?0:(d17[e].equals("null") ? 0 : Long.parseLong("" + d17[e]))));
                                              }else if (startDateClone.getDate() == 18){
                                                 empScheduleBeforeUpdate.setD18((d18==null?0:(d18[e].equals("null") ? 0 : Long.parseLong("" + d18[e]))));
                                              }else if (startDateClone.getDate() == 19){
                                                 empScheduleBeforeUpdate.setD19((d19==null?0:(d19[e].equals("null") ? 0 : Long.parseLong("" + d19[e]))));
                                              }else if (startDateClone.getDate() == 20){
                                                 empScheduleBeforeUpdate.setD20((d20==null?0:(d20[e].equals("null") ? 0 : Long.parseLong("" + d20[e]))));
                                              }else if (startDateClone.getDate() == 21){
                                                 empScheduleBeforeUpdate.setD21((d21==null?0:(d21[e].equals("null") ? 0 : Long.parseLong("" + d21[e]))));
                                              }else if (startDateClone.getDate() == 22){
                                                 empScheduleBeforeUpdate.setD22((d22==null?0:(d22[e].equals("null") ? 0 : Long.parseLong("" + d22[e]))));
                                              }else if (startDateClone.getDate() == 23){
                                                 empScheduleBeforeUpdate.setD23((d23==null?0:(d23[e].equals("null") ? 0 : Long.parseLong("" + d23[e]))));
                                              }else if (startDateClone.getDate() == 24){
                                                 empScheduleBeforeUpdate.setD24((d24==null?0:(d24[e].equals("null") ? 0 : Long.parseLong("" + d24[e]))));
                                              }else if (startDateClone.getDate() == 25){
                                                 empScheduleBeforeUpdate.setD25((d25==null?0:(d25[e].equals("null") ? 0 : Long.parseLong("" + d25[e]))));
                                              }else if (startDateClone.getDate() == 26){
                                                 empScheduleBeforeUpdate.setD26((d26==null?0:(d26[e].equals("null") ? 0 : Long.parseLong("" + d26[e]))));
                                              }else if (startDateClone.getDate() == 27){
                                                 empScheduleBeforeUpdate.setD27((d27==null?0:(d27[e].equals("null") ? 0 : Long.parseLong("" + d27[e]))));
                                              }else if (startDateClone.getDate() == 28){
                                                 empScheduleBeforeUpdate.setD28((d28==null?0:(d28[e].equals("null") ? 0 : Long.parseLong("" + d28[e]))));
                                              }else if (startDateClone.getDate() == 29){
                                                 empScheduleBeforeUpdate.setD29((d29==null?0:(d29[e].equals("null") ? 0 : Long.parseLong("" + d29[e]))));
                                              }else if (startDateClone.getDate() == 30){
                                                 empScheduleBeforeUpdate.setD30((d30==null?0:(d30[e].equals("null") ? 0 : Long.parseLong("" + d30[e]))));
                                              }else if (startDateClone.getDate() == 31){
                                                 empScheduleBeforeUpdate.setD31((d31==null?0:(d31[e].equals("null") ? 0 : Long.parseLong("" + d31[e]))));
                                              }
                                        
                                       nilai = 1;
                                   }
                                   startDateClone.setDate(startDateClone.getDate()+1);
                               }while(startDateClone.getDate() != (endDate+1));



                            } else {
                             //mencari berada sebelum periode ini atau setelahnya
                             //karena jika setelahnya maka dia masih bisa diupdate dan jika sebelumnya maka tidak bisa diupdate
                                Period periodeEmpScheduleBeforeUpdate = PstPeriod.fetchExc(empScheduleBeforeUpdate.getPeriodId());
                                if (periodeEmpScheduleBeforeUpdate.getStartDate().after(deadDay)){
                                    
                                         
                                              empScheduleBeforeUpdate.setD1((d1==null?0:(d1[e].equals("null") ? 0 : Long.parseLong("" + d1[e]))));
                                              empScheduleBeforeUpdate.setD2((d2==null?0:(d2[e].equals("null") ? 0 : Long.parseLong("" + d2[e]))));
                                              empScheduleBeforeUpdate.setD3((d3==null?0:(d3[e].equals("null") ? 0 : Long.parseLong("" + d3[e]))));
                                              empScheduleBeforeUpdate.setD4((d4==null?0:(d4[e].equals("null") ? 0 : Long.parseLong("" + d4[e]))));
                                              empScheduleBeforeUpdate.setD5((d5==null?0:(d5[e].equals("null") ? 0 : Long.parseLong("" + d5[e]))));
                                              empScheduleBeforeUpdate.setD6((d6==null?0:(d6[e].equals("null") ? 0 : Long.parseLong("" + d6[e]))));
                                              empScheduleBeforeUpdate.setD7((d7==null?0:(d7[e].equals("null") ? 0 : Long.parseLong("" + d7[e]))));
                                              empScheduleBeforeUpdate.setD8((d8==null?0:(d8[e].equals("null") ? 0 : Long.parseLong("" + d8[e]))));
                                              empScheduleBeforeUpdate.setD9((d9==null?0:(d9[e].equals("null") ? 0 : Long.parseLong("" + d9[e]))));
                                              empScheduleBeforeUpdate.setD10((d10==null?0:(d10[e].equals("null") ? 0 : Long.parseLong("" + d10[e]))));
                                              empScheduleBeforeUpdate.setD11((d11==null?0:(d11[e].equals("null") ? 0 : Long.parseLong("" + d11[e]))));
                                              empScheduleBeforeUpdate.setD12((d12==null?0:(d12[e].equals("null") ? 0 : Long.parseLong("" + d12[e]))));
                                              empScheduleBeforeUpdate.setD13((d13==null?0:(d13[e].equals("null") ? 0 : Long.parseLong("" + d13[e]))));
                                              empScheduleBeforeUpdate.setD14((d14==null?0:(d14[e].equals("null") ? 0 : Long.parseLong("" + d14[e]))));
                                              empScheduleBeforeUpdate.setD15((d15==null?0:(d15[e].equals("null") ? 0 : Long.parseLong("" + d15[e]))));
                                              empScheduleBeforeUpdate.setD16((d16==null?0:(d16[e].equals("null") ? 0 : Long.parseLong("" + d16[e]))));
                                              empScheduleBeforeUpdate.setD17((d17==null?0:(d17[e].equals("null") ? 0 : Long.parseLong("" + d17[e]))));
                                              empScheduleBeforeUpdate.setD18((d18==null?0:(d18[e].equals("null") ? 0 : Long.parseLong("" + d18[e]))));
                                              empScheduleBeforeUpdate.setD19((d19==null?0:(d19[e].equals("null") ? 0 : Long.parseLong("" + d19[e]))));
                                              empScheduleBeforeUpdate.setD20((d20==null?0:(d20[e].equals("null") ? 0 : Long.parseLong("" + d20[e]))));
                                              empScheduleBeforeUpdate.setD21((d21==null?0:(d21[e].equals("null") ? 0 : Long.parseLong("" + d21[e]))));
                                              empScheduleBeforeUpdate.setD22((d22==null?0:(d22[e].equals("null") ? 0 : Long.parseLong("" + d22[e]))));
                                              empScheduleBeforeUpdate.setD23((d23==null?0:(d23[e].equals("null") ? 0 : Long.parseLong("" + d23[e]))));
                                              empScheduleBeforeUpdate.setD24((d24==null?0:(d24[e].equals("null") ? 0 : Long.parseLong("" + d24[e]))));
                                              empScheduleBeforeUpdate.setD25((d25==null?0:(d25[e].equals("null") ? 0 : Long.parseLong("" + d25[e]))));
                                              empScheduleBeforeUpdate.setD26((d26==null?0:(d26[e].equals("null") ? 0 : Long.parseLong("" + d26[e]))));
                                              empScheduleBeforeUpdate.setD27((d27==null?0:(d27[e].equals("null") ? 0 : Long.parseLong("" + d27[e]))));
                                              empScheduleBeforeUpdate.setD28((d28==null?0:(d28[e].equals("null") ? 0 : Long.parseLong("" + d28[e]))));
                                              empScheduleBeforeUpdate.setD29((d29==null?0:(d29[e].equals("null") ? 0 : Long.parseLong("" + d29[e]))));
                                              empScheduleBeforeUpdate.setD30((d30==null?0:(d30[e].equals("null") ? 0 : Long.parseLong("" + d30[e]))));
                                              empScheduleBeforeUpdate.setD31((d31==null?0:(d31[e].equals("null") ? 0 : Long.parseLong("" + d31[e]))));
                                              
                                    
                                }
                                
                            }  
                                        
                                        
                                        
                                        
                                        
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
                                                empSchedule.setD1(empScheduleBeforeUpdate.getD1());                                                                                        
                                            }
                                            
                                            //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD1());
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD1(), objEmpSchedule.getPeriodId(), oidSymbolNew, 1 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
                                            }
                                            
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus1(PstEmpSchedule.STATUS_PRESENCE_OK);
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
                                               // empSchedule.setD2(d2==null?0: (d2[e].equals("null") ? 0 : Long.parseLong("" + d2[e])));
                                                 empSchedule.setD2(empScheduleBeforeUpdate.getD2());
                                                
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus2(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                            //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD2()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD2(), objEmpSchedule.getPeriodId(), oidSymbolNew, 2 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                //empSchedule.setD3(d3==null?0: (d3[e].equals("null") ? 0 : Long.parseLong("" + d3[e])));
                                                empSchedule.setD3(empScheduleBeforeUpdate.getD3());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus3(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                            //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD3()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD3(), objEmpSchedule.getPeriodId(), oidSymbolNew, 3 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD4(empScheduleBeforeUpdate.getD4());
                                                //empSchedule.setD4(d4==null?0: (d4[e].equals("null") ? 0 : Long.parseLong("" + d4[e])));
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus4(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                            //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD4()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD4(), objEmpSchedule.getPeriodId(), oidSymbolNew, 4 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD5(empScheduleBeforeUpdate.getD5());
                                                //empSchedule.setD5(d5==null?0: (d5[e].equals("null") ? 0 : Long.parseLong("" + d5[e])));
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus5(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                            //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD5()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD5(), objEmpSchedule.getPeriodId(), oidSymbolNew, 5 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD6(empScheduleBeforeUpdate.getD6());
                                                //empSchedule.setD6(d6==null?0: (d6[e].equals("null") ? 0 : Long.parseLong("" + d6[e])));
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus6(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                            //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD6()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD6(), objEmpSchedule.getPeriodId(), oidSymbolNew, 6 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD7(empScheduleBeforeUpdate.getD7());
                                                //empSchedule.setD7(d7==null?0: (d7[e].equals("null") ? 0 : Long.parseLong("" + d7[e])));
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus7(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                            //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD7()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD7(), objEmpSchedule.getPeriodId(), oidSymbolNew, 7 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD8(empScheduleBeforeUpdate.getD8());
                                                //empSchedule.setD8(d8==null?0: (d8[e].equals("null") ? 0 : Long.parseLong("" + d8[e])));
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus8(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                            //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD8()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD8(), objEmpSchedule.getPeriodId(), oidSymbolNew, 8 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD9(empScheduleBeforeUpdate.getD9());
                                            //  empSchedule.setD9(d9==null?0: (d9[e].equals("null") ? 0 : Long.parseLong("" + d9[e])));
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus9(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                             //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD9()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD9(), objEmpSchedule.getPeriodId(), oidSymbolNew, 9 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD10(empScheduleBeforeUpdate.getD10());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus10(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                             //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD10()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD10(), objEmpSchedule.getPeriodId(), oidSymbolNew, 10 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD11(empScheduleBeforeUpdate.getD11());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus11(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                             //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD11()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD11(), objEmpSchedule.getPeriodId(), oidSymbolNew, 11 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD12(empScheduleBeforeUpdate.getD12());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus12(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                             //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD12()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD12(), objEmpSchedule.getPeriodId(), oidSymbolNew, 12 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD13(empScheduleBeforeUpdate.getD13());
                                            }   
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus13(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                             //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD13()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD13(), objEmpSchedule.getPeriodId(), oidSymbolNew, 13 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                              empSchedule.setD14(empScheduleBeforeUpdate.getD14());
                                            }  
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus14(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                             //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD14()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD14(), objEmpSchedule.getPeriodId(), oidSymbolNew, 14 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD15(empScheduleBeforeUpdate.getD15());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus15(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                             //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD15()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD15(), objEmpSchedule.getPeriodId(), oidSymbolNew, 15 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD16(empScheduleBeforeUpdate.getD16());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus16(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                             //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD16()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD16(), objEmpSchedule.getPeriodId(), oidSymbolNew, 16 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD17(empScheduleBeforeUpdate.getD17()); 
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus17(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }  
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD17()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD17(), objEmpSchedule.getPeriodId(), oidSymbolNew, 17 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD18(empScheduleBeforeUpdate.getD18());
                                            }    
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus18(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD18()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD18(), objEmpSchedule.getPeriodId(), oidSymbolNew, 18 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD19(empScheduleBeforeUpdate.getD19());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus19(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD19()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD19(), objEmpSchedule.getPeriodId(), oidSymbolNew, 19 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD20(empScheduleBeforeUpdate.getD20());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus20(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD20()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD20(), objEmpSchedule.getPeriodId(), oidSymbolNew, 20 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD21(empScheduleBeforeUpdate.getD21());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus21(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD21()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD21(), objEmpSchedule.getPeriodId(), oidSymbolNew, 21 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD22(empScheduleBeforeUpdate.getD22());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus22(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD22()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD22(), objEmpSchedule.getPeriodId(), oidSymbolNew, 22 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD23(empScheduleBeforeUpdate.getD23());
                                            }    
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus23(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD23()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD23(), objEmpSchedule.getPeriodId(), oidSymbolNew, 23 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD24(empScheduleBeforeUpdate.getD24());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus24(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD24()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD24(), objEmpSchedule.getPeriodId(), oidSymbolNew, 24 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD25(empScheduleBeforeUpdate.getD25());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus25(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD25()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD25(), objEmpSchedule.getPeriodId(), oidSymbolNew, 25);
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD26(empScheduleBeforeUpdate.getD26());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus26(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD26()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD26(), objEmpSchedule.getPeriodId(), oidSymbolNew, 26 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD27(empScheduleBeforeUpdate.getD27());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus27(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD27()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD27(), objEmpSchedule.getPeriodId(), oidSymbolNew, 27 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD28(empScheduleBeforeUpdate.getD28());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus28(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD28()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD28(), objEmpSchedule.getPeriodId(), oidSymbolNew, 28 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD29(empScheduleBeforeUpdate.getD29());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus29(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD29()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD29(), objEmpSchedule.getPeriodId(), oidSymbolNew, 29 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                empSchedule.setD30(empScheduleBeforeUpdate.getD30());
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus30(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                            
                                              //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD30()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD30(), objEmpSchedule.getPeriodId(), oidSymbolNew, 30 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                               if(d31!=null){
                                                empSchedule.setD31(empScheduleBeforeUpdate.getD31());
                                               }else{
                                                   empSchedule.setD31(0);
                                               }
                                                
                                            }
                                            // Jika employee presence check parameter == PRESENCE_CHECK_ALWAYS_OK | Hendra McHen | 2015-01-15
                                            if (empCheck.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                empSchedule.setStatus31(PstEmpSchedule.STATUS_PRESENCE_OK);
                                            }
                                               //cek dp by priska 20150930
                                            try {
                                            long oidSymbolNew = (Long) (empScheduleBeforeUpdate.getD31()) ;
                                            String cekDp = PstDpStockManagement.checkGetDP(objEmpSchedule.getOID(), objEmpSchedule.getEmployeeId(), objEmpSchedule.getD31(), objEmpSchedule.getPeriodId(), oidSymbolNew, 31 );
                                            } catch (Exception ex){
                                               System.out.printf("Gagal update dp"); 
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
                                                //empSchedule.setD2nd1(d2nd1[e].equals("null") ? 0 : Long.parseLong("" + d2nd1[e]));
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
                                                //empSchedule.setD2nd2(d2nd2[e].equals("null") ? 0 : Long.parseLong("" + d2nd2[e]));
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
                                                //empSchedule.setD2nd3(d2nd3[e].equals("null") ? 0 : Long.parseLong("" + d2nd3[e]));
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
                                                //empSchedule.setD2nd4(d2nd4[e].equals("null") ? 0 : Long.parseLong("" + d2nd4[e]));
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
                                                //empSchedule.setD2nd5(d2nd5[e].equals("null") ? 0 : Long.parseLong("" + d2nd5[e]));
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
                                                //empSchedule.setD2nd6(d2nd6[e].equals("null") ? 0 : Long.parseLong("" + d2nd6[e]));
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
                                                //empSchedule.setD2nd7(d2nd7[e].equals("null") ? 0 : Long.parseLong("" + d2nd7[e]));
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
                                                //empSchedule.setD2nd8(d2nd8[e].equals("null") ? 0 : Long.parseLong("" + d2nd8[e]));
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
                                                //empSchedule.setD2nd9(d2nd9[e].equals("null") ? 0 : Long.parseLong("" + d2nd9[e]));
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
                                                //empSchedule.setD2nd10(d2nd10[e].equals("null") ? 0 : Long.parseLong("" + d2nd10[e]));
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
                                                //empSchedule.setD2nd11(d2nd11[e].equals("null") ? 0 : Long.parseLong("" + d2nd11[e]));
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
                                                //empSchedule.setD2nd12(d2nd12[e].equals("null") ? 0 : Long.parseLong("" + d2nd12[e]));
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
                                                //empSchedule.setD2nd13(d2nd13[e].equals("null") ? 0 : Long.parseLong("" + d2nd13[e]));
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
                                                //empSchedule.setD2nd14(d2nd14[e].equals("null") ? 0 : Long.parseLong("" + d2nd14[e]));
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
                                                //empSchedule.setD2nd15(d2nd15[e].equals("null") ? 0 : Long.parseLong("" + d2nd15[e]));
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
                                                //empSchedule.setD2nd16(d2nd16[e].equals("null") ? 0 : Long.parseLong("" + d2nd16[e]));
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
                                               // empSchedule.setD2nd17(d2nd17[e].equals("null") ? 0 : Long.parseLong("" + d2nd17[e]));
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
                                                //empSchedule.setD2nd18(d2nd18[e].equals("null") ? 0 : Long.parseLong("" + d2nd18[e]));
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
                                                //empSchedule.setD2nd19(d2nd19[e].equals("null") ? 0 : Long.parseLong("" + d2nd19[e]));
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
                                                //empSchedule.setD2nd20(d2nd20[e].equals("null") ? 0 : Long.parseLong("" + d2nd20[e]));
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
                                                //empSchedule.setD2nd21(d2nd21[e].equals("null") ? 0 : Long.parseLong("" + d2nd21[e]));
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
                                                //empSchedule.setD2nd22(d2nd22[e].equals("null") ? 0 : Long.parseLong("" + d2nd22[e]));
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
                                               // empSchedule.setD2nd23(d2nd23[e].equals("null") ? 0 : Long.parseLong("" + d2nd23[e]));
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
                                                //empSchedule.setD2nd24(d2nd24[e].equals("null") ? 0 : Long.parseLong("" + d2nd24[e]));
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
                                                //empSchedule.setD2nd25(d2nd25[e].equals("null") ? 0 : Long.parseLong("" + d2nd25[e]));
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
                                                //empSchedule.setD2nd26(d2nd26[e].equals("null") ? 0 : Long.parseLong("" + d2nd26[e]));
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
                                                //empSchedule.setD2nd27(d2nd27[e].equals("null") ? 0 : Long.parseLong("" + d2nd27[e]));
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
                                                //empSchedule.setD2nd28(d2nd28[e].equals("null") ? 0 : Long.parseLong("" + d2nd28[e]));
                                            }    
                                        }
                                        if(29  <= maxDay){
                                              if (dt <= 29) {
                                                   boolean scheduleLeave = false;
                                                   try{
                                                       scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd29(),specialSchedule);  
                                                   }catch(Exception E){}

                                                   if(scheduleLeave == true){
                                                       empSchedule.setD2nd29(objEmpSchedule.getD2nd29());
                                                   }else{
                                                       //empSchedule.setD2nd29(d2nd29[e].equals("null") ? 0 : Long.parseLong("" + d2nd29[e]));
                                                   }
                                               }
                                        }
                                       
                                        if(30  <= maxDay){
                                               if (dt <= 30) {
                                                    boolean scheduleLeave = false;
                                                    try{
                                                        scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd30(),specialSchedule);  
                                                    }catch(Exception E){}

                                                    if(scheduleLeave == true){
                                                        empSchedule.setD2nd30(objEmpSchedule.getD2nd30());
                                                    }else{
                                                        //empSchedule.setD2nd30(d2nd30[e].equals("null") ? 0 : Long.parseLong("" + d2nd30[e]));
                                                    }
                                                }
                                        }
                                        
                                        if(31 <= maxDay){
                                            if (dt <= 31) {
                                            boolean scheduleLeave = false;
                                            try{
                                                scheduleLeave = SessLeaveApplication.scheduleLeave(objEmpSchedule.getD2nd31(),specialSchedule);  
                                            }catch(Exception E){}
                                            
                                            if(scheduleLeave == true){
                                                empSchedule.setD2nd31(objEmpSchedule.getD2nd31());
                                            }else{
                                                //empSchedule.setD2nd31(d2nd31[e].equals("null") ? 0 : Long.parseLong("" + d2nd31[e]));
                                            }
                                        }
                                        } 
                                        

                                        EmpSchedule empBeforeUpdate = PstEmpSchedule.fetchExc(empSchedule.getOID());
                                       if(empSchedule.getEmployeeId()!=0){
                                           
                                           
                                        PstEmpSchedule.updateExc(empSchedule);
                                                       
                                        PstPresence.importPresenceTriggerByImportEmpScheduleExcel(empBeforeUpdate, empSchedule);
                                       }else{
                                         msgString =  msgString + "<div class=\"errfont\">Can't save data row " + (e + 1) + "</div>"; 
                                       }

                                   
                                    }
                                } catch (Exception exc) {
                                    System.out.println("Exception"+exc);
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
                                if(29  <= maxDay && 29 <= vCCode.size()){ 
                                empSchedule.setD29(d29[e].equals("null") ? 0 : Long.parseLong("" + d29[e]));
                                }else{
                                    empSchedule.setD29(0);
                                }
                                 if(30  <= maxDay && 30 <= vCCode.size()){ 
                                    empSchedule.setD30(d30[e].equals("null") ? 0 : Long.parseLong("" + d30[e]));
                                }else{
                                    empSchedule.setD30(0);
                                }
                                if(31  <= maxDay && 31 <= vCCode.size()){ 
                                empSchedule.setD31(d31[e].equals("null") ? 0 : Long.parseLong("" + d31[e]));
                                }else{
                                    empSchedule.setD31(0);
                                }
                                    long oid =0;
                                    if(empSchedule!=null && empSchedule.getEmployeeId()!=0){
                                         oid = PstEmpSchedule.insertExc(empSchedule);
                                        empSchedule.setOID(oid);
                                                                            
                                        EmpSchedule objEmpSchedulePrev = new EmpSchedule();
                                        objEmpSchedulePrev.setOID(empSchedule.getOID());
                                        objEmpSchedulePrev.setPeriodId(empSchedule.getPeriodId());
                                        objEmpSchedulePrev.setEmployeeId(empSchedule.getEmployeeId());
                                        
                                        PstPresence.importPresenceTriggerByImportEmpScheduleExcel(objEmpSchedulePrev, empSchedule);

                                    }else{
                                         msgString = msgString + "<div class=\"errfont\">Can't save data row " + (e + 1) + "</div>";
                                    }

                                    
                                    }else{
                                         msgString = msgString + "<div class=\"errfont\">Can't save data row " + (e + 1) + " because can't find employee</div>";
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
                        //di hidden by satrya 2013-06-07
                        //msgString = "<div class=\"errfont\">Some data have been saved, but one or more with the <b>red highlight</b> cannot saved because its <b>leave stock is empty or not enough</b> for this schedule...</div>";
                    }
                    }catch(Exception exc){
                                  System.out.println("Exception save "+exc);
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
    <body bgcolor="<%=bgColorBody%>" <%=verTemplate.equalsIgnoreCase("0")%> leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">      
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" <%=headerStyle ? "" : "bgcolor=\"#F9FCFF\""%> >
            <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
            <tr> 
                <td width="100%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
                        <tr> 
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
                                    <tr> 
                                        <td height="20"></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table  width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td style="background-color:<%=bgColorContent%>; " > 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <%
                                                                                 double Beforedays =  positionOfLoginUser.getDeadlineScheduleBefore()/24;
                                                                                Date deadDay = new Date();//Date today = new Date();
                                                                                    //mencari batasan harinya (sebelumnya)
                                                                                deadDay.setHours(deadDay.getHours() - positionOfLoginUser.getDeadlineScheduleBefore());
                                                                                Period periodDead = PstPeriod.getPeriodBySelectedDate(deadDay);
                                                                                %>
                                                                                Schedule yang bisa diupdate dari tanggal <%=deadDay%>
                                                                                <%
            TextLoader uploader = new TextLoader();
            FileOutputStream fOut = null;
            ByteArrayInputStream inStream = null;
            StringBuffer drawList = new StringBuffer();
            Vector vSchedulePerEmp = new Vector(1,1);
            Hashtable schdelueDate = new Hashtable();
            Hashtable schdelueName = new Hashtable();


            // jumlah kolom schedule (employee name, payroll number, 31 column for date schedule 
            int numcol = 33;

            try {

                // jika command==SAVE, maka ambil data dari SESSION yang ada, simpan ke vector "v"
                if (iCommand == Command.SAVE) {
                    Vector vector = (Vector) session.getValue("WORK_SCHEDULE");
                    Hashtable schName = (Hashtable) session.getValue("SCHEDULE_NAME");
                    Hashtable schDate = (Hashtable) session.getValue("SCHEDULE_DATE");
                    String sCreator = (String) session.getValue("CREATOR_NAME"); 
                    String sdeptName = (String) session.getValue("DEPARTEMENT_NAME"); 
                    
                    vSchedulePerEmp = (Vector) vector.clone();
                    schdelueDate = (Hashtable) schDate.clone();
                    schdelueName = (Hashtable) schName.clone();
                    creatorName = (String) sCreator;
                    depName = (String) sdeptName;
                } // jika command!=SAVE, maka proses import dari file excel, simpan ke vector "v"
                else {
                    uploader.uploadText(config, request, response);
                    Object obj = uploader.getTextFile("file");
                    byte byteText[] = null;
                    byteText = (byte[]) obj;
                    inStream = new ByteArrayInputStream(byteText);
                    Excel tp = new Excel();
                    
                schdelueDate = new Hashtable();
                schdelueName = new Hashtable();
                  EmployeeUpload employeeUpload = new EmployeeUpload();
                 
                Vector headerX = new Vector(); 
                boolean schDate = false;
                int col_name = -1;
                int row_name = -1;
                POIFSFileSystem fs = new POIFSFileSystem(inStream);
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(0);
                int rows = sheet.getPhysicalNumberOfRows();                
                  for (int r =0; r < rows; r++) {
                       Employee employee = null;
                      try{ 
                            employeeUpload = new EmployeeUpload();
	                    HSSFRow row = sheet.getRow(r);
	                    int cells = 0;
                        //if number of cell is static
                        if(NUM_CELL > 0){
                        	cells = NUM_CELL;
                        }
                        //number of cell is dinamyc
                        else{
                            cells = row.getPhysicalNumberOfCells();
                        }

                        // ambil jumlah kolom yang sebenarnya
                        NUM_CELL = cells;
                        
                        String cellColor="";                            
	                    for (int c = 0; c <= cells; c++)
	                    {   
                                cellColor="#CCCCCC";
	                        HSSFCell cell  = row.getCell((short) c);
                                String   value = null;
                                int dtInt = 0;
                                if(cell!=null){
	                        switch (cell.getCellType())
	                        {
	                            case HSSFCell.CELL_TYPE_FORMULA :
	                                //value = "FORMULA ";
	                                value = String.valueOf(cell.getCellFormula());;
	                                break;
	                            case HSSFCell.CELL_TYPE_NUMERIC :
	                                //value = "NUMERIC value=" + cell.getNumericCellValue();
	                                value = String.valueOf(cell.getNumericCellValue());
                                         if(value.endsWith(".0")){
                                         value=value.substring(0, value.length()-2);
                                         };
                                        if(value!=null && value.length()>0){
                                            dtInt = Integer.parseInt(value);
                                        }
                                        if(!schDate && value!=null && (r>=ROW_SCH) && (c>=COL_SCH_MIN && c <= COL_SCH_MAX)){                                             
                                            if(dtInt<=maxDay){ 
                                               schdelueDate.put(""+c, value);   
                                            }
                                         }
	                                break;
                                    case HSSFCell.CELL_TYPE_STRING :
	                                //value = "STRING value=" + cell.getStringCellValue();
	                                value = String.valueOf(cell.getStringCellValue());
                                        
                                        if(value!=null && r==ROW_DEPARTEMENT && c==COL_DEPARTEMENT_MIN){
                                            depHeader = value;
                                        }else if(value!=null && r==ROW_DEPARTEMENT && c==COL_DEPARTEMENT_MAX){
                                            depName = value;
                                        }
                                        //mencari period Name
                                        if(value!=null && r==ROW_PERIOD && c==COL_PERIOD_MIN){
                                            periodHeader = value;
                                        }else if(value!=null && r==ROW_PERIOD && c==COL_PERIOD_MAX){
                                            periodName = value;
                                        }
                                        // mencari create BY
                                        if(value!=null && r==ROW_CREATE_BY && c==COL_CREATE_BY_MIN){
                                            creatorHeader = value; 
                                        }else if(value!=null && r==ROW_CREATE_BY && c==COL_CREATE_BY_MAX){
                                            creatorName = value; 
                                        }
                                        // mencari sch date
                                        if(value!=null && r==ROW_SCH_DATE && c>=COL_SCHDATE_MIN && c<=COL_SCHDATE_MAX){
                                            schdelueName.put(""+c, value);  
                                        }
	                                break;                                                                        
	                            default :
                                        value = String.valueOf(cell.getStringCellValue()!=null?cell.getStringCellValue():"");
                                        ;
	                        }
                                 try{ // search for the row containts the first employee data
                                if(value!=null && r>=ROW_EMPLOYEE && c>=COL_EMPLOYEE_MIN && c<=COL_EMPLOYEE_MAX && value!=null){
                                    
                                    if(c==COL_EMPLOYEE_MIN){
                                        //mencari namanya
                                        fullNameExcel = value;
                                    }
                                     //mencari employee berdasarkan employee_number
                                   if(employee==null && c==COL_EMPNUMB){
                                       employee = PstEmployee.getEmployeeByNum(value);
                                     if(employee!=null){
                                       employeeUpload.setEmpNumberExcel(value);
                                       employeeUpload.setEmpId(employee.getOID());
                                       employeeUpload.setEmpName(employee.getFullName());
                                       employeeUpload.setEmpNumb(employee.getEmployeeNum());
                                       employeeUpload.setDeptName(depName);
                                       employeeUpload.setEmpNameKeyPayrol(fullNameExcel);
                                       employeeUpload.setCreatorName(creatorName); 
                                     }else{
                                       employee = PstEmployee.getEmployeeByFullName(fullNameExcel);
                                       employeeUpload.setEmpNumberExcel(value);
                                      if(employee!=null){
                                       employeeUpload.setEmpId(employee.getOID());
                                       employeeUpload.setEmpName(employee.getFullName());
                                       employeeUpload.setEmpNumb(employee.getEmployeeNum());
                                      }else{
                                       employeeUpload.setEmpId(0);
                                       employeeUpload.setEmpName(null);
                                       employeeUpload.setEmpNumb(null);
                                       employee = new Employee(); 
                                      } 
                                       employeeUpload.setDeptName(depName);
                                       employeeUpload.setEmpNameKeyPayrol(fullNameExcel);
                                       employeeUpload.setCreatorName(creatorName); 
                                     }
                                   }
                                   if(employee!=null && c>=COL_EMP_SCH){
                                       employeeUpload.addSchedule(c, value);
                                   }
                                 } 
                                } catch(Exception exc){
                                    System.out.println("r="+r+" c="+c+" "+exc);
                                }
                            }
                        
                         }
                      }catch(Exception exc){
                            System.out.println("Exception"+exc); 
                        }
                      //prosess menampung schedule input karyawan
                     if(employeeUpload!=null && employeeUpload.getSchedule()!=null && employeeUpload.getSchedule().size()>0){
                       vSchedulePerEmp.add(employeeUpload); 
                      }
                  }
                    // proses data vector hasil parsing ke SESSION
                    if (session.getValue("WORK_SCHEDULE") != null) {
                        session.removeValue("WORK_SCHEDULE");
                        session.removeValue("SCHEDULE_NAME");
                        session.removeValue("SCHEDULE_DATE");
                        session.removeValue("CREATOR_NAME");
                        session.removeValue("DEPARTEMENT_NAME");
                               
                    }
                    session.putValue("WORK_SCHEDULE", vSchedulePerEmp);
                    session.putValue("SCHEDULE_NAME", schdelueName);
                    session.putValue("SCHEDULE_DATE", schdelueDate);
                    session.putValue("CREATOR_NAME", creatorName);
                    session.putValue("DEPARTEMENT_NAME", depName);
                    

                }

                // Tampilkan data dengan list berupa table
                boolean useDayHeader = true;
                 EmployeeUpload employeeUpload = new EmployeeUpload();
                drawList.append("<form name=\"frmupload\" method=\"post\" action=\"\">" +
                        "\n<input type=\"hidden\" name=\"command\" value=\"" + iCommand + "\">");

                if (vSchedulePerEmp.size() > 0) {
                    drawList.append("\n<table cellpadding=\"2\" cellspacing=\"2\" border=\"0\">" + 
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"3\"><B><font size=\"3\">" + topHeader  + "</font></B></td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"2\"><B>DEPARTMENT</B></td>" +
                            "\n\t\t<td>" + (depName.length()>0 ? depName : employeeUpload.getDeptName())+ "</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"2\"><B>CREATED BY</B></td>" +
                            "\n\t\t<td> " + (creatorName.length()>0 ? creatorName: employeeUpload.getCreatorName()) + "</td>" +
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
                            "\n\t\t<td>" + ControlCombo.draw("period_id", "formElemen", "select...", "" + (periodX.getOID()!=0?periodX.getOID():periodId), periodValue, periodKey) + "</td>" +
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
                            "\n\t\t<td width=\"77%\" colspan=\"" + (schdelueDate.size()) + "\" align=\"center\" class=\"tableheader\">Date</td>" +
                            "\n\t</tr>");

                    double width = 77 / (new Double(schdelueDate.size()+2)).doubleValue();

                    if (useDayHeader) {
                        // untuk header (day symbol)																
                        drawList.append("\n\t<tr class=\"listheader\">");
                        for (int i = 2; i < schdelueName.size()+2; i++) {
                                String dayCode = "";
                                try{
                                    dayCode = (String.valueOf(schdelueName.get(""+i))).trim(); 
                                } catch (Exception exc){
                                    
                                }
                                drawList.append("\n\t\t<td align=\"center\" width=\"" + width + "%\" class=\"tableheader\">" + dayCode+ "</td>");
                        }
                        drawList.append("\n\t</tr>");
                    }

                    drawList.append("\n\t<tr class=\"listheader\">");
                    for (int h = 2; h < schdelueDate.size()+2; h++) {
                        //if ((h % numcol) > 1) {
                                String dateNumber = "";
                                try{
                                dateNumber = (String.valueOf(schdelueDate.get(""+h))).trim();
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
                    Hashtable hashScheduleOidKey = new Hashtable();
                    hashScheduleOidKey.put("0", " ");
                    Hashtable hashCategory = new Hashtable();
                    hashCategory.put(" ", "0");
                    //add by priska 20151112
                    Period period = new Period();
                    try { period = PstPeriod.fetchExc(periodId); } catch (Exception ex){}
                    Vector listSchSymbol = PstScheduleSymbol.listScheduleSymbolAndCategory();
                    if (listSchSymbol != null && listSchSymbol.size() > 0){
                        int intListSchSymbol = listSchSymbol.size();
                        for (int ls = 0; ls < intListSchSymbol; ls++) {
                            try {
                                Vector vectSchldCat = (Vector) listSchSymbol.get(ls);
                                ScheduleSymbol schSymbol = (ScheduleSymbol) vectSchldCat.get(0);
                                ScheduleCategory schCategory = (ScheduleCategory) vectSchldCat.get(1);

                                hashSchedule.put(schSymbol.getSymbol().toUpperCase(), String.valueOf(schSymbol.getOID()));
                                hashScheduleOidKey.put(String.valueOf(schSymbol.getOID()) , schSymbol.getSymbol().toUpperCase());
                                hashCategory.put(schSymbol.getSymbol().toUpperCase(), String.valueOf(schCategory.getCategoryType()));
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
                    String tmp1stSchd = "";
                    String sch="";
                    String ndsch = "";
                    String writeSchd = "";
                  
                        String clsHtml = "class=\"listgensell\"";
                    //int startEmployeeRow=ROW_EMPLOYEE;
                   
                    //int iCell =0;
                    //String strNameOnList ="";
                        drawList.append("\n\t</tr>\n\t");
                        int start =0; 
                    for (int i =0; i < vSchedulePerEmp.size(); i++){
                        //Vector scheduleAfterCheck = new Vector();
                        
                        employeeUpload = (EmployeeUpload)vSchedulePerEmp.get(i);
                       /// input employeenya
                         drawList.append("<tr>");
                        //prosess no per employee
                         String stEmpNameExcel = employeeUpload.getEmpNameKeyPayrol().trim();
                         String strEmployeeInDB= employeeUpload.getEmpName().trim();
                         String messageFullName=null;
                         if(stEmpNameExcel!=null && stEmpNameExcel.length()>0 
                           && strEmployeeInDB!=null && strEmployeeInDB.length()>0){
                             if(!strEmployeeInDB.equalsIgnoreCase(stEmpNameExcel)){
                                messageFullName = "<strong> <font color=\"#000000\"> <i>" +stEmpNameExcel+ "  </i></font></strong> ( Excel ) <strong> <font color=\"#FF0000\">  Not Match   </font></strong> <strong> <font color=\"#000000\"> <i>" +strEmployeeInDB+ "</i> </font></strong>( Harisma ) please cek your excel"; 
                             }
                         }else{
                             messageFullName = "<strong> <font color=\"#000000\"> <i>"+ stEmpNameExcel +"</i></font></strong> <strong> <font color=\"#FF0000\">  is not in Harisma  </font></strong>"; 
                         }
                        drawList.append("\n\t\t<td " + clsHtml  + ">" + ((start+i)+1) 
                         + "</td><td " + clsHtml + " width=\"" + width + "%\">" 
                         + (messageFullName==null ? "<strong> <font color=\"#000000\"> "+ employeeUpload.getEmpName() +"</font></strong>" : (messageFullName))  + "</td>"); 
                         
                        String stEmpNumberExcel = employeeUpload.getEmpNumberExcel();
                         String strEmpNumberDB= employeeUpload.getEmpNumb();
                         String messageNumber=null;
                         if(stEmpNumberExcel!=null && stEmpNumberExcel.length()>0 
                           && strEmpNumberDB!=null && strEmpNumberDB.length()>0){
                             if(!strEmpNumberDB.equalsIgnoreCase(stEmpNumberExcel)){
                                messageNumber = "<strong> <font color=\"#000000\"><i> Payroll: "+employeeUpload.getEmpNumberExcel()+" </i> </font></strong> (Excel) <strong><font color=\"#FF0000\"> not in Harisma  </font></strong>"; 
                             }
                         }else{
                             messageNumber = " Payroll: <strong> <font color=\"#000000\"><i>  "+employeeUpload.getEmpNumberExcel()+" </i> </font></strong> (Excel) <strong><font color=\"#FF0000\"> not in Harisma  </font></strong>"; 
                         }
                        String st1 = (messageNumber == null ?"":"bgcolor=\"#FFFF00\"");
                        //String st2 = (messageNumber == null ?"":"<strong><font color=\"#FF0000\">");
                       // String st3 = (messageNumber == null ?"": (messageNumber)); 
                        
                         
                        drawList.append("\n\t\t<td " + clsHtml +" " + st1+ " width=\"" + width + "%\">");
                        drawList.append("<input type=\"hidden\" name=\"employee_id\" value=\"" + employeeUpload.getEmpId() + "\">");
                        drawList.append("<input type=\"hidden\" name=\"employee_num\" value=\"" + employeeUpload.getEmpNumb() + "\">");
                        drawList.append(messageNumber == null? "<strong> <font color=\"#000000\">"+ employeeUpload.getEmpNumberExcel() +" </font></strong>":messageNumber + "</td>");
                        
                      for (int h = COL_EMP_SCH; h < schdelueDate.size()+COL_EMP_SCH; h++) {
                            String clsHtmlIdxScheduleErrPre = "";
                        String clsHtmlIdxScheduleErrPost = "";
                          int iStartPeriode = 2+(h-COL_EMP_SCH);
                        String dateNumber = "";
                        int dtNumber =0;
                        try{
                            dateNumber = (String.valueOf(schdelueDate.get(""+iStartPeriode))).trim();
                            //dateNumber=dateNumber.substring(0, dateNumber.indexOf("."));
                            dtNumber = Integer.parseInt(dateNumber);
                            //pengecekan dengan colom di schedule
                            if(employeeUpload.getScheduleCheck(iStartPeriode)){    
                                tmp1stSchd = (String.valueOf(employeeUpload.getSchedule(h))).trim();
                                
                                //add by priska menampilkan schedule yang berhasil dirubah saja 20151112
                                if (Command.SAVE == iCommand){
                                long schOid = PstEmpSchedule.getSchedule(dtNumber, employeeUpload.getEmpId(), period.getStartDate());
                                sch = hashScheduleOidKey.get(""+schOid).toString();
                                }
                                  if (hashSchedule.get(tmp1stSchd.trim().toUpperCase()) == null) {
                                     sch = "?";
                                     writeSchd = sch;
                                  }else{
                                    sch = tmp1stSchd.toUpperCase(); 
                                    writeSchd = sch;
                                  }
                         if (sch.equalsIgnoreCase("?")) {
                                clsHtmlIdxScheduleErrPre = "<span class=\"errfont\">";
                                clsHtmlIdxScheduleErrPost = "</span>";
                        }
                                   //prosess pemasukan schedule
                                
                          switch (dtNumber){  
                              case 1:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D1\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND1\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT1\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>"); 
                                break;

                            case 2:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D2\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND2\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT2\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 3:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D3\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND3\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT3\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 4:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D4\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND4\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT4\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 5:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D5\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND5\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT5\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 6:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D6\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND6\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT6\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 7:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D7\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND7\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT7\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 8:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D8\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND8\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT8\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 9:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D9\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND9\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT9\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 10:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D10\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND10\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT10\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 11:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D11\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND11\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT11\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 12:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D12\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND12\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT12\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 13:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D13\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND13\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT13\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 14:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D14\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND14\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT14\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 15:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D15\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND15\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT15\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 16:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D16\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND16\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT16\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 17:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D17\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND17\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT17\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 18:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D18\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND18\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT18\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 19:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D19\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND19\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT19\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 20:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D20\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND20\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT20\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 21:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D21\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND21\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT21\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 22:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D22\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND22\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT22\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 23:
                                drawList.append("\n\t\t<td  " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D23\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND23\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT23\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 24:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D24\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND24\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT24\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 25:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D25\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND25\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT25\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 26:
                                drawList.append("\n\t\t<td  " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D26\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND26\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT26\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 27:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D27\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND27\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT27\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 28:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D28\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND28\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT28\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 29:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D29\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND29\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT29\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 30:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D30\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND30\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT30\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 31:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D31\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND31\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT31\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            default:
                                break;
                          }
                               
                            }
                        }
                        catch (Exception exc){   

                        }
                        
                        
                    }
                    drawList.append("\n\t</tr>");  
                }
                    drawList.append("\n\t</tr> " + "\n</table>");  
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
                    //if (iCommand != Command.SAVE) {
                    drawList.append("\n\t<tr>" +
                            "\n\t\t<td width=\"1%\"><img src=\"" + approot + "/images/spacer.gif\" width=\"4\" height=\"4\"></td>" +
                            "\n\t\t<td width=\"5%\"><a href=\"javascript:cmdSave()\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image300','','" + approot + "/images/BtnSaveOn.jpg',1)\"><img name=\"Image300\" border=\"0\" src=\"" + approot + "/images/BtnSave.jpg\" width=\"24\" height=\"24\" alt=\"Save\"></a></td>" +
                            "\n\t\t<td width=\"1%\"><img src=\"" + approot + "/images/spacer.gif\" width=\"4\" height=\"4\"></td>" +
                            "\n\t\t<td width=\"93%\"nowrap> <a href=\"javascript:cmdSave()\" class=\"command\">Save Working Schedule</a></td>" +
                            "\n\t</tr>"); 
                    //   }
                    drawList.append("\n</table>");
                
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
            <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
         
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
