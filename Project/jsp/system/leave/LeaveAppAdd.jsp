
<%-- 
    Document   : LeaveAppAdd
    Created on : Dec 22, 2009, 11:59:09 AM
    Author     : Tu Roy
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*"%>
<%@ page import = "com.dimata.harisma.form.attendance.*"%>
<%@ page import = "com.dimata.harisma.entity.employee.*"%>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*"%>
<%@ page import = "com.dimata.harisma.form.leave.*"%>
<%@ page import = "com.dimata.harisma.session.attendance.*"%>
<%@ page import = "com.dimata.harisma.session.leave.*"%>
<%@ page import = "com.dimata.harisma.session.employee.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
    /*
     * Menampilkan checkbox dan tanggal sesuai schedule leave
     * 
     * Parameter :
     *      dateRequested = list tgl untuk suatu schedule leave
     *
     * Output :
     *      String : kode html untuk cell tanggal 
     */

    private String drawDateInCell(long symbolId, Vector dateRequested) {
        
        String dateStr = "";
        
        if(dateRequested != null) 
        {                     

            // tampilkan daftar hari
            Vector adjacentDate = new Vector(); // penampung sementara untuk tgl yang berdampingan

            // cek tiap tanggal yang direquest
            for(int x=0; x<dateRequested.size(); x++) 
            {
                // ambil tanggal untuk iterasi sekarang
                Date currentDate = (Date)dateRequested.get(x);


                if(x == 0) {

                    // cek jumlah tanggal yang direquest
                    if(dateRequested.size() > 1) {

                        // jika > 1, masukkan tanggal pertama ke vector
                        adjacentDate.add(currentDate);
                        continue;
                    }
                    else {

                        // jika 1, langsung buat output
                        //dateStr += "<input type=\"checkbox\" name=\"leave_status\" value=\"1\"  checked=\"checked\"> &nbsp; &nbsp;";
                        dateStr += "<input type=\"hidden\" name=\"leave_status\" value=\"1\">";
                        dateStr += Formater.formatDate(currentDate, "MMM dd, yyyy") + "<br />";
                        dateStr += "<input type=\"hidden\" name=\"date_start\" value=\"" + currentDate.getTime() + "\">";
                        dateStr += "<input type=\"hidden\" name=\"date_end\" value=\"" + currentDate.getTime() + "\">";
                        dateStr += "<input type=\"hidden\" name=\"symbol_id\" value=\"" + symbolId + "\">";
                    }
                }
                else 
                {
                    // cek apakah tgl berdampingan
                    Date lastDate = (Date)adjacentDate.lastElement();   // tgl terakhir di penampung

                    // jika berjarak 24 jam, berarti tanggal berdampingan
                    if(currentDate.getTime() - lastDate.getTime() <= (24 * 60 * 60 * 1000)) {

                        // tambah ke penampung
                        adjacentDate.add(currentDate);

                        // cek apakah ada pengulangan selanjutnya, jika tidak, output data
                        if(x == dateRequested.size() - 1)  // iterasi terakhir
                        {    

                            // ambil range bawah dan range atas
                            sort(adjacentDate);
                            Date startRange = (Date)adjacentDate.firstElement();
                            Date endRange = (Date)adjacentDate.lastElement();

                            // buat output tanggal 
                            //dateStr += "<input type=\"checkbox\" name=\"leave_status\" value=\"1\"  checked=\"checked\"> &nbsp; &nbsp;";
                            dateStr += "<input type=\"hidden\" name=\"leave_status\" value=\"1\">";
                            dateStr += Formater.formatDate(startRange, "MMM dd, yyyy") + " &nbsp; &ndash; &nbsp; " + Formater.formatDate(endRange, "MMM dd, yyyy") + "<br />";
                            dateStr += "<input type=\"hidden\" name=\"date_start\" value=\"" + startRange.getTime() + "\">";
                            dateStr += "<input type=\"hidden\" name=\"date_end\" value=\"" + endRange.getTime() + "\">";
                            dateStr += "<input type=\"hidden\" name=\"symbol_id\" value=\"" + symbolId + "\">";
                            
                        }
                        else {
                            continue;
                        }
                    }
                    else {

                        // ambil range bawah dan range atas
                        sort(adjacentDate);
                        Date startRange = (Date)adjacentDate.firstElement();
                        Date endRange = (Date)adjacentDate.lastElement();

                        // buat output tanggal sebelumnya

                        if(startRange.compareTo(endRange) == 0)     // hanya 1 hari
                        {
                            //dateStr += "<input type=\"checkbox\" name=\"leave_status\" value=\"1\"  checked=\"checked\"> &nbsp; &nbsp;";
                            dateStr += "<input type=\"hidden\" name=\"leave_status\" value=\"1\">";
                            dateStr += Formater.formatDate(startRange, "MMM dd, yyyy") + "<br />";
                            dateStr += "<input type=\"hidden\" name=\"date_start\" value=\"" + startRange.getTime() + "\">";
                            dateStr += "<input type=\"hidden\" name=\"date_end\" value=\"" + startRange.getTime() + "\">";
                            dateStr += "<input type=\"hidden\" name=\"symbol_id\" value=\"" + symbolId + "\">";
                            
                        }
                        else 
                        {
                            //dateStr += "<input type=\"checkbox\" name=\"leave_status\" value=\"1\"  checked=\"checked\"> &nbsp; &nbsp;";
                            dateStr += "<input type=\"hidden\" name=\"leave_status\" value=\"1\">";
                            dateStr += Formater.formatDate(startRange, "MMM dd, yyyy") + " &nbsp; &ndash; &nbsp; " + Formater.formatDate(endRange, "MMM dd, yyyy") + "<br />";
                            dateStr += "<input type=\"hidden\" name=\"date_start\" value=\"" + startRange.getTime() + "\">";
                            dateStr += "<input type=\"hidden\" name=\"date_end\" value=\"" + endRange.getTime() + "\">";
                            dateStr += "<input type=\"hidden\" name=\"symbol_id\" value=\"" + symbolId + "\">";
                            
                        }

                        // kosongkan penampung
                        adjacentDate.clear();

                        // tambah tanggal terakhir ke penampung
                        adjacentDate.add(currentDate);


                        // cek apakah ada pengulangan selanjutnya, jika tidak, output data
                        if(x == dateRequested.size() - 1)  // iterasi terakhir
                        {    

                            // ambil range bawah dan range atas
                            startRange = (Date)adjacentDate.firstElement();                                        

                            // buat output tanggal 
                            //dateStr += "<input type=\"checkbox\" name=\"leave_status\" value=\"1\"  checked=\"checked\"> &nbsp; &nbsp;";
                            dateStr += "<input type=\"hidden\" name=\"leave_status\" value=\"1\">";
                            dateStr += Formater.formatDate(startRange, "MMM dd, yyyy") + "<br />";
                            dateStr += "<input type=\"hidden\" name=\"date_start\" value=\"" + startRange.getTime() + "\">";
                            dateStr += "<input type=\"hidden\" name=\"date_end\" value=\"" + startRange.getTime() + "\">";
                            dateStr += "<input type=\"hidden\" name=\"symbol_id\" value=\"" + symbolId + "\">";
                            
                        }
                        else {
                            continue;
                        }

                    } // end check adjacent date

                } // end check iteration index


                // tambahkan baris baru
                if(x < dateRequested.size()-1) 
                    dateStr += "<br />";

            }                        

        }
        
        return dateStr;
        
    }

    /*
     * Menurutkan tanggal ascending
     * 
     * Parameter / Output :
     *      dates : Vector taggal pengambilan
     *
     */
    
    private void sort(Vector dates) {
       if(dates != null && dates.size()>0) {
            
            for(int i=0; i<dates.size()-1; i++) {
                int idx = i;            
                Date min = (Date)dates.get(i);
                
                for(int j=i+1; j<dates.size(); j++) {
                    Date curr = (Date)dates.get(j);
                                                           
                    if(curr.getTime() < min.getTime()) {
                        idx = j;
                        min = curr;
                    }
                    
                }
                
                Date tmp = null;
                
                tmp = (Date)dates.get(i);
                dates.setElementAt(dates.get(idx), i);
                dates.setElementAt(tmp, idx);
            }
            
        }
    }

    /*
     * Membuat table detail leave
     * 
     * Parameter :
     *      ids = list symbol oid untuk annual/special leave
     *      names = list symbol name untuk annual/special leave
     *      cats = list category untuk symbol annual/special leave
     *      
     *      qty = list jumlah requested day tiap tipe leave berdasarkan schedule/table
     *      dates = vector list tanggal (sebanyak qty) untuk tiap requested day 
     *      saved = flag menyatakan data induk sudah disimpan/belum 
     *
     * Output :
     *      String : kode html untuk table detail leave 
     *   
     */

    public Vector drawList(Vector ids, Vector names, Vector cats, 
                           Vector qty, Vector dates, long oidEmployee, long oidLeave,
                           boolean saved, boolean approved){
            
            Vector result = new Vector();
            Vector vctDetails = new Vector();
            
        
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            
            ctrlist.addHeader("No","5%");
            ctrlist.addHeader("Type of Leave.","30%");
            ctrlist.addHeader("No. of Days <br />Eligible","15%");
            ctrlist.addHeader("No. of Days <br />Requested","15%");
            ctrlist.addHeader("Date", "25%");  
            ctrlist.addHeader("Action", "10%");                        
  
            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();

            if(ids != null && ids.size()>0) 
            {                
                for(int i=0; i<ids.size(); i++) 
                {
                    Vector rowx = new Vector();
                    long id = Long.parseLong(String.valueOf(ids.get(i)));
                    String name = String.valueOf(names.get(i));

                    rowx.add("" + (i + 1));                    
                    rowx.add(name);
                                                                                
                    long scheduleId = Long.parseLong(String.valueOf(ids.get(i)));   
                    int scheduleCat = Integer.parseInt(String.valueOf(cats.get(i)));
                    int dayEligible = SessLeaveApplication.countEligibleDay(oidEmployee, scheduleId, scheduleCat);                    
                    int dayRequested = Integer.parseInt(String.valueOf(qty.get(i)));
                    
                    // updated
                    if(scheduleCat == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) {
                         //if(saved)
                             //rowx.add(String.valueOf(dayEligible + dayRequested));
                         //else
                             rowx.add(String.valueOf(dayEligible));
                    }
                    else {
                         rowx.add(String.valueOf(dayEligible + dayRequested));
                    }
                    
                    rowx.add(String.valueOf(dayRequested));
                    
                    Vector dateRequested = (Vector)dates.get(i);                    
                    String dateStr = drawDateInCell(id, dateRequested);
                    rowx.add(dateStr);    
                                        
                    // tambahkan link edit di kolom terakhir jika sudah di-save
                    if(saved && !approved) 
                        rowx.add("<a href=\"javascript:cmdEditDates('" + oidEmployee + "','" + oidLeave +"','" + ids.get(i) + "','" + cats.get(i) + "','" + dayEligible + "')\">Add</a>");
                    else
                        rowx.add("");  
                        
                    lstData.add(rowx);                      
                    vctDetails.add(rowx);
                }     
            }  
                                           
            result.add(ctrlist.draw());
            result.add(vctDetails);
            
            return result;
	}
    
    
        public void parserDate(Calendar date, int iCommand, long symbolId, long oidEmployee, SpecialLeaveStock leaveStock,
                               CtrlSpecialLeaveStock ctrlSpecialLeaveStock, SessLeaveApplication sessLeave){
            
            Calendar d = (Calendar)date.clone();
            
            for(int i=1; i<=date.getActualMaximum(Calendar.DATE); i++){
                d.set(Calendar.DATE, i);
                
                long oidStock = sessLeave.getLeaveStock(symbolId, oidEmployee, d.getTime());
                leaveStock.setOID(oidStock);
                leaveStock.setTakenDate(d.getTime());
                
                ctrlSpecialLeaveStock.action(iCommand, oidStock, leaveStock);
            }            
        }
        
        public long getCategoryId(Vector specialLeaveIds, Vector specialLeaveCats, long symbolId) {
            // ambil category AL
            if(specialLeaveIds != null) {
                for(int i=0; i<specialLeaveIds.size(); i++) {
                    long id = Long.parseLong(String.valueOf(specialLeaveIds.get(i)));

                    if(id == symbolId) {
                        return Long.parseLong(String.valueOf(specialLeaveCats.get(i)));
                    }
                }
            }
            
            return 0;
        }
    
