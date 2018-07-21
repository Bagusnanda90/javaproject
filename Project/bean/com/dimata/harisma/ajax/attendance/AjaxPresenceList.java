/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.attendance;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.gui.jsp.ControlDate;
import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstDefaultSchedule;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Company;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.HolidaysTable;
import com.dimata.harisma.entity.masterdata.PayrollGroup;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstPayrollGroup;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Reason;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.search.SrcPresence;
import com.dimata.harisma.form.attendance.CtrlEmpSchedule;
import com.dimata.harisma.form.attendance.CtrlPresence;
import com.dimata.harisma.form.attendance.FrmPresence;
import com.dimata.harisma.form.search.FrmSrcPresence;
import com.dimata.harisma.session.attendance.SessPresence;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author IPAG
 */
public class AjaxPresenceList extends HttpServlet {

    //DATATABLES
    private String searchTerm;
    private String colName;
    private int colOrder;
    private String dir;
    private int start;
    private int amount;
    
    //OBJECT
    private JSONObject jSONObject = new JSONObject();
    private JSONArray jSONArray = new JSONArray();
    
    //LONG
    private long oid = 0;
    private long oidReturn = 0;
    private long userId = 0;
    private long empId = 0;
    
    //STRING
    private String dataFor = "";
    private String oidDelete = "";
    private String approot = "";
    private String htmlReturn = "";
    private String empName = "";
    
    
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //LONG
	this.oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
	this.oidReturn=0;
        this.userId = FRMQueryString.requestLong(request, "userId");
        this.empId = FRMQueryString.requestLong(request, "empId");
	
	//STRING
	this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
	this.oidDelete = FRMQueryString.requestString(request, "FRM_FIELD_OID_DELETE");
	this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
	this.htmlReturn = "";
        this.empName = FRMQueryString.requestString(request, "empName");
	
	//INT
	this.iCommand = FRMQueryString.requestCommand(request);
	this.iErrCode = 0;
	
	
	
	//OBJECT
	this.jSONObject = new JSONObject();
	
	switch(this.iCommand){
	    case Command.SAVE :
		commandSave(request);
	    break;
		
	    case Command.LIST :
		commandList(request, response);
	    break;
		
	    case Command.DELETEALL : 
		commandDeleteAll(request);
	    break;
            
            case Command.DELETE :
                commandDelete(request);
            break;    
                
	    default : commandNone(request);
	}
	
	
	try{
	    
	    this.jSONObject.put("FRM_FIELD_HTML", this.htmlReturn);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
	
	response.getWriter().print(this.jSONObject);
        
    }
    
    public void commandNone(HttpServletRequest request){
	if(this.dataFor.equals("showPresenceListForm")){
	  this.htmlReturn = presenceForm();
	}
        if(this.dataFor.equals("showMultipleAttendance")){
            this.htmlReturn = multipleForm();
        }
        if(this.dataFor.equals ("showGenerateSchPh")){
            this.htmlReturn = generateForm();    
        }
        if(this.dataFor.equals ("getSection")){
            this.htmlReturn = sectionForm(request);    
        }
    }
    
