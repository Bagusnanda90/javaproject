/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.ajax.attendance;

/* java package */
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.attendance.ScheduleD1D2;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleCategory;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.search.SrcEmpSchedule;
import com.dimata.harisma.form.attendance.CtrlEmpSchedule;
import com.dimata.harisma.form.attendance.FrmEmpSchedule;
import com.dimata.harisma.form.search.FrmSrcEmpSchedule;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.system.entity.system.SystemProperty;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

/**
 *
 * @author Gunadi
 */
public class AjaxEmpWorkingSchedule extends HttpServlet {

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
    private long loginPositionId = 0;
    private long oidPeriod = 0;
    
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
        this.loginPositionId = FRMQueryString.requestLong(request, "login_position_id");
        this.oidPeriod = FRMQueryString.requestLong(request, "oid_period");
	
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
        if(this.dataFor.equals("editSchedule")){
            this.htmlReturn = workingScheduleForm(request);
        } else if(this.dataFor.equals("addSchedule")){
            this.htmlReturn = addWorkingScheduleForm();
        }
	
    }
    
    public void commandSave(HttpServletRequest request){
        if (dataFor.equals("upload")){
            String msgString = "";
            long periodId = FRMQueryString.requestLong(request, "PERIOD_ID");
            long positionOfLoginUserId = FRMQueryString.requestLong(request, "POSITION_OF_USER_LOGIN");
            Period periodX = new Period();
            if (periodId > 0){
                try {
                periodX= PstPeriod.fetchExc(periodId);
                } catch (Exception exc){}
            }
            
            Position positionOfLoginUser = new Position();
            if (positionOfLoginUserId > 0){
                try{
                    positionOfLoginUser = PstPosition.fetchExc(positionOfLoginUserId);
                } catch (Exception exc){}
            }

            int startDatePeriodX = periodX==null || periodX.getOID()==0 ?  Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD"))) : periodX.getStartDate().getDate() ; 
            Date dtCalc = new Date();
            Calendar calendar = Calendar.getInstance();
            if(periodX!=null && periodX.getStartDate()!=null){
                dtCalc = (Date)periodX.getStartDate().clone();
            }

            //(Date)srcTransaction.getStartDate().clone();
            calendar.setTime(dtCalc);
            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            boolean noErrWithLeaveStock = true;
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
            this.htmlReturn = "<i class='fa fa-info'></i> "+msgString;
            
        } else {
            Position pos = new Position();
            try{
                pos = PstPosition.fetchExc(this.loginPositionId);
            } catch (Exception exc){

            }

            CtrlEmpSchedule ctrlEmpSchedule = new CtrlEmpSchedule(request);
            this.iErrCode = ctrlEmpSchedule.action(this.iCommand, this.oid, pos, this.oidDelete);
            String message = ctrlEmpSchedule.getMessage();
            this.htmlReturn = "<i class='fa fa-info'></i> "+message;
        }
    }
    
    public void commandDeleteAll(HttpServletRequest request){
	Position pos = new Position();
        try{
            pos = PstPosition.fetchExc(this.loginPositionId);
        } catch (Exception exc){

        }

        CtrlEmpSchedule ctrlEmpSchedule = new CtrlEmpSchedule(request);
        this.iErrCode = ctrlEmpSchedule.action(this.iCommand, this.oid, pos, this.oidDelete);
        String message = ctrlEmpSchedule.getMessage();
        this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandDelete(HttpServletRequest request){
        Position pos = new Position();
        try{
            pos = PstPosition.fetchExc(this.loginPositionId);
        } catch (Exception exc){

        }

        CtrlEmpSchedule ctrlEmpSchedule = new CtrlEmpSchedule(request);
        this.iErrCode = ctrlEmpSchedule.action(this.iCommand, this.oid, pos, this.oidDelete);
        String message = ctrlEmpSchedule.getMessage();
        this.htmlReturn = "<i class='fa fa-info'></i> "+message;
    }
    
    public void commandList(HttpServletRequest request, HttpServletResponse response){
	if(this.dataFor.equals("listSchedule")){
	    String[] cols = { PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID],
		PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1], 
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30],
                PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31],                
                
                };

	    jSONObject = listDataTables(request, response, cols, this.dataFor, this.jSONObject);
	}
    }
    
    public JSONObject listDataTables (HttpServletRequest request, HttpServletResponse response, String[] cols, String dataFor, JSONObject result){
        this.searchTerm = FRMQueryString.requestString(request, "sSearch");
        int amount = 10;
        int start = 0;
        int col = 0;
        String dir = "asc";
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        
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
        
        SrcEmpSchedule srcEmpSchedule = new SrcEmpSchedule();
        FrmSrcEmpSchedule frmSrcEmpSchedule = new FrmSrcEmpSchedule(request, srcEmpSchedule);
        frmSrcEmpSchedule.requestEntityObject(srcEmpSchedule);
	
        SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();
        String whereClause = "";
        
        int countLevel = 0;

        try{
            countLevel = PstLevel.getCount(null);
        }catch(Exception E){
            System.out.println("excption "+E.toString());
        }

        Vector vLevelList = new Vector();
        String orderCnt = PstLevel.fieldNames[PstLevel.FLD_LEVEL]+" ASC ";

        try{
            vLevelList = PstLevel.list(0, 0, "", orderCnt);
        }catch(Exception E){
            System.out.println("excption "+E.toString());
        }
        
        String[] levelId = null;
        levelId = new String[countLevel];

        int max1 = 0;
        for(int j = 0 ; j < countLevel ; j++){

            Level objLevel = new Level();
            objLevel = (Level)vLevelList.get(j);

            String name = "LEVL_"+objLevel.getOID();
            levelId[j] = FRMQueryString.requestString(request,name);
            max1++;
        }
        
        if(dataFor.equals("listSchedule")){
	    whereClause += " ("+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" LIKE '%"+this.searchTerm+"%')";
	}
	
        String colName = cols[col];
        int total = -1;
	
	if(dataFor.equals("listSchedule")){
	    total = sessEmpSchedule.getCountSearch(srcEmpSchedule, levelId);
	}
        
        
        this.amount = amount;
       
        this.colName = colName;
        this.dir = dir;
        this.start = start;
        this.colOrder = col;
        
        try {
            result = getData(total, request, dataFor, srcEmpSchedule, levelId);
        } catch(Exception ex){
            System.out.println(ex);
        }
       
       return result;
    }
    
    public JSONObject getData(int total, HttpServletRequest request, String datafor, SrcEmpSchedule srcEmpSchedule, String[] levelId){
        
        int totalAfterFilter = total;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();
        EmpSchedule empSchedule = new EmpSchedule();
        Vector temp = new Vector();
        Employee emp = new Employee();
        Period period = new Period();
        String whereClause = "";
        String order ="";
        
        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        
        hSysLeaveDP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT);
        hSysLeaveSP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE);
        hSysLeaveLL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_LONG_LEAVE);
        hSysLeaveAL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE);
        
        Hashtable hLeave = new Hashtable(1,1);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT),hSysLeaveDP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE),hSysLeaveSP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE),hSysLeaveLL);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE),hSysLeaveAL);
        
        Hashtable scheduleSymbol = new Hashtable();
	Vector listScd = PstScheduleSymbol.list(0, 0, "", "");
	scheduleSymbol.put("0", "-");
	for (int ls = 0; ls < listScd.size(); ls++) 
	{
            ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
            scheduleSymbol.put(String.valueOf(scd.getOID()), scd.getSymbol());
	}
        
        //Inisialisasi Tanggal
        Date now = new Date();	
	int monthStartDate = Integer.parseInt(String.valueOf(now.getMonth()));
	int yearStartDate =  Integer.parseInt(String.valueOf(now.getYear()+1900));
	int dateStartDate =  Integer.parseInt(String.valueOf(now.getDate()));
	
        int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));// POINT						
	startDatePeriod = startDatePeriod -1;
	GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate); // POINT	
        int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); // POINT	
	//boolean privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
        
        if (this.searchTerm == null){
            whereClause += "";                  
        }else{
	     if(dataFor.equals("listSchedule")){
               whereClause += " ("+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+" LIKE '%"+this.searchTerm+"%')";
            }
        }
        
        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
	
        Vector listData = new Vector(1,1);
	if(datafor.equals("listSchedule")){
	    listData = sessEmpSchedule.searchEmpSchedule(srcEmpSchedule, levelId , this.start, this.amount);
	}
         
        
        
        for (int i =0 ; i<=listData.size()-1;i++){
	    JSONArray ja = new JSONArray();
            if(datafor.equals("listSchedule")){
		temp = (Vector) listData.get(i);
                empSchedule = (EmpSchedule)temp.get(0);
		emp = (Employee) temp.get(1);
		period = (Period) temp.get(2);	
                
                long employeeId = emp.getOID();
		Date periodStartDate = period.getStartDate();
                
                EmpSchedule objEmpSchedule = new EmpSchedule();
                
                try{
                    objEmpSchedule = PstEmpSchedule.listSchedule(employeeId,periodStartDate);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
		String strFullName = emp.getFullName()+" / "+ emp.getEmployeeNum();
                
                String checkButton = "<input type='checkbox' name='"+FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMP_SCHEDULE_ID]+"' class='"+FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMP_SCHEDULE_ID]+"' value='"+empSchedule.getOID()+"'>" ;
                ja.put(""+checkButton);
		ja.put("<a href='#' class='btneditgeneral' data-oid='"+empSchedule.getOID()+"' data-for='editSchedule'>"+period.getPeriod()+"</a>");
		ja.put(strFullName);
                
                if (SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) {
                    //Not define yet
                } else {
                    String strScheduleSymbol = "";
                    long idScheduleSymbol1 = 0;
                    long idScheduleSymbol2 = 0;

                    for (int j = 0; j < maxDayOfMonth; j++) {

                        if (startDatePeriod == maxDayOfMonth) {

                            startDatePeriod = 1;

                            ScheduleD1D2 scheduleD1D2 = new ScheduleD1D2();

                            scheduleD1D2 = PstEmpSchedule.getSch(objEmpSchedule, startDatePeriod);

                            if (scheduleD1D2 != null) {
                                try {
                                    idScheduleSymbol1 = scheduleD1D2.getD();
                                } catch (Exception e) {
                                    idScheduleSymbol1 = 0;
                                }

                                try {
                                    idScheduleSymbol2 = scheduleD1D2.getD2Nd();
                                } catch (Exception e) {
                                    idScheduleSymbol2 = 0;
                                }
                            }

                            strScheduleSymbol = "" + scheduleSymbol.get("" + idScheduleSymbol1) + (idScheduleSymbol2 == 0 ? "" : "/" + scheduleSymbol.get("" + idScheduleSymbol2));

                            int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);

                            if (typeSymbol > 0) {

                                if (typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT) {

                                    strScheduleSymbol = "<font color=\"#FF0000\">" + strScheduleSymbol + "</font>";
                                }


                                if (typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                    strScheduleSymbol = "<font color=\"#FF0000\">" + strScheduleSymbol + "</font>";
                                }

                                if (typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) {

                                    strScheduleSymbol = "<font color=\"#FF0000\">" + strScheduleSymbol + "</font>";

                                }
                            }
                            ja.put(!strScheduleSymbol.equals("null") ? strScheduleSymbol : "-");

                        } else {

                            startDatePeriod = startDatePeriod + 1;
                            ScheduleD1D2 scheduleD1D2 = new ScheduleD1D2();

                            scheduleD1D2 = PstEmpSchedule.getSch(objEmpSchedule, startDatePeriod);

                            if (scheduleD1D2 != null) {
                                try {
                                    idScheduleSymbol1 = scheduleD1D2.getD();
                                } catch (Exception e) {
                                    idScheduleSymbol1 = 0;
                                }

                                try {
                                    idScheduleSymbol2 = scheduleD1D2.getD2Nd();
                                } catch (Exception e) {
                                    idScheduleSymbol2 = 0;
                                }
                            }
                            strScheduleSymbol = "" + scheduleSymbol.get("" + idScheduleSymbol1) + (idScheduleSymbol2 == 0 ? "" : "/" + scheduleSymbol.get("" + idScheduleSymbol2));

                            int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);

                            if (typeSymbol > 0) {

                                if (typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT) {

                                    strScheduleSymbol = "<font color=\"#FF0000\">" + strScheduleSymbol + "</font>";

                                }

                                if (typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE) {

                                    strScheduleSymbol = "<font color=\"#FF0000\">" + strScheduleSymbol + "</font>";
                                }

                                if (typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) {

                                    strScheduleSymbol = "<font color=\"#FF0000\">" + strScheduleSymbol + "</font>";

                                }

                            }
                            ja.put(!strScheduleSymbol.equals("null") ? strScheduleSymbol : "-");
                        }
                    }

                }
                
                String buttonUpdate = "";
                //String buttonTemplate = "<button class='btn btn-warning btneditgeneral' data-oid='"+presence.getOID()+"' data-for='showDocMasterTemplate' type='button'><i class='fa fa-file'></i> Template</button> "; 
                //String buttonFlow = "<button class='btn btn-warning btnflow btn-xs' data-oid='"+presence.getOID()+"' data-for='showFlowForm' type='button' data-toggle='tooltip' data-placement='top' title='Approval'><i class='fa fa-user'></i></button> ";
		if(true/*privUpdate*/){
		    //buttonUpdate = "<button class='btn btn-warning btneditgeneral btn-xs' data-oid='"+empSchedule.getOID()+"' data-for='showPresenceListForm' type='button' data-toggle='tooltip' data-placement='top' title='Edit'><i class='fa fa-pencil'></i></button> ";
		}
		ja.put(buttonUpdate+"<button class='btn btn-danger btn-xs btndeletesingle' data-oid='"+empSchedule.getOID()+"' data-for='deleteDocMasterSingle' type='button' data-toggle='tooltip' data-placement='top' title='Delete'><i class='fa fa-trash'></i></button> ");
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
    
    
    public int getLeaveSchType(Hashtable hLeave, long leaveOid) {
        int type = -1;

        String key = String.valueOf(leaveOid);

        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();

        hSysLeaveDP = (Hashtable) hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT));
        hSysLeaveSP = (Hashtable) hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE));
        hSysLeaveLL = (Hashtable) hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE));
        hSysLeaveAL = (Hashtable) hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE));

        if (hSysLeaveDP.containsKey(key)) {
            return PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT;
        } else if (hSysLeaveSP.containsKey(key)) {
            return PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
        } else if (hSysLeaveLL.containsKey(key)) {
            return PstScheduleCategory.CATEGORY_LONG_LEAVE;
        } else if (hSysLeaveAL.containsKey(key)) {
            return PstScheduleCategory.CATEGORY_ANNUAL_LEAVE;
        }

        return type;
    }

    public int getLeaveType(Hashtable hashData, long leaveOid) {
        int result = 0;
        String strResult = String.valueOf(hashData.get("" + leaveOid));

        if (strResult.equalsIgnoreCase("AL")) {
            result = PstLeaveApplication.LEAVE_APPLICATION_AL;
        }

        if (strResult.equalsIgnoreCase("LL")) {
            result = PstLeaveApplication.LEAVE_APPLICATION_LL;
        }

        if (strResult.equalsIgnoreCase("MATERNITY")) {
            result = PstLeaveApplication.LEAVE_APPLICATION_MATERNITY;
        }

        if (strResult.equalsIgnoreCase("DC")) {
            result = PstLeaveApplication.LEAVE_APPLICATION_DC;
        }

        if (strResult.equalsIgnoreCase("SPEC")) {
            result = PstLeaveApplication.LEAVE_APPLICATION_SPECIAL;
        }

        if (strResult.equalsIgnoreCase("UNPAID")) {
            result = PstLeaveApplication.LEAVE_APPLICATION_UNPAID;
        }

        return result;
    }
    
    public String addWorkingScheduleForm(){
        String returnData = ""
                + "<div class='form-group'>"
		    + "<label>Period</label>"
                    + "<select id='period1' name='"+ FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]+"' class='form-control chosen-select' data-for='editSchedule' data-replacement='#calendar'>"
                    + "<option value='0'>Select Period...</option>";
                    Vector listPeriod = PstPeriod.listAll();
                    if (listPeriod.size() > 0){
                        for (int i=0; i < listPeriod.size(); i++){
                            Period period = (Period) listPeriod.get(i);
                            returnData += "<option value='"+period.getOID()+"'>"+period.getPeriod()+"</option>";
                        }
                    }
		returnData += "</select></div>"
                        + "<div class='form-group'>"
                        + "<label>Employee</label>"
                        + "<select id='employee' name='"+ FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]+"' class='form-control chosen-select'>"
                        + "<option value='0'>Select Employee...</option>"
                        + "";
                        Vector listEmployee = PstEmployee.listAll();
                        if (listEmployee.size() > 0){
                            for (int i=0; i < listEmployee.size(); i++){
                                Employee emp = (Employee) listEmployee.get(i);
                                returnData += "<option value='"+emp.getOID()+"'>"+emp.getFullName()+" / " + emp.getEmployeeNum() +"</option>";
                            }
                        }
                returnData += "</select></div>"
                        + "<div id='calendarContainer'>"
                        + "</div>"
                        + "</div>";
        
        return returnData;
    }
    
    public String workingScheduleForm(HttpServletRequest request){
        String returnData = "";
        
        long empLoginId = FRMQueryString.requestLong(request, "emplx");
        boolean isHRDLogin = FRMQueryString.requestBoolean(request, "hrdlogin");
        
        EmpSchedule empSchedule = new EmpSchedule();
        long oidPeriod = 0;
        long oidEmployee = 0;
        if(this.oid > 0){
            try{
                empSchedule = PstEmpSchedule.fetchExc(this.oid);
                oidPeriod = empSchedule.getPeriodId();
                oidEmployee = empSchedule.getEmployeeId();
            } catch (Exception exc){
                
            }
        }
        
        if (oidPeriod == 0){
            oidPeriod = FRMQueryString.requestLong(request, "OID_PERIOD");
        }
        
        Date dtCurrentDate = new Date();
        boolean checkUpdateScheduleByDate = false;
        int intcheckUpdateScheduleByDate = 0;
        try{
        intcheckUpdateScheduleByDate=Integer.parseInt("" + PstSystemProperty.getValueByName("CHECK_UPDATE_SCHEDULE"));
        }catch(Exception exc){

        }

        if (intcheckUpdateScheduleByDate == 1 && !isHRDLogin){
            checkUpdateScheduleByDate = true;
        }

        int intCheckLeaveRoster=0;
        try{
             intCheckLeaveRoster= Integer.parseInt("" + PstSystemProperty.getValueByName("UPDATE_SCHLD_TIME_LEAVE_ROSTER"));
        }catch(Exception exc){

        }

         int intCheckDPRoster=0;
        try{
            intCheckDPRoster  = Integer.parseInt("" + PstSystemProperty.getValueByName("UPDATE_SCHLD_TIME_DP_ROSTER"));
        }catch(Exception exc){

        }


        //update by devin 2014-01-22
         boolean cekScheduleWeekly = false;
        try{
        cekScheduleWeekly=Boolean.parseBoolean("" + PstSystemProperty.getValueByName("ATTENDANCE_CONFIG_WEEKLY_TO_DISABLED"));
        }catch(Exception exc){

        }
        
        boolean isCheckFirstSchedule = true;
        
        Date dtPeriodNow = new Date();
        Date dtPeriodStart = new Date();
        Date dtPeriodEnd = new Date();
        long periodId = 0;

        Period period = new Period();
        Employee employee = new Employee();

        Vector list = PstScheduleSymbol.getScheduleDPALLL();

        if (iCommand == Command.ADD){                

            dtPeriodStart = new Date(dtPeriodNow.getYear(), dtPeriodNow.getMonth() - 1, 21);
            dtPeriodEnd = new Date(dtPeriodNow.getYear(), dtPeriodNow.getMonth(), 20);

        } else {
            try {
                period = PstPeriod.fetchExc(oidPeriod);
                dtPeriodStart = period.getStartDate();
                dtPeriodEnd = period.getEndDate();                    
                employee = PstEmployee.fetchExc(oidEmployee);
            } catch (Exception ex) {
                System.out.println("Exception "+ex.toString());
            }
        }

        Vector listScheduleSymbol = new Vector(1, 1);
        listScheduleSymbol = PstScheduleSymbol.list(0, 500, "", PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]);
        
        
        String dField[] = new String[31];
        dField[0] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D1];
        dField[1] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2];
        dField[2] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D3];
        dField[3] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D4];
        dField[4] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D5];
        dField[5] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D6];
        dField[6] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D7];
        dField[7] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D8];
        dField[8] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D9];
        dField[9] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D10];
        dField[10] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D11];
        dField[11] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D12];
        dField[12] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D13];
        dField[13] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D14];
        dField[14] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D15];
        dField[15] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D16];
        dField[16] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D17];
        dField[17] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D18];
        dField[18] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D19];
        dField[19] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D20];
        dField[20] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D21];
        dField[21] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D22];
        dField[22] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D23];
        dField[23] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D24];
        dField[24] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D25];
        dField[25] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D26];
        dField[26] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D27];
        dField[27] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D28];
        dField[28] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D29];
        dField[29] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D30];
        dField[30] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D31];

        // Name of each second combobox's schedule
        String dField2nd[] = new String[31];
        dField2nd[0] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND1];
        dField2nd[1] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND2];
        dField2nd[2] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND3];
        dField2nd[3] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND4];
        dField2nd[4] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND5];
        dField2nd[5] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND6];
        dField2nd[6] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND7];
        dField2nd[7] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND8];
        dField2nd[8] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND9];
        dField2nd[9] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND10];
        dField2nd[10] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND11];
        dField2nd[11] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND12];
        dField2nd[12] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND13];
        dField2nd[13] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND14];
        dField2nd[14] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND15];
        dField2nd[15] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND16];
        dField2nd[16] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND17];
        dField2nd[17] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND18];
        dField2nd[18] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND19];
        dField2nd[19] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND20];
        dField2nd[20] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND21];
        dField2nd[21] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND22];
        dField2nd[22] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND23];
        dField2nd[23] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND24];
        dField2nd[24] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND25];
        dField2nd[25] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND26];
        dField2nd[26] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND27];
        dField2nd[27] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND28];
        dField2nd[28] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND29];
        dField2nd[29] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND30];
        dField2nd[30] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND31];

        String dSelect[] = new String[31];
        dSelect[0] = String.valueOf(empSchedule.getD1());
        dSelect[1] = String.valueOf(empSchedule.getD2());
        dSelect[2] = String.valueOf(empSchedule.getD3());
        dSelect[3] = String.valueOf(empSchedule.getD4());
        dSelect[4] = String.valueOf(empSchedule.getD5());
        dSelect[5] = String.valueOf(empSchedule.getD6());
        dSelect[6] = String.valueOf(empSchedule.getD7());
        dSelect[7] = String.valueOf(empSchedule.getD8());
        dSelect[8] = String.valueOf(empSchedule.getD9());
        dSelect[9] = String.valueOf(empSchedule.getD10());
        dSelect[10] = String.valueOf(empSchedule.getD11());
        dSelect[11] = String.valueOf(empSchedule.getD12());
        dSelect[12] = String.valueOf(empSchedule.getD13());
        dSelect[13] = String.valueOf(empSchedule.getD14());
        dSelect[14] = String.valueOf(empSchedule.getD15());
        dSelect[15] = String.valueOf(empSchedule.getD16());
        dSelect[16] = String.valueOf(empSchedule.getD17());
        dSelect[17] = String.valueOf(empSchedule.getD18());
        dSelect[18] = String.valueOf(empSchedule.getD19());
        dSelect[19] = String.valueOf(empSchedule.getD20());
        dSelect[20] = String.valueOf(empSchedule.getD21());
        dSelect[21] = String.valueOf(empSchedule.getD22());
        dSelect[22] = String.valueOf(empSchedule.getD23());
        dSelect[23] = String.valueOf(empSchedule.getD24());
        dSelect[24] = String.valueOf(empSchedule.getD25());
        dSelect[25] = String.valueOf(empSchedule.getD26());
        dSelect[26] = String.valueOf(empSchedule.getD27());
        dSelect[27] = String.valueOf(empSchedule.getD28());
        dSelect[28] = String.valueOf(empSchedule.getD29());
        dSelect[29] = String.valueOf(empSchedule.getD30());
        dSelect[30] = String.valueOf(empSchedule.getD31());

        // Selected value of each second combobox's schedule
        String dSelect2nd[] = new String[31];
        dSelect2nd[0] = String.valueOf(empSchedule.getD2nd1());
        dSelect2nd[1] = String.valueOf(empSchedule.getD2nd2());
        dSelect2nd[2] = String.valueOf(empSchedule.getD2nd3());
        dSelect2nd[3] = String.valueOf(empSchedule.getD2nd4());
        dSelect2nd[4] = String.valueOf(empSchedule.getD2nd5());
        dSelect2nd[5] = String.valueOf(empSchedule.getD2nd6());
        dSelect2nd[6] = String.valueOf(empSchedule.getD2nd7());
        dSelect2nd[7] = String.valueOf(empSchedule.getD2nd8());
        dSelect2nd[8] = String.valueOf(empSchedule.getD2nd9());
        dSelect2nd[9] = String.valueOf(empSchedule.getD2nd10());
        dSelect2nd[10] = String.valueOf(empSchedule.getD2nd11());
        dSelect2nd[11] = String.valueOf(empSchedule.getD2nd12());
        dSelect2nd[12] = String.valueOf(empSchedule.getD2nd13());
        dSelect2nd[13] = String.valueOf(empSchedule.getD2nd14());
        dSelect2nd[14] = String.valueOf(empSchedule.getD2nd15());
        dSelect2nd[15] = String.valueOf(empSchedule.getD2nd16());
        dSelect2nd[16] = String.valueOf(empSchedule.getD2nd17());
        dSelect2nd[17] = String.valueOf(empSchedule.getD2nd18());
        dSelect2nd[18] = String.valueOf(empSchedule.getD2nd19());
        dSelect2nd[19] = String.valueOf(empSchedule.getD2nd20());
        dSelect2nd[20] = String.valueOf(empSchedule.getD2nd21());
        dSelect2nd[21] = String.valueOf(empSchedule.getD2nd22());
        dSelect2nd[22] = String.valueOf(empSchedule.getD2nd23());
        dSelect2nd[23] = String.valueOf(empSchedule.getD2nd24());
        dSelect2nd[24] = String.valueOf(empSchedule.getD2nd25());
        dSelect2nd[25] = String.valueOf(empSchedule.getD2nd26());
        dSelect2nd[26] = String.valueOf(empSchedule.getD2nd27());
        dSelect2nd[27] = String.valueOf(empSchedule.getD2nd28());
        dSelect2nd[28] = String.valueOf(empSchedule.getD2nd29());
        dSelect2nd[29] = String.valueOf(empSchedule.getD2nd30());
        dSelect2nd[30] = String.valueOf(empSchedule.getD2nd31());                                                                                                                            

        int dCategory[] = new int[31];

        String dayname[] = new String[7];

        dayname[0] = "Sunday";
        dayname[1] = "Monday";
        dayname[2] = "Tuesday";
        dayname[3] = "Wednesday";
        dayname[4] = "Thursday";
        dayname[5] = "Friday";
        dayname[6] = "Saturday";

        String mon[] = new String[12];

        mon[0] = "January";
        mon[1] = "February";
        mon[2] = "March";
        mon[3] = "April";
        mon[4] = "May";
        mon[5] = "June";
        mon[6] = "July";
        mon[7] = "August";
        mon[8] = "September";
        mon[9] = "October";
        mon[10] = "November";
        mon[11] = "December";

        int yearStart = dtPeriodStart.getYear() + 1900;
        int monthStart = dtPeriodStart.getMonth();
        int dateStart = dtPeriodStart.getDate();
        int yearEnd = dtPeriodEnd.getYear() + 1900;
        int monthEnd = dtPeriodEnd.getMonth();
        int dateEnd = dtPeriodEnd.getDate();
        System.out.println(":::::::::::::: Start Month : "+monthStart);
        System.out.println(":::::::::::::: End Month   : "+monthEnd);

        GregorianCalendar gcStart = new GregorianCalendar(yearStart, monthStart, dateStart);
        int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);                                                                                                                            

        int nDayOfWeekStart = 0;
        if (monthEnd != monthStart) {
            nDayOfWeekStart = (gcStart.get(GregorianCalendar.DAY_OF_WEEK)+6)%7;
        } else {
            nDayOfWeekStart = (gcStart.get(GregorianCalendar.DAY_OF_WEEK)+6)%7;
        }
        GregorianCalendar gcLastDateStart = new GregorianCalendar(yearStart, monthStart, nDayOfMonthStart - 1);
        int nWeekInMonthStart = gcLastDateStart.get(GregorianCalendar.WEEK_OF_MONTH);

        GregorianCalendar gcEnd;
        if (monthEnd != monthStart) {
            gcEnd = new GregorianCalendar(yearEnd, monthEnd, 1);
        } else {
            gcEnd = new GregorianCalendar(yearEnd, monthEnd, dateEnd);
        }
        int nDayOfMonthEnd = gcEnd.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int nDayOfWeekEnd = gcEnd.get(GregorianCalendar.DAY_OF_WEEK);
        GregorianCalendar gcLastDateEnd = new GregorianCalendar(yearEnd, monthEnd, nDayOfMonthEnd - 1);
        int nWeekInMonthEnd = gcLastDateEnd.get(GregorianCalendar.WEEK_OF_MONTH);

        int nDayTotal = nDayOfMonthStart - dateStart + 20 + 1;
        System.out.println("nDayOfWeekStart ::::::" + nDayOfWeekStart);
        int nWeekInMonthTotal = (nDayTotal / 7 + 1) * 7;
        int i = 0;
        int j = 0;
        int dS = dateStart;
        int remainder = (((nDayTotal + nDayOfWeekStart) / 7 + 1) * 7 - (nDayTotal + nDayOfWeekStart)) % 7;

        //tambahan
        // Vector schedule lengkap dengan category schedule-nya
        int includeSchedule = 0;
        try{
            includeSchedule = Integer.parseInt(PstSystemProperty.getValueByName("INCLUDE_LEAVE"));
        }catch(Exception e){
            includeSchedule = 0;
            System.out.println("Exception "+e.toString());    
        }

        Vector vectScheduleFirstRules = new Vector(1, 1);
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_REGULAR));
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_SPLIT_SHIFT));
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_NIGHT_WORKER));
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_ABSENCE));
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_OFF));
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_ACCROSS_DAY));
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY));
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_NEAR_ACCROSS_DAY));

        if(includeSchedule == 0){
            vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT));
            vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE));
            vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE));
        }

        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE));
        vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF));
        Vector vectScheduleWithCategory = SessEmpSchedule.getMasterSchedule(vectScheduleFirstRules);

        Vector vectScheduleSecondRules = new Vector(1, 1);
        vectScheduleSecondRules.add(String.valueOf(PstScheduleCategory.CATEGORY_SPLIT_SHIFT));                                                                                                                            																	
        Vector vectSchldSecondWithCategory = SessEmpSchedule.getMasterSchedule(vectScheduleSecondRules);
        
        
        returnData += ""
	    + "<div class='form-group'>";
        if (this.oid > 0){
                returnData += "<input type='hidden' name='"+FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]+"' value='"+oidPeriod+"'>"
                + "<input type='hidden' name='"+FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]+"' value='"+oidEmployee+"'>";
                        }
                returnData += "<table id='calendar'>"
                    + "<b><caption>"
                    + mon[gcEnd.get(GregorianCalendar.MONTH)] + " " + String.valueOf(yearEnd)
                    + "</caption></b>"
                    + "<tr class='weekdays'>";
                    for (int d = 0; d < 7; d++) {
                        if ((d % 7) == 0) {
                            returnData += "<th scope=\"col\">" + dayname[d] + "</th>";
                        } else if ((d % 7) == 6) {
                            returnData += "<th scope=\"col\">" + dayname[d] + "</th>";
                        } else {
                            returnData +="<th scope=\"col\">" + dayname[d] + "</th>";
                        }
                    }
	    returnData += "</tr>"
                        + "<tr class='days'>";
                        long total = dtPeriodEnd.getTime() - dtPeriodStart.getTime();
                    int intMaxDay = (int)((total)/(24*60*60*1000))+1;
                    System.out.println("::::::::::::::::::::::::::::: TOTAL DAY :: "+dtPeriodEnd.getTime());
                    System.out.println("::::::::::::::::::::::::::::: TOTAL MAX DAY :: "+intMaxDay);

                    int delimenter = 0;
                    if(dateStart>1){delimenter = dateEnd;}//memisahkan proses yang menggunakan bulan sama atau split bulan
                    System.out.println("::::::::::::::::::::::::::::: USE DAY :: "+(intMaxDay+nDayOfWeekStart-delimenter));

                    String valUpdateScheduleByLevel = "no";
                    try{
                        valUpdateScheduleByLevel = PstSystemProperty.getValueByName("CHANGE_SCHEDULE_BY_LEVEL");
                    }catch(Exception E){
                        valUpdateScheduleByLevel = "no";
                        System.out.println("[exception] "+E.toString());
                    }

                    if(valUpdateScheduleByLevel.compareTo(PstSystemProperty.SYS_NOT_INITIALIZED)==0){
                        valUpdateScheduleByLevel = "no";
                    }

                    Date calenderDt = dtPeriodStart;
                    Date ProcesDt = dtPeriodStart;

                    Date tmpCalenderDt = dtPeriodStart;
                    for (i = 0; i < intMaxDay+nDayOfWeekStart-delimenter; i++){ //looping sebanyak tanggal calennder

                        //START PROSES JIKA NILAI i ADALAH KELIPATAN 7 ==> SUNDAY    
                        if (((i % 7) == 0 && i > 0)||i==0){

                            returnData += "<td class=\"day\">";

                            if (i > nDayOfWeekStart-1){    

                                //returnData +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                returnData += "<div class=\"date\">" + dS + "</div>";
                                returnData +=  "<div>";

                                boolean updateSchMnth = false;

                                ProcesDt.setDate(dS);

                                int diffMnth = SessEmpSchedule.DateDifferent(new Date(), ProcesDt);

                                if(diffMnth >= 0){
                                    updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_BEFORE_TIME, ProcesDt);
                                }else{
                                    updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_AFTER_TIME, ProcesDt);
                                }

                                /* First schedule */
                                Vector scd_value = new Vector(1, 1);
                                Vector scd_key = new Vector(1, 1);            

                                /* Second schedule */
                                Vector scd_2nd_value = new Vector(1, 1);
                                Vector scd_2nd_key = new Vector(1, 1);

                                if(updateSchMnth == false && valUpdateScheduleByLevel.equals("ok")){

                                    for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                        Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                        ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);       
                                        ScheduleCategory scdCat = (ScheduleCategory)vectResult.get(1);

                                        if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))){

                                            scd_key.add(scd.getSymbol());
                                            scd_value.add(String.valueOf(scd.getOID()));

                                            dSelect[dS - 1] = String.valueOf(scd.getOID());
                                            dCategory[dS - 1] = scdCat.getCategoryType();

                                        }

                                        if(scd_value == null || scd_value.size() < 0){
                                            scd_value.add("");
                                            scd_key.add("...");
                                        }
                                    }

                                }else{/* Jika status schedule bisa di ubah */

                                    scd_value.add("");
                                    scd_key.add("...");

                                    // first schedule
                                    if (vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                        String value = "";
                                        String key = "";
                                        boolean statusSchedule = false;

                                        for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                            boolean scheduleOff = false;

                                            Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                            ScheduleCategory scdCat = (ScheduleCategory)vectResult.get(1);

                                            scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                            if(scheduleOff == false){ ///tidak cuti atau schedule of
                                                scd_key.add(scd.getSymbol());
                                                scd_value.add(String.valueOf(scd.getOID()));
                                            }

                                            if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))){

                                                statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(statusSchedule){
                                                    value = String.valueOf(scd.getOID());
                                                    key = scd.getSymbol();
                                                }

                                                dSelect[dS - 1] = String.valueOf(scd.getOID());
                                                dCategory[dS - 1] = scdCat.getCategoryType();

                                            }
                                        }

                                        if(statusSchedule){ /* termasuk schedule leave */

                                            scd_value = new Vector(1, 1);
                                            scd_key = new Vector(1, 1);

                                            scd_key.add(key);
                                            scd_value.add(value);

                                        }
                                    }
                                 }

                                /* Untuk second schedule */
                                if(updateSchMnth == false && valUpdateScheduleByLevel.equals("ok")){

                                    if (vectSchldSecondWithCategory != null && vectSchldSecondWithCategory.size() > 0){
                                        for (int ls2nd = 0; ls2nd < vectSchldSecondWithCategory.size(); ls2nd++){

                                            Vector vectResult = (Vector) vectSchldSecondWithCategory.get(ls2nd);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                            if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))){
                                                scd_2nd_key.add(scd.getSymbol());
                                                scd_2nd_value.add(String.valueOf(scd.getOID()));
                                            }
                                        }

                                        if(scd_2nd_key == null || scd_2nd_key.size() < 0){
                                            scd_2nd_key.add("");
                                            scd_2nd_value.add("...");
                                        }
                                    }

                                }else{

                                    // second schedule
                                    scd_2nd_value.add("0");
                                    scd_2nd_key.add("...");

                                    if (vectSchldSecondWithCategory != null && vectSchldSecondWithCategory.size() > 0){

                                        String value = "";
                                        String key = "";
                                        boolean statusSchedule = false;

                                        for (int ls2nd = 0; ls2nd < vectSchldSecondWithCategory.size(); ls2nd++){

                                            boolean scheduleOff = false;

                                            Vector vectResult = (Vector) vectSchldSecondWithCategory.get(ls2nd);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);                        

                                            scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                            if(scheduleOff == false){
                                                scd_2nd_key.add(scd.getSymbol());
                                                scd_2nd_value.add(String.valueOf(scd.getOID()));
                                            }

                                            if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))){

                                                statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(statusSchedule){
                                                    value = String.valueOf(scd.getOID());
                                                    key = scd.getSymbol();
                                                }

                                                dSelect2nd[dS - 1] = String.valueOf(scd.getOID());                            
                                            }
                                        }

                                        if(statusSchedule){
                                            scd_2nd_value = new Vector(1, 1);
                                            scd_2nd_key = new Vector(1, 1);

                                            scd_2nd_key.add(key);
                                            scd_2nd_value.add(value);
                                        }
                                    }
                                }

                                // combo first schedule     
                                   //update by devin  2014-01-20  tgl 22,29   
                               Calendar ca1 = Calendar.getInstance();
                        Date ne=new Date();
                        ca1.set(ne.getYear()+1900, ne.getMonth()+1, ne.getDate());
                       ca1.setTime(ne);
                     int wik = ca1.get(Calendar.WEEK_OF_MONTH);

                        //System.returnData +="Week of Month :" + wk);
                        if( cekScheduleWeekly && dField[j] == dField[j] ){
                      Calendar ca12 = Calendar.getInstance();
                        Date nee=new Date();

                       ca12.setTime(tmpCalenderDt);
                          int wnnk = ca12.get(Calendar.WEEK_OF_MONTH);
                        if(tmpCalenderDt.getYear()<ne.getYear()){   
                            if(tmpCalenderDt.getMonth()  - ne.getMonth()==11){
                                if(wik-wnnk==-4||wik-wnnk==-3||wik-wnnk==-2){
                                returnData += ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "");
                            }  else{
                                returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "disabled");
                            }
                            }
                           else{
                                returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "disabled");
                            }

                        }else if(tmpCalenderDt.getMonth() - ne.getMonth()==-2  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-3  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-4  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-5  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-6  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-7  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-8  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-9  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-10  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-11  && tmpCalenderDt.getYear() == ne.getYear()){

                     returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "disabled");

                    } else if(tmpCalenderDt.getMonth() - ne.getMonth()==-1  && tmpCalenderDt.getYear() == ne.getYear() ){
                    if(wik-wnnk==-3 ||wik-wnnk==-4){
                      returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "");

                    }else{

                      returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "disabled");
                    }

                    }else if(tmpCalenderDt.getMonth() < ne.getMonth()  && tmpCalenderDt.getYear() > ne.getYear()){
                         returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "");
                           }else if(tmpCalenderDt.getMonth()> ne.getMonth()  && tmpCalenderDt.getYear() == ne.getYear()){
                            returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "");
                                   }else if(tmpCalenderDt.getYear()==ne.getYear()){
                          if(tmpCalenderDt.getMonth()==ne.getMonth()){
                              if(wik-wnnk==2||wik-wnnk==1 ||wik-wnnk==0 ||wik-wnnk==-1||wik-wnnk==-2||wik-wnnk==-3||wik-wnnk==-4 ){        
                      returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "");
                                           }else{                   
                      returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "disabled");
                                           }
                        }  
                        }
                           }else{
                            returnData +=ControlCombo.draw(dField[dS - 1], "", "", dSelect[dS - 1], scd_value, scd_key, "");
                           }

                                // combo second schedule
                                if (dSelect2nd[dS - 1].equals("0")) {
                                    if (isCheckFirstSchedule) {
                                        returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\"");
                                    } else {
                                        int cat1stSchld = -1;
                                        if ((dSelect[dS - 1]).length() >= 18) {                        
                                            cat1stSchld = dCategory[dS-1];
                                        }

                                        if (cat1stSchld == PstScheduleCategory.CATEGORY_ABSENCE || cat1stSchld == PstScheduleCategory.CATEGORY_OFF || cat1stSchld == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || cat1stSchld == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || cat1stSchld == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                            returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\"");
                                        } else {
                                            returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[dS - 1]) + "','" + dS + "')\"");
                                        }
                                    }
                                } else {
                                    returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[dS - 1]) + "','" + dS + "')\"");
                                }

                                System.out.println("dS : " + dS + " - dField : " + dField[dS - 1]);
                                returnData +="</div>";
                                //returnData +="</td");
                                dS++;
                                long tmpDtx = tmpCalenderDt.getTime() + (24 * 60 * 60 * 1000);
                                tmpCalenderDt = new Date(tmpDtx);
                            } else {
                                returnData +="&nbsp;";
                            }
                            returnData +="</td>";

                        } else if ((i % 7) == 6) {

                            returnData +="<td class=\"day\">";

                            if (i > nDayOfWeekStart-1) {

                                //returnData +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                returnData +="<div class=\"date\">" + dS + "</div>";
                                returnData +="<div>";

                                boolean updateSchMnth = false;

                                ProcesDt.setDate(dS);

                                int diffMnth = SessEmpSchedule.DateDifferent(new Date(), ProcesDt);

                                if(diffMnth >= 0){
                                    updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_BEFORE_TIME, ProcesDt);
                                }else{
                                    updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_AFTER_TIME, ProcesDt);
                                }

                                //first schedule
                                Vector scd_value = new Vector(1, 1);
                                Vector scd_key = new Vector(1, 1);

                                // second schedule
                                Vector scd_2nd_value = new Vector(1, 1);
                                Vector scd_2nd_key = new Vector(1, 1);   

                                  //update by satrya 2012-08-05
                                /*
                                * Desc: untuk melakukan check apakah itu cuti'nya jam'an atau full day
                                * iLeaveMinuteEnable = 1 maka cuti jam-jam'an
                                * */
                                            int iLeaveMinuteEnable = 0;//hanya cuti full day jika fullDayLeave = 0
                                            try{
                                                iLeaveMinuteEnable = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_MINUTE_ENABLE"));
                                            }catch(Exception ex){System.out.println("Execption LEAVE_MINUTE_ENABLE: " + ex);}


                                if(updateSchMnth ==false && valUpdateScheduleByLevel.equals("ok")){

                                    if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                        for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                            Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                            if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))){
                                                scd_key.add(scd.getSymbol());
                                                scd_value.add(String.valueOf(scd.getOID()));
                                            }
                                        }

                                        if(scd_value == null || scd_value.size() < 0){
                                            scd_value.add("");
                                            scd_key.add("...");
                                        }
                                    }

                                }else{/* Jika status schedule bisa di ubah */

                                    scd_value.add("");
                                    scd_key.add("...");

                                    // first schedule
                                    if (vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                        String value = "";
                                        String key = "";
                                        boolean statusSchedule = false;

                                        int maxSchedule = vectScheduleWithCategory.size();

                                        for (int ls = 0; ls < maxSchedule; ls++){

                                            boolean scheduleOff = false;

                                            Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                            ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                            scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                            if(scheduleOff == false){ ///jika ada cuti
                                                scd_key.add(scd.getSymbol());
                                                  //scd_key.add(scd.getSymbol());
                                                scd_value.add(String.valueOf(scd.getOID())); 
                                            }

                                            if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))) {

                                                statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(statusSchedule){

                                                    value = String.valueOf(scd.getOID());
                                                    key = scd.getSymbol();

                                                }

                                                dSelect[dS - 1] = String.valueOf(scd.getOID());
                                                dCategory[dS - 1] = scdCat.getCategoryType();

                                            }
                                        }

                                        if(statusSchedule){
                                            scd_value = new Vector(1, 1);
                                            scd_key = new Vector(1, 1);

                                            scd_key.add(key);
                                            scd_value.add(value);
                                        }                
                                    }
                                }

                                if(updateSchMnth == false && valUpdateScheduleByLevel.equals("ok")){

                                    if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                        for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                            Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                            if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))) {
                                                scd_2nd_key.add(scd.getSymbol());
                                                scd_2nd_value.add(String.valueOf(scd.getOID()));
                                            }
                                        }
                                    }

                                }else{/* Jika status schedule bisa di ubah */

                                    scd_2nd_value.add("0");
                                    scd_2nd_key.add("...");

                                    // second schedule
                                    if (vectSchldSecondWithCategory != null && vectSchldSecondWithCategory.size() > 0){

                                        String value = "";
                                        String key = "";
                                        boolean statusSchedule = false;

                                        int maxSchedule = vectSchldSecondWithCategory.size();

                                        for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {

                                            boolean scheduleOff = false;

                                            Vector vectResult = (Vector) vectSchldSecondWithCategory.get(ls2nd);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                            scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                            if(scheduleOff == false){
                                                scd_2nd_key.add(scd.getSymbol());
                                                scd_2nd_value.add(String.valueOf(scd.getOID()));
                                            }

                                            if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))) {

                                                statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(statusSchedule){

                                                    value = String.valueOf(scd.getOID());
                                                    key = scd.getSymbol();

                                                }
                                                dSelect2nd[dS - 1] = String.valueOf(scd.getOID());

                                            }
                                        }

                                        if(statusSchedule){

                                            scd_2nd_value = new Vector(1, 1);
                                            scd_2nd_key = new Vector(1, 1);

                                            scd_2nd_key.add(key);
                                            scd_2nd_value.add(value);

                                        }                
                                    }
                                }
                                // combo first schedule
                                            //update by devin 2014-01-20 tgl 21,28
                                            Calendar ca1 = Calendar.getInstance();
                        Date ne=new Date();
                        ca1.set(ne.getYear()+1900, ne.getMonth()+1,ne.getDate());
                       ca1.setTime(ne);
                        int wik = ca1.get(Calendar.WEEK_OF_MONTH);

                        //System.returnData +="Week of Month :" + wk);
                        if(cekScheduleWeekly && dField[j] == dField[j] ){
                          Calendar ca12 = Calendar.getInstance();
                        Date nee=new Date();

                       ca12.setTime(tmpCalenderDt);
                        int wnnk = ca12.get(Calendar.WEEK_OF_MONTH);
                        if(tmpCalenderDt.getYear()<ne.getYear()){
                            if(tmpCalenderDt.getMonth()  - ne.getMonth()==11){
                                if(wik-wnnk==-4||wik-wnnk==-3||wik-wnnk==-2){

                                 returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "");

                            }else{
                                 returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "disabled");

                            }
                            }
                           else{
                                 returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "disabled");

                            }

                        }else if(tmpCalenderDt.getMonth() - ne.getMonth()==-2  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-3  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-4  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-5  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-6  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-7  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-8  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-9  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-10  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-11  && tmpCalenderDt.getYear() == ne.getYear()){

                     returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "disabled");

                    } else if(tmpCalenderDt.getMonth() - ne.getMonth()==-1  && tmpCalenderDt.getYear() == ne.getYear() ){
                    if(wik-wnnk==-3 ||wik-wnnk==-4){
                     returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "");

                    }else{

                     returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "disabled");
                    }

                    }else if(tmpCalenderDt.getMonth() < ne.getMonth()  && tmpCalenderDt.getYear() > ne.getYear()){
                           returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "");
                           }else if(tmpCalenderDt.getMonth() > ne.getMonth() && tmpCalenderDt.getYear() == ne.getYear()){
                             returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "");

                                   }else if(tmpCalenderDt.getYear()==ne.getYear()){
                          if(tmpCalenderDt.getMonth()==ne.getMonth()){
                            if(wik-wnnk==2||wik-wnnk==1 ||wik-wnnk==0||wik-wnnk==-1||wik-wnnk==-2||wik-wnnk==-3||wik-wnnk==-4 ){
                                returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "");
                                       }else{
                                             returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "disabled");
                                         }
                        }  
                        }


                           }else{
                               returnData +=ControlCombo.draw(dField[dS - 1], "","", dSelect[dS - 1], scd_value, scd_key, "");
                           }

                                // combo second schedule
                                if (dSelect2nd[dS - 1].equals("0")) {
                                    if (isCheckFirstSchedule) {
                                        returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\"");
                                    } else {
                                        int cat1stSchld = -1;
                                        if ((dSelect[dS - 1]).length() >= 18) {                        
                                            cat1stSchld = dCategory[dS-1];
                                        }

                                        if (cat1stSchld == PstScheduleCategory.CATEGORY_ABSENCE || cat1stSchld == PstScheduleCategory.CATEGORY_OFF || cat1stSchld == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || cat1stSchld == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || cat1stSchld == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                            returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\"");
                                        } else {
                                            returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[dS - 1]) + "','" + dS + "')\"");
                                        }
                                    }
                                } else {
                                    returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[dS - 1]) + "','" + dS + "')\"");
                                }

                               System.out.println("dS : " + dS + " - dField : " + dField[dS - 1]);
                              returnData +="</div>";
                    //           returnData +="</tr></table>");
                               dS++;
                               long tmpDtx = tmpCalenderDt.getTime() + (24 * 60 * 60 * 1000);
                                tmpCalenderDt = new Date(tmpDtx);
                           } else {
                               returnData +="&nbsp;";
                           }
                           returnData +="</td>";
                       } else {

                           returnData +="<td class=\"day\">";
                           if (i > nDayOfWeekStart-1) {

                               //returnData +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                               returnData +="<div class=\"date\">" + dS + "</div>";
                               returnData +="<div>";

                               boolean updateSchMnth = false;

                               ProcesDt.setDate(dS);

                               int diffMnth = SessEmpSchedule.DateDifferent(new Date(), ProcesDt);

                               if(diffMnth >= 0){
                                    updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_BEFORE_TIME, ProcesDt);
                               }else{
                                    updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_AFTER_TIME, ProcesDt);
                               }

                               //first schedule
                               Vector scd_value = new Vector(1, 1);
                               Vector scd_key = new Vector(1, 1);           

                               // second schedule
                               Vector scd_2nd_value = new Vector(1, 1);
                               Vector scd_2nd_key = new Vector(1, 1);

                               if(updateSchMnth == false && valUpdateScheduleByLevel.equals("ok")){

                                    if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                        for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){
                                            if(dS==26){
                                                System.out.println(dS);
                                            }
                                            Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                            ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                            if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))){
                                                scd_key.add(scd.getSymbol());
                                                scd_value.add(String.valueOf(scd.getOID()));

                                                dSelect[dS - 1] = String.valueOf(scd.getOID());
                                                dCategory[dS - 1] = scdCat.getCategoryType();
                                            }
                                        }
                                    }

                               }else{/* Jika status schedule bisa di ubah */

                                    scd_value.add("");
                                    scd_key.add("...");
                                    // first schedule
                                    if (vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0) {

                                        String value = "";
                                        String key = "";
                                        boolean statusSchedule = false;

                                        for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++) {

                                            boolean scheduleOff = false;

                                            Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                            ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                            scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                            if(scheduleOff == false){ //ada schedule hadir
                                                scd_key.add(scd.getSymbol());
                                                scd_value.add(String.valueOf(scd.getOID()));
                                            }

                                            if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))){

                                                statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(statusSchedule){///statusnya cuti AL

                                                    value = String.valueOf(scd.getOID());
                                                    key = scd.getSymbol();
                                                }

                                                dSelect[dS - 1] = String.valueOf(scd.getOID());
                                                dCategory[dS - 1] = scdCat.getCategoryType();
                                            }
                                        }

                                        if(statusSchedule){
                                            scd_value = new Vector(1, 1);
                                            scd_key = new Vector(1, 1);

                                            scd_key.add(key);
                                            scd_value.add(value);
                                        }
                                    }
                               }
                               // second schedule           
                               if(updateSchMnth == false && valUpdateScheduleByLevel.equals("ok")){

                                    if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                        for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                            Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                            if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))) {
                                                scd_2nd_key.add(scd.getSymbol());
                                                scd_2nd_value.add(String.valueOf(scd.getOID()));
                                            }
                                        }
                                    }

                               }else{/* Jika status schedule bisa di ubah */

                                    scd_2nd_value.add("");
                                    scd_2nd_key.add("...");

                                    if (vectSchldSecondWithCategory != null && vectSchldSecondWithCategory.size() > 0) {

                                        String value = "";
                                        String key = "";
                                        boolean statusSchedule = false;

                                        for (int ls2nd = 0; ls2nd < vectSchldSecondWithCategory.size(); ls2nd++) {

                                            boolean scheduleOff = false;

                                            Vector vectResult = (Vector) vectSchldSecondWithCategory.get(ls2nd);
                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                            scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                            if(scheduleOff == false){
                                                scd_2nd_key.add(scd.getSymbol());
                                                scd_2nd_value.add(String.valueOf(scd.getOID())); 
                                            }

                                            if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))) {
                                                statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(statusSchedule){
                                                    value = String.valueOf(scd.getOID());
                                                    key = scd.getSymbol();
                                                }

                                                dSelect2nd[dS - 1] = String.valueOf(scd.getOID());
                                            }
                                        }
                                        if(statusSchedule){
                                            scd_2nd_value = new Vector(1, 1);
                                            scd_2nd_key = new Vector(1, 1);

                                            scd_2nd_key.add(key);
                                            scd_2nd_value.add(value);
                                        }

                                    }
                               }

                    //update by devin 2014-01-20  
                    Calendar ca1 = Calendar.getInstance();
                        Date ne=new Date();
                        ca1.set(ne.getYear()+1900, ne.getMonth()+1, ne.getDate());
                       ca1.setTime(ne);
                        int wik = ca1.get(Calendar.WEEK_OF_MONTH);

                        //System.returnData +="Week of Month :" + wk);
                        if(cekScheduleWeekly && dField[j] == dField[j] ){
                          Calendar ca12 = Calendar.getInstance();
                        Date nee=new Date();
                         //update by devin 2014-01-20 1 tgl 23,24,25,26,27,30,31
                       ca12.setTime(tmpCalenderDt);
                        int wnnk = ca12.get(Calendar.WEEK_OF_MONTH);
                        if(tmpCalenderDt.getYear()<ne.getYear()){
                            if(tmpCalenderDt.getMonth()  - ne.getMonth()==11){
                                 if(wik-wnnk==-4||wik-wnnk==-3||wik-wnnk==-2){
                                returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "");
                            }else{
                              returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "disabled");  
                            }
                            }
                            else{
                              returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "disabled");  
                            }

                        }else if(tmpCalenderDt.getMonth() - ne.getMonth()==-2  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-3  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-4  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-5  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-6  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-7  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-8  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-9  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-10  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-11  && tmpCalenderDt.getYear() == ne.getYear()){

                     returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "disabled");

                    } else if(tmpCalenderDt.getMonth() - ne.getMonth()==-1  && tmpCalenderDt.getYear() == ne.getYear() ){
                    if(wik-wnnk==-3 ||wik-wnnk==-4){
                     returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "");

                    }else{

                     returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "disabled");
                    }

                    }else if(tmpCalenderDt.getMonth() < ne.getMonth()  && tmpCalenderDt.getYear() > ne.getYear()){
                         returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "");
                           }else if(tmpCalenderDt.getMonth() > ne.getMonth() && tmpCalenderDt.getYear()==ne.getYear() ){
                            returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "");
                                   }else if(tmpCalenderDt.getYear()==ne.getYear()){
                          if(tmpCalenderDt.getMonth()==ne.getMonth()){
                              if(wik-wnnk==2||wik-wnnk==1 ||wik-wnnk==0||wik-wnnk==-1||wik-wnnk==-2||wik-wnnk==-3||wik-wnnk==-4 ){
                               returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "");
                                           }else{
                                            returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "disabled");
                                           }
                        }  
                        }
                               }  else{
                             returnData +=ControlCombo.draw(dField[dS - 1], "elementForm", "",dSelect[dS - 1], scd_value, scd_key, "");
                               }


                               // combo second schedule
                               if (dSelect2nd[dS - 1].equals("0")){
                                   if (isCheckFirstSchedule) {
                                       returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], "", "", dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\"");
                                   } else {
                                       int cat1stSchld = -1;
                                       if ((dSelect[dS - 1]).length() >= 18) {                        
                                            cat1stSchld = dCategory[dS-1];
                                       }

                                       if (cat1stSchld == PstScheduleCategory.CATEGORY_ABSENCE || cat1stSchld == PstScheduleCategory.CATEGORY_OFF || cat1stSchld == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || cat1stSchld == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || cat1stSchld == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                           returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], "", "", dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\"");
                                       } else {
                                           returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], "", "", dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[dS - 1]) + "','" + dS + "')\"");
                                       }
                                   }
                               } else {
                                   returnData +="<br>" + ControlCombo.draw(dField2nd[dS - 1], "", "", dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[dS - 1]) + "','" + dS + "')\"");
                               }

                                returnData +="</div>";
                    //            returnData +="</tr></table>");
                                dS++;
                                long tmpDtx = tmpCalenderDt.getTime() + (24 * 60 * 60 * 1000);
                                tmpCalenderDt = new Date(tmpDtx);
                            } else {
                                returnData +="&nbsp;";
                            }
                            returnData +="</td>";
                        }
                        if ((i + 1) % 7 == 0) {
                            returnData +="<tr class=\"days\">";
                        }
                        if(dS>nDayOfMonthStart){
                            dS = 1;
                        }

                        long tmpDt = calenderDt.getTime() + (24 * 60 * 60 * 1000);
                        calenderDt = new Date(tmpDt);

                    }
                    int stopI = i;

                    if (dateStart>1) {

                        for (j = 0; j < dateEnd; j++, i++) {
                            if ((i % 7) == 0) {

                                returnData +="<td class=\"day\">";

                                if ((i > nDayOfWeekStart-1) && (i < dateEnd+stopI)){

                                    //returnData +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                    returnData +="<div class=\"date\">" + (j + 1) + "</div>";
                                    returnData +="<div>";

                                    boolean updateSchMnth = false;

                                    int dtSet = j+1;
                                    ProcesDt.setDate(dtSet);

                                    int diffMnth = SessEmpSchedule.DateDifferent(new Date(), ProcesDt);

                                    if(diffMnth >= 0){
                                        updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_BEFORE_TIME, ProcesDt);
                                    }else{
                                        updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_AFTER_TIME, ProcesDt);
                                    }

                                    //first schedule
                                    Vector scd_value = new Vector(1, 1);
                                    Vector scd_key = new Vector(1, 1);

                                    // second schedule
                                    Vector scd_2nd_value = new Vector(1, 1);
                                    Vector scd_2nd_key = new Vector(1, 1);

                                    if(updateSchMnth ==false && valUpdateScheduleByLevel.equals("ok")){

                                        if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                            for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))){
                                                    scd_key.add(scd.getSymbol());
                                                    scd_value.add(String.valueOf(scd.getOID()));

                                                    dSelect[dS - 1] = String.valueOf(scd.getOID());
                                                    dCategory[dS - 1] = scdCat.getCategoryType();
                                                }
                                            }
                                        }

                                    }else{

                                        scd_value.add("");
                                        scd_key.add("...");                    

                                        // First Schedule
                                        if (vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                            String value = "";
                                            String key = "";
                                            boolean statusSchedule = false;

                                            for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                                boolean scheduleOff = false;

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(scheduleOff == false){
                                                    scd_key.add(scd.getSymbol());
                                                    scd_value.add(String.valueOf(scd.getOID()));
                                                }

                                                if (dSelect[j].equals(String.valueOf(scd.getOID()))) {

                                                    statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                    if(statusSchedule){
                                                        value = String.valueOf(scd.getOID());
                                                        key = scd.getSymbol();
                                                    }

                                                    dSelect[j] = String.valueOf(scd.getOID());
                                                    dCategory[j] = scdCat.getCategoryType();
                                                }
                                            }

                                            if(statusSchedule){
                                                scd_value = new Vector(1, 1);
                                                scd_key = new Vector(1, 1);

                                                scd_key.add(key);
                                                scd_value.add(value);
                                            }                    
                                        }
                                    }

                                    if(updateSchMnth == false && valUpdateScheduleByLevel.equals("ok")){

                                        if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                            for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                                if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))) {
                                                    scd_2nd_key.add(scd.getSymbol());
                                                    scd_2nd_value.add(String.valueOf(scd.getOID()));
                                                }
                                            }
                                        }

                                    }else{

                                        scd_2nd_value.add("");
                                        scd_2nd_key.add("...");
                                        // Second Schedule
                                        if (vectSchldSecondWithCategory != null && vectSchldSecondWithCategory.size() > 0){

                                            String value = "";
                                            String key = "";
                                            boolean statusSchedule = false;

                                            int maxSchedule = vectSchldSecondWithCategory.size();

                                            for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {

                                                boolean scheduleOff = false;
                                                Vector vectResult = (Vector) vectSchldSecondWithCategory.get(ls2nd);

                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(scheduleOff == false){
                                                    scd_2nd_key.add(scd.getSymbol());
                                                    scd_2nd_value.add(String.valueOf(scd.getOID()));
                                                }

                                                if (dSelect2nd[j].equals(String.valueOf(scd.getOID()))) {

                                                    statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                    if(statusSchedule){
                                                        value = String.valueOf(scd.getOID());
                                                        key = scd.getSymbol();
                                                    }

                                                    dSelect2nd[j] = String.valueOf(scd.getOID());
                                                }
                                            }

                                            if(statusSchedule){
                                                scd_2nd_value = new Vector(1, 1);
                                                scd_2nd_key = new Vector(1, 1);

                                                scd_2nd_key.add(key);
                                                scd_2nd_value.add(value);
                                            }
                                        }
                                    }
                                     //update by devin  2014-01-20
                                    //mengatur tgl khusus hari minggu,,mulai hari tgl 1
                             Calendar ca1 = Calendar.getInstance();
                        Date ne=new Date();
                        ca1.set(ne.getYear()+1900, ne.getMonth()+1, ne.getDate());
                       ca1.setTime(ne);
                        int wik = ca1.get(Calendar.WEEK_OF_MONTH);

                        //System.returnData +="Week of Month :" + wk);
                        if(cekScheduleWeekly && dField[j] == dField[j] ){
                          Calendar ca12 = Calendar.getInstance();
                        Date nee=new Date();

                       ca12.setTime(tmpCalenderDt);
                        int wnnk = ca12.get(Calendar.WEEK_OF_MONTH);
                        if(tmpCalenderDt.getYear() < ne.getYear()){
                            if(tmpCalenderDt.getMonth()  - ne.getMonth()==11){
                                 if(wik-wnnk==-4||wik-wnnk==-3||wik-wnnk==-2){

                               returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "");

                       }
                           else{
                             returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "disabled");
                           } 
                            }
                             else{
                             returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "disabled");
                           } 

                        }else if(tmpCalenderDt.getMonth() - ne.getMonth()==-2  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-3  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-4  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-5  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-6  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-7  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-8  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-9  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-10  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-11  && tmpCalenderDt.getYear() == ne.getYear()){

                     returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "disabled");

                    }else if(tmpCalenderDt.getMonth() - ne.getMonth()==-1  && tmpCalenderDt.getYear() == ne.getYear() ){
                    if(wik-wnnk==-3 ||wik-wnnk==-4){
                     returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "");

                    }else{

                     returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "disabled");
                    }

                    }else if(tmpCalenderDt.getMonth() < ne.getMonth()  && tmpCalenderDt.getYear() > ne.getYear()){
                          returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "");
                           }else if(tmpCalenderDt.getMonth() > ne.getMonth()  && tmpCalenderDt.getYear() == ne.getYear()){
                             returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "");
                                   }else if(tmpCalenderDt.getMonth() == ne.getMonth() && tmpCalenderDt.getYear() == ne.getYear()){
                            if(wik-wnnk==2||wik-wnnk==1 ||wik-wnnk==0||wik-wnnk==-1||wik-wnnk==-2||wik-wnnk==-3 ||wik-wnnk==-4 ){

                               returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "");

                       }
                           else{
                             returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "disabled");
                           } 
                        }

                           }else{
                            returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "");
                           }
                                        //update by devin  2014-01-20 2 tgl 5,12,19          
                                    //returnData +=ControlCombo.draw(dField[j], "","", dSelect[j], scd_value, scd_key, "disabled"));

                                    //Combo second schedule
                                    if (dSelect2nd[j].equals("0")) {
                                        if (isCheckFirstSchedule) {
                                            returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\"");
                                        } else {
                                            int cat1stSchld = -1;
                                            if ((dSelect[j]).length() >= 18) {                           
                                                cat1stSchld = dCategory[j];
                                            }

                                            if (cat1stSchld == PstScheduleCategory.CATEGORY_ABSENCE || cat1stSchld == PstScheduleCategory.CATEGORY_OFF || cat1stSchld == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || cat1stSchld == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || cat1stSchld == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                                returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\"");
                                            } else {
                                                returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[j]) + "','" + dS + "')\"");
                                            }
                                        }
                                    } else {
                                        returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[j]) + "','" + dS + "')\"");
                                    }
                                    returnData +="</div>";
                                    //returnData +="</tr></table>");
                                    dS++;
                                    long tmpDtx = tmpCalenderDt.getTime() + (24 * 60 * 60 * 1000);
                                tmpCalenderDt = new Date(tmpDtx);
                                } else {
                                    returnData +="&nbsp;";
                                }
                                returnData +="</td>";
                            } else if ((i % 7) == 6) {

                               
                                if (i > (nDayOfWeekStart-1)&& (i < dateEnd + stopI)) {

                                    returnData +="<td class=\"day\">";
                                    //returnData +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                    returnData +="<div class=\"date\">" + (j + 1) + "</div>";
                                    returnData +="<div>";

                                    int DtCurent = j+1;

                                    boolean updateSchMnth = false;

                                    ProcesDt.setDate(DtCurent);

                                    int diffMnth = SessEmpSchedule.DateDifferent(new Date(), ProcesDt);

                                    if(diffMnth >= 0){
                                        updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_BEFORE_TIME, ProcesDt);
                                    }else{
                                        updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_AFTER_TIME, ProcesDt);
                                    }

                                    //first schedule
                                    Vector scd_value = new Vector(1, 1);
                                    Vector scd_key = new Vector(1, 1);

                                    // second schedule
                                    Vector scd_2nd_value = new Vector(1, 1);
                                    Vector scd_2nd_key = new Vector(1, 1);

                                     //update by satrya 2012-08-05
                                /*
                                * Desc: untuk melakukan check apakah itu cuti'nya jam'an atau full day
                                * iLeaveMinuteEnable = 1 maka cuti jam-jam'an
                                * */
                                int iLeaveMinuteEnable = 0;//hanya cuti full day jika fullDayLeave = 0
                                try{
                                    iLeaveMinuteEnable = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_MINUTE_ENABLE"));
                                }catch(Exception ex){System.out.println("Execption LEAVE_MINUTE_ENABLE: " + ex);}

                                    if(updateSchMnth ==false && valUpdateScheduleByLevel.equals("ok")){

                                        if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                            for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))){
                                                    scd_key.add(scd.getSymbol());
                                                    scd_value.add(String.valueOf(scd.getOID()));

                                                    dSelect[dS - 1] = String.valueOf(scd.getOID());
                                                    dCategory[dS - 1] = scdCat.getCategoryType();
                                                }
                                            }
                                        }

                                    }else{

                                        scd_value.add("");
                                        scd_key.add("...");
                                        // first schedule
                                        if (vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0) {

                                            String value = "";
                                            String key = "";
                                            boolean statusSchedule = false;

                                            int maxSchedule = vectScheduleWithCategory.size();

                                            for (int ls = 0; ls < maxSchedule; ls++) {

                                                boolean scheduleOff = false;

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(scheduleOff == false){
                                                    scd_key.add(scd.getSymbol());
                                                    scd_value.add(String.valueOf(scd.getOID())); 
                                                }

                                                if (dSelect[j].equals(String.valueOf(scd.getOID()))) {
                                                    statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                    if(statusSchedule){

                                                        value = String.valueOf(scd.getOID());
                                                        key = scd.getSymbol();

                                                    }
                                                    dSelect[j] = String.valueOf(scd.getOID()); 
                                                    dCategory[j] = scdCat.getCategoryType();
                                                }
                                            }

                                            if(statusSchedule){

                                                scd_value = new Vector(1, 1);
                                                scd_key = new Vector(1, 1);

                                                scd_key.add(key);
                                                scd_value.add(value);
                                            }
                                        }
                                    }

                                    if(updateSchMnth == false && valUpdateScheduleByLevel.equals("ok")){

                                        if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                            for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                                if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))) {
                                                    scd_2nd_key.add(scd.getSymbol());
                                                    scd_2nd_value.add(String.valueOf(scd.getOID()));
                                                }
                                            }
                                        }

                                    }else{

                                        scd_2nd_value.add("");
                                        scd_2nd_key.add("...");
                                        // second schedule
                                        if (vectSchldSecondWithCategory != null && vectSchldSecondWithCategory.size() > 0) {

                                            String value = "";
                                            String key = "";
                                            boolean statusSchedule = false;

                                            int maxSchedule = vectSchldSecondWithCategory.size();

                                            for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {

                                                boolean scheduleOff = false;

                                                Vector vectResult = (Vector) vectSchldSecondWithCategory.get(ls2nd);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                                scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(scheduleOff == false){
                                                    scd_2nd_key.add(scd.getSymbol());
                                                    scd_2nd_value.add(String.valueOf(scd.getOID())); 
                                                }

                                                if (dSelect2nd[j].equals(String.valueOf(scd.getOID()))) {

                                                    statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                    if(statusSchedule){

                                                        value = String.valueOf(scd.getOID());
                                                        key = scd.getSymbol();

                                                    }
                                                    dSelect2nd[j] = String.valueOf(scd.getOID()); 
                                                }
                                            }

                                            if(statusSchedule){
                                                scd_2nd_value = new Vector(1, 1);
                                                scd_2nd_key = new Vector(1, 1);

                                                scd_2nd_key.add(key);
                                                scd_2nd_value.add(value);
                                            }
                                        }
                                    }

                                    // combo first schedule                
                                    //update by devin 2014-01-20   tgl 4,11,18      
                    Calendar ca1 = Calendar.getInstance();
                        Date ne=new Date();
                        ca1.set(ne.getYear()+1900, ne.getMonth()+1, ne.getDate());
                       ca1.setTime(ne);
                        int wik = ca1.get(Calendar.WEEK_OF_MONTH);

                        //System.returnData +="Week of Month :" + wk);
                        if(cekScheduleWeekly && dField[j] == dField[j]){
                          Calendar ca12 = Calendar.getInstance();
                        Date nee=new Date();

                       ca12.setTime(tmpCalenderDt);
                        int wnnk = ca12.get(Calendar.WEEK_OF_MONTH);
                        if(tmpCalenderDt.getYear() < ne.getYear()){
                            if(tmpCalenderDt.getMonth()  - ne.getMonth()==11){
                                  if(wik-wnnk==-4||wik-wnnk==-3||wik-wnnk==-2 ){
                                   returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                            }else{
                             returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");


                        }
                            }
                             else{
                             returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");


                        }
                        }else if(tmpCalenderDt.getMonth() - ne.getMonth()==-2  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-3  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-4  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-5  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-6  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-7  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-8  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-9  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-10  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-11  && tmpCalenderDt.getYear() == ne.getYear()){

                      returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");
                    }else if(tmpCalenderDt.getMonth() - ne.getMonth()==-1  && tmpCalenderDt.getYear() == ne.getYear() ){
                    if(wik-wnnk==-3 ||wik-wnnk==-4){
                      returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");

                    }else{

                      returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");
                    }

                    }else if(tmpCalenderDt.getMonth() < ne.getMonth()  && tmpCalenderDt.getYear() > ne.getYear()){
                          returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                           }else if(tmpCalenderDt.getMonth() > ne.getMonth() && tmpCalenderDt.getYear() == ne.getYear()){
                             returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                                   }else if(tmpCalenderDt.getYear() == ne.getYear() && tmpCalenderDt.getMonth() == ne.getMonth()){
                             if(wik-wnnk==2||wik-wnnk==1 ||wik-wnnk==0 || wik-wnnk==-1||wik-wnnk==-2||wik-wnnk==-3||wik-wnnk==-4 ){
                                   returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                            }else{
                             returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");


                        }

                        }

                        }else{
                              returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");  
                        }





                                    // combo second schedule
                                    if (dSelect2nd[j].equals("0")) {
                                        if (isCheckFirstSchedule) {
                                            returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\"");
                                        } else {
                                            int cat1stSchld = -1;
                                            if ((dSelect[j]).length() >= 18) {
                                                //cat1stSchld = Integer.parseInt((dSelect[j]).substring(18));
                                                cat1stSchld = dCategory[j];
                                            }

                                            if (cat1stSchld == PstScheduleCategory.CATEGORY_ABSENCE || cat1stSchld == PstScheduleCategory.CATEGORY_OFF || cat1stSchld == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || cat1stSchld == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || cat1stSchld == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                                returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\"");
                                            } else {
                                                returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[j]) + "','" + dS + "')\"");
                                            }
                                        }
                                    } else {
                                        returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[j]) + "','" + dS + "')\"");
                                    }
                                    returnData +="</div>";
                                    //returnData +="</tr></table>");
                                    dS++;
                                  long tmpDtx = tmpCalenderDt.getTime() + (24 * 60 * 60 * 1000);
                                tmpCalenderDt = new Date(tmpDtx);


                                } else {
                                    returnData +="&nbsp;";
                                }
                                returnData +="</td>";
                            } else {
                                returnData +="<td class=\"day\">";

                                if ((i > nDayOfWeekStart-1) && (i < dateEnd + stopI)) {

                                    //returnData +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                    returnData +="<div class=\"date\">" + (j + 1) + "</div>";
                                    returnData +="<div>";

                                    int DtProces = j+1;

                                    boolean updateSchMnth = false;

                                    ProcesDt.setDate(DtProces);

                                    int diffMnth = SessEmpSchedule.DateDifferent(new Date(), ProcesDt);

                                    if(diffMnth >= 0){
                                        updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_BEFORE_TIME, ProcesDt);
                                    }else{
                                        updateSchMnth = SessEmpSchedule.getstatusSchedule(empLoginId, PstPosition.UPDATE_SCHEDULE_AFTER_TIME, ProcesDt);
                                    }

                                    //first schedule
                                    Vector scd_value = new Vector(1, 1);
                                    Vector scd_key = new Vector(1, 1);                

                                    // second schedule
                                    Vector scd_2nd_value = new Vector(1, 1);
                                    Vector scd_2nd_key = new Vector(1, 1);                

                                    if(updateSchMnth ==false && valUpdateScheduleByLevel.equals("ok")){

                                        if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                            for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                if (dSelect[dS - 1].equals(String.valueOf(scd.getOID()))){

                                                    scd_key.add(scd.getSymbol());
                                                    scd_value.add(String.valueOf(scd.getOID()));

                                                    dSelect[dS - 1] = String.valueOf(scd.getOID());
                                                    dCategory[dS - 1] = scdCat.getCategoryType();
                                                }
                                            }
                                        }

                                    }else{

                                        scd_value.add("");
                                        scd_key.add("...");
                                        // first schedule
                                        if (vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                            String value = "";
                                            String key = "";
                                            boolean statusSchedule = false;

                                            int maxSchedule = vectScheduleWithCategory.size();

                                            for (int ls = 0; ls < maxSchedule; ls++) {

                                                boolean scheduleOff = false;

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(scheduleOff == false){
                                                    scd_key.add(scd.getSymbol());
                                                    scd_value.add(String.valueOf(scd.getOID())); //+ String.valueOf(scdCat.getCategoryType()));
                                                }

                                                if (dSelect[j].equals(String.valueOf(scd.getOID()))) {
                                                    statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                    if(statusSchedule){
                                                        value = String.valueOf(scd.getOID());
                                                        key = scd.getSymbol();
                                                    }
                                                    dSelect[j] = String.valueOf(scd.getOID());
                                                    dCategory[j] = scdCat.getCategoryType();
                                                }
                                            }
                                            if(statusSchedule){
                                                scd_value = new Vector(1, 1);
                                                scd_key = new Vector(1, 1);

                                                scd_key.add(key);
                                                scd_value.add(value);
                                            }
                                        }
                                    }

                                    if(vectScheduleWithCategory != null && vectScheduleWithCategory.size() > 0){

                                            for (int ls = 0; ls < vectScheduleWithCategory.size(); ls++){

                                                Vector vectResult = (Vector) vectScheduleWithCategory.get(ls);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);

                                                if (dSelect2nd[dS - 1].equals(String.valueOf(scd.getOID()))) {
                                                    scd_2nd_key.add(scd.getSymbol());
                                                    scd_2nd_value.add(String.valueOf(scd.getOID()));
                                                }
                                            }
                                    }else{

                                        // second schedule
                                        if (vectSchldSecondWithCategory != null && vectSchldSecondWithCategory.size() > 0) {

                                            String value = "";
                                            String key = "";
                                            boolean statusSchedule = false;
                                               //update by satrya 2012-08-05
                                            /*
                                            * Desc: untuk melakukan check apakah itu cuti'nya jam'an atau full day
                                            * iLeaveMinuteEnable = 1 maka cuti jam-jam'an
                                            * */
                                            int iLeaveMinuteEnable = 0;//hanya cuti full day jika fullDayLeave = 0
                                            try{
                                                iLeaveMinuteEnable = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_MINUTE_ENABLE"));
                                            }catch(Exception ex){System.out.println("Execption LEAVE_MINUTE_ENABLE: " + ex);}


                                            int maxSchedule = vectSchldSecondWithCategory.size();

                                            for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {

                                                boolean scheduleOff = false;
                                                Vector vectResult = (Vector) vectSchldSecondWithCategory.get(ls2nd);
                                                ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);                            

                                                scheduleOff = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                if(scheduleOff == false){
                                                    scd_2nd_key.add(scd.getSymbol());
                                                    scd_2nd_value.add(String.valueOf(scd.getOID()));
                                                }

                                                if (dSelect2nd[j].equals(String.valueOf(scd.getOID()))) {
                                                    statusSchedule = SessEmpSchedule.getStatusSchedule(scd.getOID());

                                                    if(statusSchedule){
                                                        value = String.valueOf(scd.getOID());
                                                        key = scd.getSymbol();
                                                    }

                                                    dSelect2nd[j] = String.valueOf(scd.getOID());
                                                }
                                            }

                                            if(statusSchedule){

                                                scd_2nd_value = new Vector(1, 1);
                                                scd_2nd_key = new Vector(1, 1);

                                                scd_2nd_key.add(key);
                                                scd_2nd_value.add(value);
                                            }
                                        }
                                    }

                                    // combo first schedule                
                                    //update by devin  2014-01-20 tgl 1,2,3,6,7,8,9,10,13,14,15,16,17,,20
                                     Calendar ca1 = Calendar.getInstance();
                        Date ne=new Date();
                        ca1.set(ne.getYear()+1900, ne.getMonth()+1, ne.getDate());
                       ca1.setTime(ne);
                        int wik = ca1.get(Calendar.WEEK_OF_MONTH);


                        //System.returnData +="Week of Month :" + wk);
                        if(cekScheduleWeekly && dField[j] == dField[j] ){
                          Calendar ca12 = Calendar.getInstance();
                        Date nee=new Date();

                       ca12.setTime(tmpCalenderDt);
                        int wnnk = ca12.get(Calendar.WEEK_OF_MONTH);
                        if(tmpCalenderDt.getYear() < ne.getYear()){
                             if(tmpCalenderDt.getMonth()  - ne.getMonth()==11){
                                  if(wik-wnnk==-4||wik-wnnk==-3||wik-wnnk==-2){


                                    returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                                                   }else{
                             returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");
                                                   } 
                             }
                           else{
                             returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");
                                                   } 
                        }else if(tmpCalenderDt.getMonth() - ne.getMonth() ==-2  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-3  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-4  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-5  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-6  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-7  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-8  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-9  && tmpCalenderDt.getYear() == ne.getYear() ||
                    tmpCalenderDt.getMonth() - ne.getMonth()==-10  && tmpCalenderDt.getYear() == ne.getYear() ||tmpCalenderDt.getMonth() - ne.getMonth()==-11  && tmpCalenderDt.getYear() == ne.getYear()){

                     returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");

                    }else if(tmpCalenderDt.getMonth() - ne.getMonth()==-1  && tmpCalenderDt.getYear() == ne.getYear() ){
                    if(wik-wnnk==-3 ||wik-wnnk==-4){
                         returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");

                    }else{

                         returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");
                    }

                    }else if(tmpCalenderDt.getMonth() < ne.getMonth()  && tmpCalenderDt.getYear() > ne.getYear()){
                        returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                           }else if(tmpCalenderDt.getMonth() > ne.getMonth()  && tmpCalenderDt.getYear() == ne.getYear()){
                              returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                                   }else if(tmpCalenderDt.getYear() == ne.getYear() && tmpCalenderDt.getMonth() == ne.getMonth()){
                             if(wik-wnnk==2||wik-wnnk==1 ||wik-wnnk==0||wik-wnnk==-1||wik-wnnk==-2||wik-wnnk==-3||wik-wnnk==-4 ){


                                    returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                                                   }else{
                             returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "disabled");
                                                   }


                        }

                                  }else{
                              returnData +=ControlCombo.draw(dField[j], "", "", dSelect[j], scd_value, scd_key, "");
                                  }
                                    // combo second schedule
                                    if (dSelect2nd[j].equals("0")) {
                                        if (isCheckFirstSchedule) {
                                            returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\"");
                                        } else {
                                            int cat1stSchld = -1;
                                            if ((dSelect[j]).length() >= 18) {

                                                cat1stSchld = dCategory[j];
                                            }

                                            if (cat1stSchld == PstScheduleCategory.CATEGORY_ABSENCE || cat1stSchld == PstScheduleCategory.CATEGORY_OFF || cat1stSchld == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT || cat1stSchld == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE || cat1stSchld == PstScheduleCategory.CATEGORY_LONG_LEAVE) {
                                                returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\"");
                                            } else {
                                                returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[j]) + "','" + dS + "')\"");
                                            }
                                        }
                                    } else {
                                        returnData +="<br>" + ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'" + (dSelect2nd[j]) + "','" + dS + "')\"");
                                    }
                                    returnData +="</div>";
                                    //returnData +="</tr></table>");
                                    dS++;
                                    long tmpDtx = tmpCalenderDt.getTime() + (24 * 60 * 60 * 1000);
                                tmpCalenderDt = new Date(tmpDtx);
                                } else {
                                    returnData +="&nbsp;";
                                }
                                returnData +="</td>";
                            }
                            if ((i + 1) % 7 == 0) {
                                returnData +="<tr>";
                            }
                        }
                    }
                    int addCell = 7-(i%7);
                    if(addCell==7){addCell=0;}
                    System.out.println("ADD CELL :::::::: "+addCell);
                    for (int d = 0; d < addCell; d++) {
                        if ((d ) == addCell-1) {
                            returnData +="<td class=\"day\">"

                                    +"</td>";
                        } else {
                            returnData +="<td class=\"day\">"

                                    +"</td>";
                        }
                    }
            
            
            
                    returnData += "</div>";
        
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