%>

<%
    int iCommand = FRMQueryString.requestCommand(request); 
    int start = FRMQueryString.requestInt(request, "start");
    long oidEmployee = FRMQueryString.requestLong(request, "oid_employee");
    long oidLeave = FRMQueryString.requestLong(request, "oid_leave");
    long oidPeriod = FRMQueryString.requestLong(request, "oid_period");
    int isSaved = FRMQueryString.requestInt(request, "saved");
    
    Employee dataemployee = new Employee();
    
    long approvedId1 = FRMQueryString.requestLong(request, "approvedId1");
    long approvedId2 = FRMQueryString.requestLong(request, "approvedId2");
    long approvedId3 = FRMQueryString.requestLong(request, "approvedId3");
    boolean isApprove1 = FRMQueryString.requestBoolean(request, "isApprove1");
    boolean isApprove2 = FRMQueryString.requestBoolean(request, "isApprove2");
    boolean isApprove3 = FRMQueryString.requestBoolean(request, "isApprove3");
    
    String[] leaveStatus = request.getParameterValues("leave_status");
    String[] symbolIds = request.getParameterValues("symbol_id");
    String[] dateStarts = request.getParameterValues("date_start");
    String[] dateEnds = request.getParameterValues("date_end");
  
    if(oidLeave != 0) {
        isSaved = 1;
    }

    
    SessLeaveApplication sessLeave = new SessLeaveApplication();
    SpecialLeave specialLeave = new SpecialLeave();
    
    Vector specialLeaveStocks = new Vector(1,1);
    
       
    /* General Data */    
    Vector specialLeaveIds = new Vector(1,1);       // daftar oid schedule symbol dgn kategori special/annual leaves
    Vector specialLeaveNames = new Vector(1,1);     // daftar nama schedule symbol dgn kategori special/annual leaves
    Vector specialLeaveCats = new Vector(1,1);      // daftar kategori tiap symbol (kategori: special leave atau annual leave)
  
    // ambil semua symbol special/annual leaves
    Vector tmp = sessLeave.getDistinctSpecialLeave();
    specialLeaveIds = (Vector)tmp.get(0);
    specialLeaveNames = (Vector)tmp.get(1);
    specialLeaveCats = (Vector)tmp.get(2);    
    
        
    // siapkan penampung untuk jumlah & tgl permintaan tiap leave dalam 1 periode
    Vector specialLeaveReqQty = new Vector(1,1);
    Vector specialLeaveReqDate = new Vector(1,1);
    
    // inisialisasi
    if(specialLeaveIds != null) {
        for(int i=0; i<specialLeaveIds.size(); i++) {
            specialLeaveReqQty.add("0");
            specialLeaveReqDate.add(new Vector());
        }
    }
    
    
    // jika data telah disimpan sebelumnya, ambil dari database    
    if(oidLeave != 0) {
        
        try {
            specialLeave = PstSpecialLeave.fetchExc(oidLeave);
        }
        catch(Exception e) {
            specialLeave = new SpecialLeave();            
        }
        
        // ambil detail leave
        try {
            String where = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SPECIAL_LEAVE_ID] + "=" + oidLeave;            
            String order = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE];
            specialLeaveStocks = PstSpecialLeaveStock.list(0, 0, where, order);
        }
        catch(Exception e) {
            specialLeaveStocks = new Vector(1,1);
        }
        
        // specialLeaveReqQty n specialLeaveReqDate hitung disini!
        if(specialLeaveStocks != null && specialLeaveStocks.size()>0) {
            
            for(int i=0; i<specialLeaveStocks.size(); i++) {  
                
                // ambil leave stock
                SpecialLeaveStock leaveStock = (SpecialLeaveStock)specialLeaveStocks.get(i);
                
                
                // ambil symbol dan tgl schedule
                ScheduleSymbol symbol = new ScheduleSymbol();
                                       
                try {
                    symbol = PstScheduleSymbol.fetchExc(leaveStock.getSymbolId());
                     
                }
                catch(Exception e) {
                    symbol = new ScheduleSymbol();
                }
                
                Date date = (Date)leaveStock.getTakenDate();
                
                // iterasi daftar special leaves untuk menentukan jenis special leaves
                for(int j=0; j<specialLeaveIds.size(); j++) {
                    long specialSymbolOid = Long.parseLong(String.valueOf(specialLeaveIds.get(j)));
                    
                    if(symbol.getOID() == specialSymbolOid)  {
                        
                        // increment jumlah leave yang diambil untuk leave ybs
                        int total = Integer.parseInt(String.valueOf(specialLeaveReqQty.get(j)));
                        total++;
                        specialLeaveReqQty.setElementAt(String.valueOf(total), j);
                        
                        // tambahkan tgl pengambilan untuk leaves ybs
                        Vector dates = (Vector)specialLeaveReqDate.get(j);
                        dates.add(date);
                        
                        break;
                    }                     
                    
                } // end all special leaves list iteration
                
            } // end special leaves on schedule iteration
        }
                
    } 

    // jika data belum disimpan, ambil dari schedule   
    else     
    {      
        
        // ambil data schedule symbol dan tgl untuk tiap annual/special leaves
        
        Vector temp = sessLeave.searchSpecialLeaveScheduleSymbols(oidPeriod, oidEmployee);

        Vector leaveSymbols = (Vector)temp.get(0);
        Vector leaveDates = (Vector)temp.get(1);
        
        if(leaveSymbols != null && leaveSymbols.size() > 0 && specialLeaveIds != null) {
            
            for(int i=0; i<leaveSymbols.size(); i++) {       
                  
                // ambil symbol dan tgl schedule
                ScheduleSymbol symbol = (ScheduleSymbol)leaveSymbols.get(i);
                Date date = (Date)leaveDates.get(i);
                
                // iterasi daftar special leaves untuk menentukan jenis special leaves
                for(int j=0; j<specialLeaveIds.size(); j++) {
                    long specialSymbolOid = Long.parseLong(String.valueOf(specialLeaveIds.get(j)));
                    
                    if(symbol.getOID() == specialSymbolOid)  {
                        
                        // increment jumlah leave yang diambil untuk leave ybs
                        int total = Integer.parseInt(String.valueOf(specialLeaveReqQty.get(j)));
                        total++;
                        specialLeaveReqQty.setElementAt(String.valueOf(total), j);
                        
                        // tambahkan tgl pengambilan untuk leaves ybs
                        Vector dates = (Vector)specialLeaveReqDate.get(j);
                        dates.add(date);
                        
                        break;
                    }                     
                    
                } // end all special leaves list iteration
                
            } // end special leaves on schedule iteration
            
        } 
        
    } // end checking save
    
    
    // mengambil data employee
    Employee emp = new Employee();
    Division div = new Division();
    Department dept = new Department();
    Position pos = new Position();
        
    try {
        if(oidEmployee == 0 && specialLeave.getEmployeeId()>0)
            oidEmployee = specialLeave.getEmployeeId();
        else
            specialLeave.setEmployeeId(oidEmployee);
    }
    catch(Exception e) {
        oidEmployee = 0;
    }
    
    
    I_Leave leaveConfig = null;           
    try {
        leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
    }
    catch(Exception e) {
        System.out.println("Exception : " + e.getMessage());
    }

    int maxApproval = leaveConfig.getMaxApproval(oidEmployee);
    
    
    if(oidEmployee != 0) {
        try {
            emp = PstEmployee.fetchExc(oidEmployee);
        }
        catch(Exception e) {
            emp = new Employee();
        }
        
        try {
            long oidDiv = emp.getDivisionId();
            div = PstDivision.fetchExc(oidDiv);
        }
        catch(Exception e) {
            div = new Division();
        }
        
        try {
            long oidDept = emp.getDepartmentId();
            dept = PstDepartment.fetchExc(oidDept);
        }
        catch(Exception e) {
            dept = new Department();
        }
        
        try {
            long oidPos = emp.getPositionId();
            pos = PstPosition.fetchExc(oidPos);
        }
        catch(Exception e) {
            pos = new Position();
        }
    }
    
    int iErrCode = FRMMessage.ERR_NONE;
    String errMsg = "";

    ControlLine ctrLine = new ControlLine();
        
    CtrlSpecialLeave ctrlSpecialLeave = new CtrlSpecialLeave(request);
    CtrlSpecialLeaveStock ctrlSpecialLeaveStock = new CtrlSpecialLeaveStock(request);
    
       
    /*
     * menyimpan data leave 
     *   
     */  

    if(iCommand == Command.SAVE) {
              
        int idxApprove = 0;
        
        try{
            
            if(isApprove1){
                idxApprove = 1;
                isApprove1 = false;
            }else if(isApprove2) {
                idxApprove = 2;
                isApprove2 = false;
            }else if(isApprove3) {
                idxApprove = 3;
                isApprove3 = false;
            }
        }catch(Exception ex){}
      
        // jika belum disimpan atau approval valid
        if(idxApprove != 0 || isSaved == 0) {
            iErrCode = ctrlSpecialLeave.action(iCommand, oidLeave, maxApproval);
            
            System.out.println("Is this before ? " + oidLeave);
        
            specialLeave = ctrlSpecialLeave.getSpecialLeave();
            oidLeave = specialLeave.getOID();
            
            System.out.println("Is this after ? " + oidLeave);
            System.out.println("idxApprove ? " + idxApprove);
            System.out.println("isSaved ? " + isSaved);

            if(iErrCode == FRMMessage.NONE)
                isSaved = 1;


            /*
             * memproses leave detail
             *   
             */ 
                 
            if(leaveStatus != null) {
                
                for(int j=0; j<leaveStatus.length; j++) {
                    
                    // cek apakah akan diproses
                    if(leaveStatus[j].equals("1")) {
                        long symbolId = Long.parseLong(symbolIds[j]);
                        long categId = getCategoryId(specialLeaveIds, specialLeaveCats, symbolId);
                        Date dateStart = new Date(Long.parseLong(dateStarts[j]));
                        Date dateEnd = new Date(Long.parseLong(dateEnds[j]));
                        
                        SpecialLeaveStock leaveStock = new SpecialLeaveStock();
                        
                        leaveStock.setSpecialLeaveId(oidLeave);
                        leaveStock.setEmployeeId(oidEmployee);
                        leaveStock.setSymbolId(symbolId);   
                        leaveStock.setTakenQty(1);
                        
                        
                        sessLeave.saveLeaveStock(dateStart, dateEnd, leaveStock, iCommand, symbolId, oidEmployee, categId);
                        
                    } // end process status check
                                        
                } // end leave status iteration
                
            } // end leave status check
       
        }
    }

    /*
     * menghapus data leave 
     *   
     */  
    
    if(iCommand == Command.DELETE){
        try {
            long oid = PstSpecialLeave.deleteExc(oidLeave);
            
            if(oid == oidLeave){
                
                // hapus detail
                String where = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SPECIAL_LEAVE_ID] + " = " + oid;                
                Vector listDetail = PstSpecialLeaveStock.list(0, 0, where, "");
                
                if(listDetail != null) {      
                    
                    Hashtable hSysLeaveAL = new Hashtable();
                    hSysLeaveAL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE);
                
                    for(int i=0; i<listDetail.size(); i++) {   
                        try {
                            SpecialLeaveStock leaveStock = (SpecialLeaveStock)listDetail.get(i);

                            //delete AL Management 
                            if(hSysLeaveAL.containsKey(""+leaveStock.getSymbolId())){
                                String whr = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + "=" +
                                               leaveStock.getEmployeeId() + " AND " +
                                               PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + "= 0 AND " +
                                               PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + "= 1 AND " +
                                               PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + "= '" +
                                               Formater.formatDate(leaveStock.getTakenDate(), "yyyy-MM-dd") + "' ";
                                
                                Vector vctAL = PstAlStockManagement.list(0, 0, whr, "");                                
                                long id = 0;
                                
                                if(vctAL != null && vctAL.size()>0) {
                                    AlStockManagement alStock = (AlStockManagement)vctAL.firstElement();
                                    id = alStock.getOID();
                                }

                                // remove AL management
                                try {
                                    PstAlStockManagement.deleteExc(id);
                                }
                                catch(Exception e) {}
                            }

                            PstSpecialLeaveStock.deleteExc(leaveStock.getOID());
                        }
                        catch(Exception e) {}
                    }
                    
                }
                
            
                %>
                    <jsp:forward page="../attendance/empschedule_list.jsp">
                    <jsp:param name="start" value="<%=start%>" />
                    <jsp:param name="iCommand" value="<%=Command.LIST%>" />        
                    </jsp:forward>
                <%
            }
        }
        catch(Exception ex) {
            System.out.println("Failed deleting special leave record!");
        }
    }
    
    Vector vctStrDetail = new Vector();
    
    // menyimpan ke session
    /*Vector vctSession = new Vector();
    vctSession.add(specialLeave);
    vctSession.add(vctStrDetail);
    vctSession.add(""+maxApproval);
    
    session.putValue("LEAVE_APPLICATION", vctSession);*/