    public void commandSave(HttpServletRequest request){
        if(this.dataFor.equals("showMultipleAttendance")){
                int iCommand = FRMQueryString.requestCommand(request);
                String massage ="";
                String empNum = FRMQueryString.requestString(request, "nama");
                Long companyId = FRMQueryString.requestLong(request, "company");
                Long divisionId = FRMQueryString.requestLong(request, "division");
                Long departmentId = FRMQueryString.requestLong(request, "department");
                Long sectionId = FRMQueryString.requestLong(request, "section");
                Long positionId = FRMQueryString.requestLong(request, "position");
                String StrdateFrom = FRMQueryString.requestString(request, "strdate");
                String StrdateTo = FRMQueryString.requestString(request, "enddate");
                Long payGroup = FRMQueryString.requestLong(request,"payroll");
                String note = FRMQueryString.requestString(request, "note");
                int reason_status = FRMQueryString.requestInt(request,"reason");
                int statusPresence = FRMQueryString.requestInt(request,"presence");
                int upTipe = FRMQueryString.requestInt(request,"upTipe");
                
                Vector listEmployee = new Vector();
                
                String whereClause = "1=1";
                if (!empNum.equals("")){
                    whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +  " = '" + empNum + "' ";
                }
                if (companyId !=0){
                    whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + "=" + companyId;
                }
                if (divisionId !=0){
                    whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + divisionId;
                }
                if (departmentId !=0){
                    whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + departmentId;
                }
                if (sectionId !=0){
                    whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + sectionId;
                }
                if (positionId !=0){
                    whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + positionId;
                }
                if (payGroup !=0){
                    whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_PAYROLL_GROUP] + "=" + payGroup;
                }

             listEmployee = PstEmployee.list(0, 0, whereClause, "");
             int i= 1;
                String dateFromStr = StrdateFrom+" 10:10";
                String dateToStr = StrdateTo+" 10:10";
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date dateTo = new Date();
                Date dateFrom = new Date();
                try {
                    dateTo = sdf.parse(dateFromStr);
                    dateFrom = sdf.parse(dateToStr);
                }catch(Exception ex) {}
               
             
              dateTo.setDate(dateTo.getDate()+1);
             do{
                 if (listEmployee.size() != 0){
                     for (int x=0; x < listEmployee.size(); x++){
                         Employee employee = (Employee) listEmployee.get(x);
                         long periodId = PstPeriod.getPeriodIdBySelectedDate(dateFrom);
                         int result = 1;

                         if ((upTipe == 1)|| (upTipe == 0) ){
                         String fieldstatus = PstEmpSchedule.fieldNames[(PstEmpSchedule.OFFSET_INDEX_STATUS+(dateFrom.getDate()-1))];
                         int nilaiStatus =  PstEmpSchedule.updatestatusnew(periodId, employee.getOID(), fieldstatus, statusPresence);
                         result=0;
                         }
                         if ((upTipe == 2)|| (upTipe == 0) ){
                         String fieldreason = PstEmpSchedule.fieldNames[(PstEmpSchedule.OFFSET_INDEX_REASON+(dateFrom.getDate()-1))];
                         int nilaiReason =  PstEmpSchedule.updatereasonnew(periodId, employee.getOID(), fieldreason, reason_status);
                         result=0;
                         }
                         String fieldnote = PstEmpSchedule.fieldNames[(PstEmpSchedule.OFFSET_INDEX_NOTE+(dateFrom.getDate()-1))];
                         int nilaiNote =  PstEmpSchedule.updatenotenew(periodId, employee.getOID(), fieldnote, note);
                         massage =  CtrlEmpSchedule.resultText[0][result];
                     }
                 }

                 dateFrom.setDate(dateFrom.getDate()+1);
             } while (dateFrom.before(dateTo));
            
          this.htmlReturn = "<i class='fa fa-info'></i> Generate Attendance Success";  
        }
        
        if(this.dataFor.equals("showPresenceListForm")){
	CtrlPresence ctrlPresence = new CtrlPresence(request);
	this.iErrCode = ctrlPresence.action(this.iCommand, this.oid ,request ,"",0, this.oidDelete);
	String message = ctrlPresence.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        }
        