%>

<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->

<head>    

<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Application Request</title>
<script language="JavaScript">
</script>
<!-- #EndEditable -->

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 

<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->

<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #EndEditable -->

<!-- #BeginEditable "headerscript" --> 
<script language="JavaScript">

//-------------- script control line -------------------

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
         
         
         
//-------------- script control date popup -------------------

function getThn(){
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_REQUEST_DATE])%>
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL_DATE])%>
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL2_DATE])%>
    <%=ControlDatePopup.writeDateCaller("frm_leave_application",FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL3_DATE])%>
}

function hideObjectForDate(index){
}

function showObjectForDate(){
} 

function cmdSearchEmp(){
            window.open("empleavesearch.jsp?emp_number=" + document.frm_leave.EMP_NUMBER.value + "&emp_fullname=" + document.frm_leave.EMP_FULLNAME.value + "&emp_department=" + document.frm_leave.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
	}

//------------------- main processing ------------------------

function cmdCancel()
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.CANCEL)%>";
	document.frm_leave_application.action="leave_request.jsp";
	document.frm_leave_application.submit();
} 

function cmdEdit(oid)
{ 
	document.frm_leave_application.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frm_leave_application.action="leave_request.jsp";
	document.frm_leave_application.submit(); 
} 

function cmdSave()
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.SAVE)%>"; 
	document.frm_leave_application.action="leave_request.jsp";
	document.frm_leave_application.submit();
}

function cmdAsk(oid)
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.ASK)%>"; 
	document.frm_leave_application.action="leave_request.jsp";
	document.frm_leave_application.submit();
} 

function cmdConfirmDelete(oid)
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frm_leave_application.action="leave_request.jsp"; 
	document.frm_leave_application.submit();
}  

function cmdBack()
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.FIRST)%>"; 
	document.frm_leave_application.action="<%=approot%>/employee/attendance/empschedule_list.jsp";
	document.frm_leave_application.submit();
}


//------------------- utility functions ------------------------

function cmdEditDates(oidEmployee, oidLeave, oidSymbol, oidCategory, eligibleDay){

        window.open("leave_request_edit.jsp?employee_id=" + oidEmployee + "&leave_id=" + oidLeave + "&symbol_id=" + oidSymbol + "&category_id=" + oidCategory + "&day=" + eligibleDay, 
                     null, "height=300,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
        
}   

function checkApproval(index)
{
	var empLoggedIn = "<%=String.valueOf(emp.getOID())%>";
	var empApprovalSelected = 0;
        if(index==1){
            empApprovalSelected = document.frm_leave_application.<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL_ID]%>.value;
        }
        if(index==2){
            empApprovalSelected = document.frm_leave_application.<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL2_ID]%>.value;
        }
        if(index==3){
            empApprovalSelected = document.frm_leave_application.<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL3_ID]%>.value;
        }
	if(empLoggedIn != 0)
	{
		if(empApprovalSelected != 0)
		{
			if(empLoggedIn != empApprovalSelected)
			{
				document.frm_leave_application.command.value="<%=String.valueOf(Command.LIST)%>";
				document.frm_leave_application.indexApproval.value=index;
				document.frm_leave_application.oidDevHead.value=empApprovalSelected;
				document.frm_leave_application.action="leave_application_app_login_leave.jsp";
				document.frm_leave_application.submit();  		
			}else{
                          /*  if(index==1){
                                document.frm_leave_application.isApprove1.value = "true";
                            }   		
                            if(index==2){
                                document.frm_leave_application.isApprove2.value = "true";
                            }   		
                            if(index==3){
                                document.frm_leave_application.isApprove3.value = "true";
                            }   */	
                        }
		}
		else
		{
			alert('Please choose an authorized manager to approve this DP Application ...');    					
		}
	}
	else
	{
            alert('You should login into Harisma as an authorized user ...');
            if(index==1){
		document.frm_leave_application.<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL_ID]%>.value = "0";
            }   		
            if(index==2){
		document.frm_leave_application.<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL2_ID]%>.value = "0";
            }   		
            if(index==3){
		document.frm_leave_application.<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL3_ID]%>.value = "0";
            }   		
	}
}