        if(this.dataFor.equals("showGenerateSchPh")){
            String sOidPeriod = FRMQueryString.requestString(request, "period");
            long oidPeriod = 0;
    if (sOidPeriod != null && sOidPeriod.length() > 0) {
        try {
            oidPeriod = Long.parseLong(sOidPeriod);
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
    }
    }
    String msgString = "";
    Period period = new Period();
    if (oidPeriod != 0) {
                try {
                    period = PstPeriod.fetchExc(oidPeriod);//Vector listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(0, selectedDateFrom, selectedDateTo, 0, empNum, "", "", 0, 0);   
                    //Vector listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(0, selectedDateFrom, selectedDateTo, 0, empNum, "", "", 0, 0);
                } catch (DBException ex) {
                    Logger.getLogger(AjaxPresenceList.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
        if (oidPeriod != 0) {
                    try { int totalUpdateOrInsertedSchedule=0;
                        // generate schedule fo ron existing schedule in a periode for all employee having default schedule
                        int empTotal = PstEmployee.getCountEmployeeHaveDefaultSchedule(""); // list count of employee having default schedule
                        if (empTotal > 0) {
                            //Period period = PstPeriod.fetchExc(oidPeriod);// get periode and calendar , and start day                                                 
                            int start=0;
                            //update by satrya 2012-10-09
                            HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(period.getStartDate(), period.getEndDate());
                            long oidPublicHoliday = 0;         
                            try{
                                oidPublicHoliday = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
                            }catch(Exception ex){
                                System.out.println("Execption OID_PUBLIC_HOLIDAY: " + ex.toString());
                            }
                            long oidDayOff = 0;         
                            try{
                                oidDayOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
                            }catch(Exception ex){
                                System.out.println("Execption OID_DAY_OFF: " + ex.toString());
                            }
                            do{                                
                                Vector employeeList = PstEmployee.getEmployeeHaveDefaultSchedule(start, 50, "", ""); // loop per 50 employee
                                start = start +50;                                
                                if(employeeList!=null){                                
                                for(int idx=0; idx< employeeList.size();idx++){                                    
                                    Employee employee = (Employee) employeeList.get(idx);
                                    String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employee.getOID();
                                    String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
                                    Vector dftSchedules = PstDefaultSchedule.list(0, 7, whereClauseDS, orderDS);                                    
                                    if(dftSchedules!=null && dftSchedules.size()>0){
                                        EmpSchedule schedule = PstEmpSchedule.fecth(oidPeriod, employee.getOID());
                                        boolean updated=false;
                                        if(schedule==null){
                                            updated=true;
                                            schedule = new EmpSchedule();
                                            schedule.setEmployeeId(employee.getOID());
                                            schedule.setPeriodId(oidPeriod);                                            
                                        }
                                        GregorianCalendar gcStart = new GregorianCalendar(period.getStartDate().getYear(), period.getStartDate().getMonth(), period.getStartDate().getDate());                                        
                                        int sDayOfWeek = gcStart.get(GregorianCalendar.DAY_OF_WEEK);
                                        int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                                        int iDate=period.getStartDate().getDate();                                        
                                        int iDayWeek=period.getStartDate().getDay()+1; // Gregorion callendar Sunday = 1
                                        //update by satrya 2012-10-09
                                        Date selectedDate = (Date)period.getStartDate().clone(); // = new  Date(period.getStartDate().getTime());
                                        int countOfDay=0; // counter pengaman                                        
                                        do{ // loop through the calendar 
                                            if(schedule.getOID()!=0){ // for existing schedule 
    
                                                    if(holidaysTable.isHoliday(employee.getReligionId(), selectedDate)){
                                                        schedule.setD(iDate, oidPublicHoliday); //jika ada holiday religion
                                                         updated=true;
                                                    }
                                                
                                            }//end jika oid tidak ada
                                            
                                            iDayWeek = iDayWeek+1;
                                            if(iDayWeek>7){ // if day of week saturday then back to sunday =1
                                                iDayWeek =1; 
                                            }
                                            
                                            countOfDay=countOfDay+1;
                                            iDate = iDate+1; 
                                            selectedDate.setDate(selectedDate.getDate()+1);//otomatis akan melewati bulannya
                                            if(iDate>nDayOfMonthStart){ // jika tanggal di schedule sudah melewati tanggal maximum di bulan itu
                                                iDate=1;
                                            }
                                        } while(iDate!=(period.getEndDate().getDate()+1) && countOfDay < 31 );
                                        if(updated){
                                              // save schedule for an employee
                                            totalUpdateOrInsertedSchedule++;
                                             try{
                                                 if(schedule.getOID()!=0){
                                                    PstEmpSchedule.updateExc(schedule);
                                                 }else{
                                                    PstEmpSchedule.insertExc(schedule);
                                                 }                                                 
                                             }catch(Exception exc){
                                                 System.out.println(exc);
                                             }
                                        }
                                    }
                                }
                               }
                            }while(start < empTotal);
                        }                                          
                        msgString = "Generate schedule : Total Employee have default schedule="+  empTotal + " ; total updated schedule="+totalUpdateOrInsertedSchedule;
                        this.htmlReturn = "<i class='fa fa-info'></i> "+msgString;
                    } catch (Exception exc) {
                        msgString = exc.toString();
                        System.out.println(exc);
                    }
                }
                
    
        }
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	CtrlPresence ctrlPresence = new CtrlPresence(request);
	this.iErrCode = ctrlPresence.action(this.iCommand, this.oid ,request ,"",0, this.oidDelete);
	String message = ctrlPresence.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        CtrlPresence ctrlPresence = new CtrlPresence(request);
	this.iErrCode = ctrlPresence.action(this.iCommand, this.oid ,request ,"",0, this.oidDelete);
	String message = ctrlPresence.getMessage();
	this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listPresence") || this.dataFor.equals("listPresenceFilter")){
	    String[] cols = { PstPresence.fieldNames[PstPresence.FLD_PRESENCE_ID],
                PstPresence.fieldNames[PstPresence.FLD_PRESENCE_ID],
		PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID],
                PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME], 
                PstPresence.fieldNames[PstPresence.FLD_STATUS],
                PstPresence.fieldNames[PstPresence.FLD_ANALYZED],
                PstPresence.fieldNames[PstPresence.FLD_MANUAL_PRESENCE]
                };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
    }
    