function cmdPrint()
{ 
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveRequestPdf";
    window.open(pathUrl);
}

function reloadPage() {
    if(document.frm_leave_application.command.value == '<%= "" + Command.BACK %>') {
        document.frm_leave_application.command.value = '<%= "" + Command.NONE %>'       
        cmdSave();
    }
}

</script>
<!-- #EndEditable -->

</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')" onfocus="javascript:reloadPage()">
<!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
<tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
</tr> 
<tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
      <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> 
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
<tr> 
    <td width="88%" valign="top" align="left"> 
    <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
    <tr> 
        <td width="100%">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">   
        <tr> 
            <td height="20"> 
                <!-- #BeginEditable "contenttitle" --> 
                <font color="#FF6600" face="Arial"><strong> 
                Employee &gt; Leave Management &gt; Leave Application Request
                </strong></font> 
                <!-- #EndEditable --> 
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
                        <td valign="top">
                            
                        <!-- #BeginEditable "content" -->
                        <form name="frm_leave_application" method="post" action="">
                            
                            <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                            <input type="hidden" name="saved" value="<%=String.valueOf(isSaved)%>">
                            <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                            <input type="hidden" name="oid_leave" value="<%=String.valueOf(oidLeave)%>">                           
                            <input type="hidden" name="oid_employee" value="<%=String.valueOf(oidEmployee)%>">     
                            <input type="hidden" name="oid_period" value="<%=String.valueOf(oidPeriod)%>">
                            <input type="hidden" name="<%= FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_SPECIAL_LEAVE_ID] %>" value="<%= String.valueOf(specialLeave.getOID()) %>">
                            <input type="hidden" name="<%= FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_EMPLOYEE_ID] %>" value="<%= String.valueOf(specialLeave.getEmployeeId()) %>">                            
                            
                            <input type="hidden" name="approvedId1" value="<%=String.valueOf(approvedId1)%>">
                            <input type="hidden" name="approvedId2" value="<%=String.valueOf(approvedId2)%>">
                            <input type="hidden" name="approvedId3" value="<%=String.valueOf(approvedId3)%>">
                            <input type="hidden" name="isApprove1" value="<%=String.valueOf(isApprove1)%>">
                            <input type="hidden" name="isApprove2" value="<%=String.valueOf(isApprove2)%>">
                            <input type="hidden" name="isApprove3" value="<%=String.valueOf(isApprove3)%>">
                            <input type="hidden" name="indexApproval" value="1">
                            <input type="hidden" name="oidDevHead" value="0">
                            
                            <div align="center">
                                <h2>LEAVE REQUEST</h2>
                            </div>                            
                            <table width="99%" align="center">
                            <tr>
                                <td>
                                <table width="100%">
                                <tr>
                                    <td colspan="5">&nbsp;</td>                                
                                <tr>
                                <tr>
                                <td width="14%">Name</td>
                                <td width="3%">:</td>
                                <td width="35%">
                                    <strong><%=emp.getFullName()%></strong>                                    
                                </td>
                                <td width="15%">&nbsp;</td>
                                <td width="3%">&nbsp;</td>
                                <td width="30%">&nbsp;</td>
                            <tr>
                            <tr>
                                <td>Position</td>
                                <td>:</td>
                                <td>
                                    <strong><%= pos.getPosition() %></strong>
                                </td>
                                <td>I.D. No.</td>
                                <td>:</td>
                                <td>
                                    <strong><%= emp.getEmployeeNum() %></strong>
                                </td>
                            <tr>
                            <tr>
                                <td><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                <td>:</td>
                                <td>
                                    <strong><%= div.getDivision() %></strong>
                                </td>
                                <td><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                <td>:</td>
                                <td>
                                    <strong><%= dept.getDepartment() %></strong>
                                </td>
                            <tr>
                            <tr>
                                <td>Commencing Date</td>
                                <td>:</td>
                                <td>
                                    <strong>
                                    <%= emp.getCommencingDate() == null ? "-" : Formater.formatDate(emp.getCommencingDate(), "MMMM dd, yyyy") %>
                                    </strong>
                                </td>                              
                                <td>Date of Request</td>
                                <td>:</td>
                                <td>
                                    <strong>
                                    <%=ControlDatePopup.writeDate(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_REQUEST_DATE],(specialLeave.getRequestDate() == null ? new Date() : specialLeave.getRequestDate()))%>
                                    </strong>
                                </td>
                            <tr>
                            <tr>
                                <td colspan="5">&nbsp;</td>                                
                            <tr>
                             <tr>
                                 <td>Name</td>
                                 <td>:</td>
                                 <td><%=dataemployee.getFullName() %></td>
                                 <td>I.D.No</td>
                                 <td>:</td>
                                 <td><%=dataemployee.getEmployeeNum() %></td>
                             </tr>  
                             <% 
                             Department depart = new Department();
                             if(dataemployee.getDepartmentId() != 0){
                                 String wheredept = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+dataemployee.getDepartmentId();
                                 Vector vDepartment = PstDepartment.list(1, 1, wheredept, null);
                                 depart = (Department)vDepartment.get(0);
                             }
                             %>
                             <tr>
                                 <td>Position</td>
                                 <td>:</td>
                                 <td><%=dataemployee.getFullName() %></td>
                                 <td><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                 <td>:</td>
                                 <td><%=depart.getDepartment() %></td>
                             </tr
                             <%
                             Division objDivision = new Division();
                             if(dataemployee.getDivisionId()!=0){
                                 String whereDivision = PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" = "+dataemployee.getDivisionId();
                                 Vector vPstDivision = PstDivision.list(1, 1, whereDivision, null);
                                 
                                 objDivision = (Division)vPstDivision.get(0);                                 
                             }                             
                             %>
                             <tr>
                                 <td>Devision</td>
                                 <td>:</td>
                                 <td><%=objDivision.getDivision() %></td>
                                 <td>Date of Request</td>
                                 <td>:</td>
                                 <td><%=dataemployee.getEmployeeNum() %></td>
                             </tr>
                             <tr>
                                 <td>
                                    <table width="200px" align="left">
                                        <tr>
                                            <td>Leave From</td>
                                            <td>
                                            <%=ControlDatePopup.writeDate(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_REQUEST_DATE],(specialLeave.getRequestDate() == null ? new Date() : specialLeave.getRequestDate()))%>   
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Leave To</td>
                                            <td>
                                            <%=ControlDatePopup.writeDate(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_REQUEST_DATE],(specialLeave.getRequestDate() == null ? new Date() : specialLeave.getRequestDate()))%>      
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td>Type of leave</td>
                                            <td></td>
                                        </tr>
                                    </table>                                     
                                 </td>
                             </tr>
                            </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            <% Vector res = drawList(specialLeaveIds, specialLeaveNames, specialLeaveCats, 
                                                     specialLeaveReqQty, specialLeaveReqDate, oidEmployee ,oidLeave,
                                                     (isSaved == 0 ? false : true), (specialLeave.getApprovalId() == 0 ? false : true));
                            
                               vctStrDetail = (Vector)res.get(1);
                            %>
                            <%= res.get(0) %>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            <table width="100%">
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3"><b>Unpaid Leave</b><td>
                            </tr>
                            <tr>
                                <td width="20%">Reason for unpaid leave</td>
                                <td width="5%">:</td>
                                <td width="75%">
                                    <input type="text" name="<%= FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_UNPAID_REASON] %>" value="<%= specialLeave.getUnpaidLeaveReason() %>" size="50">
                                </td>
                            </tr>
                            <tr valign="top">
                                <td>Other Remarks</td>
                                <td>:</td>
                                <td>
                                    <textarea cols="35" name="<%= FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_REMARK] %>"><%= specialLeave.getOtherRemarks() %></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            </table>
                            </td>
                        </tr>                       
                      
                        <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" class="tablecolor" cellpadding="1" cellspacing="1">
                                <tr> 
                                  <td valign="top"> 
                                    <table width="100%" border="0" bgcolor="#F9FCFF">
                                      <tr>
                                                <td width="15%" align="left"><b>Approval</b></td >
                                                <td width="2%" ></td>
                                                <td width="60%" align="center"><b>Signature</b></td>
                                                <td width="23%" align="center"><b>Date</b></td>
                                            </tr>
                                      <!--Employee-->
                                      <tr>
                                          <td width="15%" >Employee</td >
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b><%=emp.getFullName()%></b></td>
                                          <td width="23%" ><b>
                                              <%=Formater.formatDate(specialLeave.getRequestDate(), "MMMM dd, yyyy")%>
                                          </b></td>
                                      </tr>
                                      <!--Dept Head-->
                                      <%
                                      if(specialLeave.getOID()>0){
                                      %>
                                      <tr>
                                          <td width="15%" >Div./Department Head</td >
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b>
                                              <%if(specialLeave.getApprovalId()>0){
                                                  String strApp = "";
                                                  Employee employee = new Employee();
                                                  try{
                                                      employee = PstEmployee.fetchExc(specialLeave.getApprovalId());
                                                      strApp = employee.getFullName();
                                                  }catch(Exception ex){}
                                              %>
                                                  <%=strApp%>
                                                  <input type="hidden" name="<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL_ID]%>" value="<%=String.valueOf(specialLeave.getApprovalId())%>">
                                              <%}else{%>
                                              <%												  
                                                  if(specialLeave.getOID()!=0)
                                                  {
                                                          Vector divHeadKey = new Vector(1,1);
                                                          Vector divHeadValue = new Vector(1,1);

                                                          divHeadKey.add("Select Department Head");
                                                          divHeadValue.add("0");

                                                          String selectedApproval = ""+specialLeave.getApprovalId();
                                                          if(approvedId1>0){
                                                            selectedApproval = ""+approvedId1;
                                                          }

                                                          Vector vectPositionLvl1 = new Vector(1,1);
                                                          vectPositionLvl1.add(""+PstPosition.LEVEL_SECRETARY);
                                                          vectPositionLvl1.add(""+PstPosition.LEVEL_SUPERVISOR);
                                                          vectPositionLvl1.add(""+PstPosition.LEVEL_MANAGER);   
                                                          vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           

                                                          Vector listDivHead = SessEmployee.listEmployeeByPositionLevel(emp, vectPositionLvl1);
                                                          for(int i=0; i<listDivHead.size(); i++)
                                                          {
                                                                  Employee objEmp = (Employee)listDivHead.get(i);

                                                                  if(emp.getOID() != objEmp.getOID())
                                                                  {
                                                                          divHeadKey.add(objEmp.getFullName());
                                                                          divHeadValue.add(""+objEmp.getOID());
                                                                  }
                                                          }
                                                          String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(1)\"";
                                                          out.println(ControlCombo.draw(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL_ID], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                  }
                                              }		
                                              
                                               // menyimpan ke session
                                                Vector vctSession = new Vector();
                                                vctSession.add(specialLeave);
                                                vctSession.add(vctStrDetail);
                                                vctSession.add(""+maxApproval);

                                                session.putValue("LEAVE_APPLICATION", vctSession);							  		  
                                          %>

                                          </b></td>
                                          <td width="23%" ><b>
                                                <%=ControlDatePopup.writeDate(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL_DATE],(specialLeave.getApprovalDate()==null ? new Date() : specialLeave.getApprovalDate()))%>
                                              <%--if(specialLeave.getApprovalId()>0){%>
                                              <%}--%>
                                        </b></td>
                                      </tr>

                                      <%}%>
                                      <!--Exc Prod-->
                                      <%
                                      if(specialLeave.getApprovalId()>0 && maxApproval >= 2){
                                      %>
                                      <tr>
                                          <td width="15%" >Executive Producer</td >
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b>
                                          <% 
                                              if(specialLeave.getApproval2Id()>0)
                                              {
                                                  String strApp = "";
                                                  Employee employee = new Employee();
                                                  try{
                                                      employee = PstEmployee.fetchExc(specialLeave.getApproval2Id());
                                                      strApp = employee.getFullName();
                                                  }catch(Exception ex){}
                                              %>
                                                  <%=strApp%>
                                                  <input type="hidden" name="<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL2_ID]%>" value="<%=String.valueOf(specialLeave.getApproval2Id())%>">
                                              <%}else{%>
                                              <%												  
                                                  if(specialLeave.getOID()>0 && specialLeave.getApprovalId()>0)
                                                      {
                                                              Vector divHeadKey = new Vector(1,1);
                                                              Vector divHeadValue = new Vector(1,1);

                                                              divHeadKey.add("Select Executive Producer");
                                                              divHeadValue.add("0");

                                                              String selectedApproval = ""+specialLeave.getApproval2Id();
                                                              if(approvedId2>0){
                                                                selectedApproval = ""+approvedId2;
                                                              }

                                                              Vector vectPositionLvl1 = new Vector(1,1);
                                                             // vectPositionLvl1.add(""+PstPosition.LEVEL_SECRETARY);
                                                              vectPositionLvl1.add(""+PstPosition.LEVEL_SUPERVISOR);
                                                              vectPositionLvl1.add(""+PstPosition.LEVEL_MANAGER);   
                                                              vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           

                                                              Vector listDivHead = SessEmployee.listEmployeeByPositionLevel(emp, vectPositionLvl1);
                                                              for(int i=0; i<listDivHead.size(); i++)
                                                              {
                                                                      Employee objEmp = (Employee)listDivHead.get(i);

                                                                      if(emp.getOID() != objEmp.getOID())
                                                                      {
                                                                              divHeadKey.add(objEmp.getFullName());
                                                                              divHeadValue.add(""+objEmp.getOID());
                                                                      }
                                                              }
                                                              String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(2)\"";
                                                              out.println(ControlCombo.draw(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL2_ID], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                      }					
                                              }											  		  
                                          %>

                                          </b></td>
                                          <td width="23%" ><b>
                                               <%=ControlDatePopup.writeDate(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL2_DATE],(specialLeave.getApproval2Date()==null ? new Date() : specialLeave.getApproval2Date()))%>
                                              <%--if(specialLeave.getApproval2Id()>0){%>
                                              <%}--%>
                                          </b></td>
                                      </tr>
                                      <%}%>

                                      <!--Prod Talent-->
                                      <%
                                      if(specialLeave.getApproval2Id()>0 && maxApproval >= 3){
                                      %>
                                      <tr>
                                          <td width="15%" >Producer-Talent</td >
                                          <td width="2%" >:</td>
                                          <td width="60%" ><b>
                                              <%if(specialLeave.getApproval3Id()>0){
                                                  String strApp = "";
                                                  Employee employee = new Employee();
                                                  try{
                                                      employee = PstEmployee.fetchExc(specialLeave.getApproval3Id());
                                                      strApp = employee.getFullName();
                                                  }catch(Exception ex){}
                                              %>
                                                          <%=strApp%>
                                                  <input type="hidden" name="<%=FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL3_ID]%>" value="<%=String.valueOf(specialLeave.getApproval3Id())%>">
                                                      <%}else{%>
                                                      <%												  
                                                         if(specialLeave.getOID()>0 && specialLeave.getApproval2Id()>0)
                                                              {
                                                                      Vector divHeadKey = new Vector(1,1);
                                                                      Vector divHeadValue = new Vector(1,1);

                                                                      divHeadKey.add("Select Producer - Talent");
                                                                      divHeadValue.add("0");

                                                                      String selectedApproval = ""+specialLeave.getApproval3Id();
                                                                      if(approvedId3>0){
                                                                        selectedApproval = ""+approvedId3;
                                                                      }

                                                                      Vector vectPositionLvl1 = new Vector(1,1);
                                                                     // vectPositionLvl1.add(""+PstPosition.LEVEL_SECRETARY);
                                                                      vectPositionLvl1.add(""+PstPosition.LEVEL_SUPERVISOR);
                                                                      vectPositionLvl1.add(""+PstPosition.LEVEL_MANAGER);   
                                                                      vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           

                                                                      Vector listDivHead = SessEmployee.listEmployeeByPositionLevel(emp, vectPositionLvl1);
                                                                      for(int i=0; i<listDivHead.size(); i++)
                                                                      {
                                                                              Employee objEmp = (Employee)listDivHead.get(i);

                                                                              if(emp.getOID() != objEmp.getOID())
                                                                              {
                                                                                      divHeadKey.add(objEmp.getFullName());
                                                                                      divHeadValue.add(""+objEmp.getOID());
                                                                              }
                                                                      }
                                                                      String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(3)\"";
                                                                      out.println(ControlCombo.draw(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL3_ID], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                              }
                                                      }												  		  
                                                  %>
                                          </b></td>
                                          <td width="23%" ><b>
                                                <%=ControlDatePopup.writeDate(FrmSpecialLeave.fieldNames[FrmSpecialLeave.FRM_FIELD_APPROVAL3_DATE],(specialLeave.getApproval3Date()==null ? new Date() : specialLeave.getApproval3Date()))%>
                                              <%--if(specialLeave.getApproval3Id()>0){--%>
                                              <%--}--%>
                                          </b></td>
                                      </tr>  
                                     <%} %>                                        
                                    </table>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        
                        <tr> 
                            <td>&nbsp;</td>
                        </tr>
                            <%if(specialLeave.getApproval3Id()>0){%>
                               <tr> 
                                <td>
                                <table><tr>
                                  <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print data"></a></td>
                                  <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                  <td height="22" valign="middle" colspan="3" width="951"> 
                                    <a href="javascript:cmdPrint()" class="command">Print</a> </td>
                                    </tr>
                                </table>
                                </td>
                                </tr>
                              <%}%>
                        <tr> 
                            <td> 
                            <%
                                ctrLine.setLocationImg(approot+"/images");
                                ctrLine.initDefault();
                                ctrLine.setTableWidth("80");
                                ctrLine.setCommandStyle("buttonlink");												

                                String scomDel = "javascript:cmdAsk('"+oidLeave+"')";
                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidLeave+"')";
                                String scancel = "javascript:cmdEdit('"+oidLeave+"')";

                                ctrLine.setAddCaption("");												
                                ctrLine.setBackCaption("Back to List Employee Schedule");
                                ctrLine.setConfirmDelCaption("Yes, Delete Leave Application");
                                ctrLine.setDeleteCaption("Delete Leave Application");
                                ctrLine.setSaveCaption("Save Leave Application"); 

                                if ((privDelete) && specialLeave.getApprovalId() == 0)
                                {
                                        ctrLine.setConfirmDelCommand(sconDelCom);
                                        ctrLine.setDeleteCommand(scomDel);
                                        ctrLine.setEditCommand(scancel);
                                }
                                else
                                {												 
                                        ctrLine.setConfirmDelCaption("");
                                        ctrLine.setDeleteCaption("");
                                        ctrLine.setEditCaption("");
                                }

                                if((!privAdd) && (!privUpdate))
                                {
                                        ctrLine.setSaveCaption("");
                                }

                                if (!privAdd)
                                {
                                        ctrLine.setAddCaption("");
                                }

                                if(specialLeave.getApproval3Id() != 0)
                                {
                                        ctrLine.setConfirmDelCaption("");
                                        ctrLine.setDeleteCaption("");
                                        ctrLine.setEditCaption("");
                                        ctrLine.setSaveCaption("");													
                                        ctrLine.setAddCaption("");													
                                }
                                
                                if(iCommand == Command.GOTO)
                                    iCommand = Command.EDIT;

                                out.println(ctrLine.drawImage(iCommand, iErrCode, errMsg));
                            %>
                            </td>
                        </tr>
                        </table>
                        
                        </form>
                        <!-- #EndEditable -->
                        
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
    <td colspan="2" height="20" <%=bgFooterLama%>> 
      <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> 
    </td>
</tr>
</table>

</body>

<!-- #BeginEditable "script" -->
<script language="JavaScript">

</script>
<!-- #EndEditable -->

<!-- #EndTemplate -->
</html>