    public JSONObject listDataTables (HttpServletRequest request, HttpServletResponse response, String[] cols, String dataFor, JSONObject result){
        this.searchTerm = FRMQueryString.requestString(request, "sSearch");
        if (this.searchTerm != null){
            dataFor = "listPresence";
        }
        int amount = 10;
        int start = 0;
        int col = 0;
        String dir = "asc";
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        SrcPresence srcPresence = new SrcPresence();
        SessPresence sessPresence = new SessPresence();
        
        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
            if (amount < 10) {
                amount = 10;
            }
        }
        if (sCol != null) {
            col = Integer.parseInt(sCol);
            if (col < 0 )
                col = 0;
        }
        if (sdir != null) {
            if (!sdir.equals("asc"))
            dir = "desc";
        }
        
	
        
        String whereClause = "";
        
        if(dataFor.equals("listPresence")){
	    whereClause += " (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR EMP." +PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+this.searchTerm+"%' "
                    + " OR PRESENCE."+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+" LIKE '%"+this.searchTerm+"%'";
                    
                    if (Presence.STATUS_ATT.length>1){
                        whereClause+= " OR ";
                        String close="";
                        for (int i=0; i < Presence.STATUS_ATT.length-1;i++){
                            whereClause += "IF(PRESENCE."+PstPresence.fieldNames[PstPresence.FLD_STATUS] + " = " + i
                                    + ",'"+Presence.STATUS_ATT[i]+"' ,";
                            close += ")";
                        }
                        whereClause += "'"+Presence.STATUS_ATT[Presence.STATUS_ATT.length-1]+"'"+close+" LIKE '%"+this.searchTerm+"%'";
                    }   
                    
                    if (Presence.ANALYZED_STATUS.length>1){
                        whereClause+= " OR ";
                        String close="";
                        for (int i=0; i < Presence.ANALYZED_STATUS.length-1;i++){
                            whereClause += "IF(PRESENCE."+PstPresence.fieldNames[PstPresence.FLD_ANALYZED] + " = " + i
                                    + ", '"+Presence.ANALYZED_STATUS[i]+"' ,";
                            close += ")";
                        }
                        whereClause += "'"+Presence.ANALYZED_STATUS[Presence.ANALYZED_STATUS.length-1]+"'"+close+" LIKE '%"+this.searchTerm+"%'";
                    }
                    whereClause += " OR IF(PRESENCE."+PstPresence.fieldNames[PstPresence.FLD_MANUAL_PRESENCE] + " = 1 , 'YES', 'NO') LIKE '%"+this.searchTerm+"%'"
                                + ")";
	} else if(dataFor.equals("listPresenceFilter")){
            

            FrmSrcPresence frmSrcPresence = new FrmSrcPresence(request,srcPresence);
            frmSrcPresence.requestEntityObject(srcPresence); 
            
            
        }
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listPresence")){
	    total = PstPresence.getCountJoin(whereClause);
	} else if(dataFor.equals("listPresenceFilter")){
            total = sessPresence.getCountSearch(srcPresence);
        }
        
        
        this.amount = amount;
       
        this.colName = colName;
        this.dir = dir;
        this.start = start;
        this.colOrder = col;
        
        try {
            result = getData(total, request, dataFor);
        } catch(Exception ex){
            System.out.println(ex);
        }
       
       return result;
    }
    
    public JSONObject getData(int total, HttpServletRequest request, String datafor){
        
        int totalAfterFilter = total;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        Presence presence = new Presence();
        Employee emp = new Employee();
        String whereClause = "";
        String order ="";
        SrcPresence srcPresence = new SrcPresence();
        SessPresence sessPresence = new SessPresence();
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(datafor.equals("listPresence")){
               whereClause += " (EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" LIKE '%"+this.searchTerm+"%'"
                    + " OR EMP." +PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+this.searchTerm+"%' "
                    + " OR PRESENCE."+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+" LIKE '%"+this.searchTerm+"%'";
                    
                    if (Presence.STATUS_ATT.length>1){
                        whereClause+= " OR ";
                        String close="";
                        for (int i=0; i < Presence.STATUS_ATT.length-1;i++){
                            whereClause += "IF(PRESENCE."+PstPresence.fieldNames[PstPresence.FLD_STATUS] + " = " + i
                                    + ",'"+Presence.STATUS_ATT[i]+"' ,";
                            close += ")";
                        }
                        whereClause += "'"+Presence.STATUS_ATT[Presence.STATUS_ATT.length-1]+"'"+close+" LIKE '%"+this.searchTerm+"%'";
                    }   
                    
                    if (Presence.ANALYZED_STATUS.length>1){
                        whereClause+= " OR ";
                        String close="";
                        for (int i=0; i < Presence.ANALYZED_STATUS.length-1;i++){
                            whereClause += "IF(PRESENCE."+PstPresence.fieldNames[PstPresence.FLD_ANALYZED] + " = " + i
                                    + ",'"+Presence.ANALYZED_STATUS[i]+"' ,";
                            close += ")";
                        }
                        whereClause += "'"+Presence.ANALYZED_STATUS[Presence.ANALYZED_STATUS.length-1]+"'"+close+" LIKE '%"+this.searchTerm+"%'";
                    }
                    whereClause += " OR IF(PRESENCE."+PstPresence.fieldNames[PstPresence.FLD_MANUAL_PRESENCE] + " = 1 , 'YES', 'NO') LIKE '%"+this.searchTerm+"%'"
                                + ")";
            }
        }
        if(datafor.equals("listPresenceFilter")){
            FrmSrcPresence frmSrcPresence = new FrmSrcPresence(request,srcPresence);
            frmSrcPresence.requestEntityObject(srcPresence); 
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listPresence")){
	    listData = PstPresence.listJoin(this.start, this.amount,whereClause,order);
	} else if(datafor.equals("listPresenceFilter")){
            listData = sessPresence.searchPresence(srcPresence, this.start, this.amount);
        }
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
            String str_dt_PresenceDatetime = "";
	    if(datafor.equals("listPresence")){
		presence = (Presence) listData.get(i);
                try {
                    emp = PstEmployee.fetchExc(presence.getEmployeeId());
                    Date dt_PresenceDatetime = presence.getPresenceDatetime();
                        
			if(dt_PresenceDatetime==null)
			{
				dt_PresenceDatetime = new Date();
			}
			
			//str_dt_PresenceDatetime = Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - hh mm");
			//str_dt_PresenceDatetime =  Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - ") + Formater.formatTimeLocale(dt_PresenceDatetime);
			str_dt_PresenceDatetime =  Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - HH:mm:ss");
                } catch (Exception exc){
                str_dt_PresenceDatetime = "";
                }
                String checkButton = "<input type='checkbox' name='"+FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"' class='"+FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"' value='"+presence.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+emp.getEmployeeNum());
                ja.put(""+emp.getFullName());
                ja.put(""+str_dt_PresenceDatetime);
		ja.put(""+presence.STATUS_ATT[presence.getStatus()]);
                ja.put(""+presence.ANALYZED_STATUS[presence.getAnalyzed()]);
                ja.put(""+Presence.NOYES[presence.getManualPresence()]);
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+presence.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                //String buttonFlow = "<button class='btn btn-warning btnflow btn-xs' data-oid='"+presence.getOID()+"' data-for='showFlowForm' type='button' data-toggle='tooltip' data-placement='top' title='Approval'><i class='fa fa-user'></i></button> ";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+presence.getOID()+"' data-for='showPresenceListForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+presence.getOID()+"' data-for='deleteDocMasterSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
		array.put(ja);
	    } else if(datafor.equals("listPresenceFilter")){
                Vector temp = (Vector) listData.get(i);
                presence = (Presence) temp.get(0);
		emp = (Employee) temp.get(1);
                try {
                    Date dt_PresenceDatetime = presence.getPresenceDatetime();
                        
			if(dt_PresenceDatetime==null)
			{
				dt_PresenceDatetime = new Date();
			}
			
			//str_dt_PresenceDatetime = Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - hh mm");
			//str_dt_PresenceDatetime =  Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - ") + Formater.formatTimeLocale(dt_PresenceDatetime);
			str_dt_PresenceDatetime =  Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - HH:mm:ss");
                } catch (Exception exc){
                str_dt_PresenceDatetime = "";
                }
                String checkButton = "<input type='checkbox' name='"+FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"' class='"+FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"' value='"+presence.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put(""+(this.start+i+1));
		ja.put(""+emp.getEmployeeNum());
                ja.put(""+emp.getFullName());
                ja.put(""+str_dt_PresenceDatetime);
		ja.put(""+presence.STATUS_ATT[presence.getStatus()]);
                ja.put(""+presence.ANALYZED_STATUS[presence.getAnalyzed()]);
                ja.put(""+Presence.NOYES[presence.getManualPresence()]);
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+presence.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                //String buttonFlow = "<button class='btn btn-warning btnflow btn-xs' data-oid='"+presence.getOID()+"' data-for='showFlowForm' type='button' data-toggle='tooltip' data-placement='top' title='Approval'><i class='fa fa-user'></i></button> ";
		if(true/*privUpdate*/){
		    buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+presence.getOID()+"' data-for='showPresenceListForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+presence.getOID()+"' data-for='deleteDocMasterSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
		array.put(ja);
            }
        }
        
        totalAfterFilter = total;
        
        try {
            result.put("iTotalRecords", total);
            result.put("iTotalDisplayRecords", totalAfterFilter);
            result.put("aaData", array);
        } catch (Exception e) {

        }
        
        return result;
    }
    
    
    
    public String presenceForm(){
	
	//CHECK DATA
	Presence presence = new Presence();
	if(this.oid != 0){
	    try{
		presence = PstPresence.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        Vector status_value = Presence.getStatusIndexString();
        Vector status_key = Presence.getStatusAttString();
        Vector listEmployee = PstEmployee.list(0, 0, "", "");
        
        
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm";
        Date startDate = presence.getPresenceDatetime() == null ? new Date() : presence.getPresenceDatetime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String strDtNow = "";
        String returnData = ""
          + "<div class='row'>"
	    + "<div class='col-md-12'>"
                 + "<input type='hidden' name='"+ FrmPresence.fieldNames[FrmPresence.FRM_FIELD_MANUAL_PRESENCE]+"'  id='"+ FrmPresence.fieldNames[FrmPresence.FRM_FIELD_MANUAL_PRESENCE]+"' class='form-control' value='"+presence.YES+"'>"
                + "<div class='form-group'>"
                            + "<label>Employee *</label>"
                                + "<select id='employee' name='"+ FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control chosen-select'>"
                                + "<option value='0'>Select Employee...</option>";
                                for (int x=0; x < listEmployee.size(); x++){
                                    Employee emp = (Employee)listEmployee.get(x);
                                    if (presence.getEmployeeId() == emp.getOID()){
                                    returnData += "<option value='"+emp.getOID()+"' selected>"+emp.getFullName()+"</option>";    
                                    } else {
                                    returnData += "<option value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
                                    }
                                }
                        returnData += "</select>"
                 + "</div>"
                                
                 + "<div class='form-group'>"
                            + "<label>Date Time</label>"
                            + "<input type='text' name='"+ FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME]+"'  id='"+ FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME]+"' class='form-control datetimepicker2' value='"+presence.getPresenceDatetime()+"'>"
                  + "</div>"
                                
                 + "<div class='form-group'>"
                            + "<label>Status</label>"
                            + ""+ControlCombo.draw(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS],"formElemen",null, String.valueOf(presence.getStatus()), status_value, status_key)+" "   
                 + "</div>"             
                                
            + "</div>"
	+ "</div>";
	return returnData;
    }
    
    public String multipleForm(){
	
	//CHECK DATA
	Presence presence = new Presence();
	if(this.oid != 0){
	    try{
		presence = PstPresence.fetchExc(this.oid);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	}
        Vector status_value = Presence.getStatusIndexString();
        Vector status_key = Presence.getStatusAttString();
        Vector listEmployee = PstEmployee.list(0, 0, "", "");
        Vector listCompany = PstCompany.list(0, 0, "", "");
        Vector listDivision = PstDivision.list(0, 0, "", "");
        Vector listDepartment = PstDepartment.list(0, 0, "", "");
        Vector listSection = PstSection.list(0, 0, "", "");
        Vector listPosition = PstPosition.list(0, 0, "", "");
        Vector listPayrollGroup = PstPayrollGroup.list(0, 0, "", "");
        Vector listPresenceStatus = PstEmpSchedule.list(0, 0, "", "");
        Vector  listReason = PstReason.list(0, 0, "", "REASON");
        
        
        
        
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm";
        Date startDate = presence.getPresenceDatetime() == null ? new Date() : presence.getPresenceDatetime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String strDtNow = sdf.format(startDate);
        String returnData = ""
          + "<div class='row'>"
	    + "<div class='col-md-12'>"
                + "<div class='form-group'>"
                            + "<label>Employee </label>"
                                + "<select id='nama' name='nama' class='form-control chosen-select'>"
                                + "<option value='0'>Select Employee...</option>";
                                for (int x=0; x < listEmployee.size(); x++){
                                    Employee emp = (Employee)listEmployee.get(x);
                                    returnData += "<option value='"+emp.getOID()+"'>"+emp.getFullName()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"
                                
                 + "<div class='form-group'>"
                            + "<label>Company</label>"
                                + "<select id='company' name='company' class='form-control chosen-select'>"
                                + "<option value='0'>Select Company...</option>";
                                for (int x=0; x < listCompany.size(); x++){
                                    Company com = (Company)listCompany.get(x);
                                    returnData += "<option value='"+com.getOID()+"'>"+com.getCompany()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"  
                 
                 + "<div class='form-group'>"
                            + "<label>Division</label>"
                                + "<select id='division' name='division' class='form-control chosen-select'>"
                                + "<option value='0'>Select Division...</option>";
                                for (int x=0; x < listDivision.size(); x++){
                                    Division div = (Division)listDivision.get(x);
                                    returnData += "<option value='"+div.getOID()+"'>"+div.getDivision()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"
                                
                 + "<div class='form-group'>"
                            + "<label>Department</label>"
                                + "<select id='department' name='department' class='form-control chosen-select'>"
                                + "<option value='0'>Select Department...</option>";
                                for (int x=0; x < listDepartment.size(); x++){
                                    Department dept = (Department)listDepartment.get(x);
                                    returnData += "<option value='"+dept.getOID()+"'>"+dept.getDepartment()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"
                                
                 + "<div class='form-group'>"
                            + "<label>Section</label>"
                                + "<select id='section' name='section' class='form-control chosen-select'>"
                                + "<option value='0'>Select Section...</option>";
                                for (int x=0; x < listSection.size(); x++){
                                    Section section = (Section)listSection.get(x);
                                    returnData += "<option value='"+section.getOID()+"'>"+section.getSection()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"   
                                
                 + "<div class='form-group'>"
                            + "<label>Position</label>"
                                + "<select id='position' name='position' class='form-control chosen-select'>"
                                + "<option value='0'>Select Position...</option>";
                                for (int x=0; x < listPosition.size(); x++){
                                    Position position = (Position)listPosition.get(x);
                                    returnData += "<option value='"+position.getOID()+"'>"+position.getPosition()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"
                                
                 + "<div class='form-group'>"
                            + "<label>Payroll Group</label>"
                                + "<select id='payroll' name='payroll' class='form-control chosen-select'>"
                                + "<option value='0'>Select Payroll...</option>";
                                for (int x=0; x < listPayrollGroup.size(); x++){
                                    PayrollGroup payrollGroup = (PayrollGroup)listPayrollGroup.get(x);
                                    returnData += "<option value='"+payrollGroup.getOID()+"'>"+payrollGroup.getPayrollGroupName()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"
                                
                 + "<div class='form-group'>"
                            + "<label>Presence Status</label>"
                                + "<select id='presence' name='presence' class='form-control chosen-select'>"
                                + "<option value='0'>Select Presence...</option>";
                                for (int s = 0; s < PstEmpSchedule.strPresenceStatus.length; s++) {
                                returnData += "<option value='"+s+"'>"+PstEmpSchedule.strPresenceStatus[s]+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"    
                                
                 + "<div class='form-group'>"
                            + "<label>Reason</label>"
                                + "<select id='reason' name='reason' class='form-control chosen-select'>"
                                + "<option value='0'>Select Reason...</option>";
                                for (int x=0; x < listReason.size(); x++){
                                    Reason reason = (Reason)listReason.get(x);
                                    returnData += "<option value='"+reason.getOID()+"'>"+reason.getKodeReason()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>"              
                 
                 + "<div class='form-group'>"
                            + "<label>Presence Note</label>"
                            + "<textarea name='note' id='note' class='form-control'></textarea>"
                 + "</div>"               
                                
                 + "<div class='form-group'>"
		    + "<label>Start Date & End Date</label>"
		    + "<div class='input-group'>"
			+ "<input type='text' name='strdate'  id='strdate' class='form-control datepicker' value=''>"
			+ "<div class='input-group-addon'>"
			    + "To"
			+ "</div>"
			+ "<input type='text' name='enddate'  id='enddate' class='form-control datepicker' value=''>"
		    + "</div>"
                + "</div>"
                             
                + "<div class='form-group'>"
                            + "<label>Update Type</label>"
                                + "<select id='upTipe' name='upTipe' class='form-control chosen-select'>"
                                + "<option value='1'>reason and status</option>";
                                returnData += "<option value='2'>only status</option>";
                                returnData += "<option value='3'>only reason</option>";
                        returnData += "</select>"
                 + "</div>"
                                
            + "</div>"
	+ "</div>";
	return returnData;
    }
    
    public String generateForm(){
        Vector  listPeriod = PstPeriod.list(0, 0, "", "");
    
    String returnData = ""  
       + "<div class='form-group'>"
                            + "<label>Genetrate PH To Period</label>"
                                + "<select id='period' name='period' class='form-control chosen-select'>"
                                + "<option value='0'>Select Period...</option>";
                                for (int x=0; x < listPeriod.size(); x++){
                                    Period period = (Period)listPeriod.get(x);
                                    returnData += "<option value='"+period.getOID()+"'>"+period.getPeriod()+"</option>";
                                }
                        returnData += "</select>"
                 + "</div>";        
    return returnData;    
    }
    
    public String sectionForm(HttpServletRequest request){
        String returnData ="";
        long oidDepartment = FRMQueryString.requestLong(request, "department");
        
        Vector listSection = PstSection.list(0, 0, PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment, "");
        Vector sec_value = new Vector(1,1);
        Vector sec_key = new Vector(1,1);                                                            
        for (int i = 0; i < listSection.size(); i++) {
                Section sec = (Section) listSection.get(i);
                sec_key.add(sec.getSection());
                sec_value.add(String.valueOf(sec.getOID()));
        }
            returnData += ControlCombo.draw(FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_SECTION],"form-control chosen-select","Select Section...", "", sec_value, sec_key, "id='section'");
        return returnData;
    }

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

}
