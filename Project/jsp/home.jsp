
<%@page import="com.dimata.harisma.entity.search.SrcEmpReprimand"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmpDocumentStatus"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployee"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain"%>
<%@page import="javax.swing.text.Style"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.qdep.system.*" %>
<%@ page import = "com.dimata.harisma.session.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*"%>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.form.admin.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.ll.*" %>
<%@ page import = "com.dimata.harisma.session.leave.ll.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGIN); %>
<%@ include file = "main/checkuser.jsp" %>
<%
            int appObjCodeMainMenu = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_MENU, AppObjInfo.OBJ_MENU);
            int appObjCodeMenuEmployee = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_MENU, AppObjInfo.OBJ_MENU_EMPLOYEE);
            
            int objCodeEmployeeSchedule = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE);

            boolean privViewMainMenu = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeMainMenu, AppObjInfo.COMMAND_VIEW));
            boolean privViewMenuEmployee = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeMenuEmployee, AppObjInfo.COMMAND_VIEW));
            boolean privViewChangePinNPasword =  userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeMenuEmployee, AppObjInfo.COMMAND_VIEW));          
            if(privViewMainMenu == false && privViewMenuEmployee == false){
                privViewMainMenu = true;
                privViewMenuEmployee = false;
            }
            
            boolean privAddSchedule = userSession.checkPrivilege(AppObjInfo.composeCode(objCodeEmployeeSchedule, AppObjInfo.COMMAND_ADD));
            boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;             
            long hrdDepartmentOid = 0;
            try{
                hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT))); 
            }catch(Exception exc){
                out.println("System Property : OID_HRD_DEPARTMENT is not set, please go to menu system property and set");
            }
            boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
            
            long edpSectionOid = 0;
            try{
                edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION))); 
            }catch(Exception exc){
                out.println("System Property : OID_EDP_SECTION is not set, please go to menu system property and set");
            }
            
            //update by satrya 2013-12-23
            boolean viewChangePassword=false;
            try{
                viewChangePassword = Boolean.parseBoolean(String.valueOf(PstSystemProperty.getValueByName("VIEW_CHANGE_PASSWORD")));  
            }catch(Exception exc){
                out.println("System Property : VIEW_CHANGE_PASSWORD is not set, please go to menu system property and set");
            }
            
            boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
            boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
            
            response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT"); 
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "nocache");

            int TYPE_SAVE = 1;            
           
%>
<!-- Jsp Block -->
<%! 



//update by priska 2014-11-06
SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();

FrmSrcSpecialEmployeeQuery frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery();

public int getLeaveSchType(Hashtable hLeave, long leaveOid){
	
        int type = -1;
	
	String key = String.valueOf(leaveOid);

        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        
	hSysLeaveDP = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT));
	hSysLeaveSP = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE));
	hSysLeaveLL = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE));
	hSysLeaveAL = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE));
        
        if(hSysLeaveDP.containsKey(key)){
		return PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT;
	}else if(hSysLeaveSP.containsKey(key)){
		return PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
	}else if(hSysLeaveLL.containsKey(key)){
		return PstScheduleCategory.CATEGORY_LONG_LEAVE;
	}else if(hSysLeaveAL.containsKey(key)){
		return PstScheduleCategory.CATEGORY_ANNUAL_LEAVE;
	}
        
	return type;
}

public String drawListScheduleCheck(JspWriter outObj, Vector objectClass)
{	
	Date now = new Date();	
	int monthStartDate = Integer.parseInt(String.valueOf(now.getMonth()));
	int yearStartDate =  Integer.parseInt(String.valueOf(now.getYear()+1900));
	int dateStartDate =  Integer.parseInt(String.valueOf(now.getDate()));
	int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	        
        ctrlist.dataFormat("PERIOD","8%","center","center");
        ctrlist.dataFormat("EMPLOYEE","8%","center","center");
        
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
        
	//untuk period
	for (int i = 0; i < objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);		
		Date periodStartDate = period.getStartDate();
		monthStartDate = periodStartDate.getMonth()+1;
		yearStartDate =  periodStartDate.getYear()+1900;
		dateStartDate =  periodStartDate.getDate();		
	}	
	
	if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
	{		
	
                ctrlist.addHeader("21","2%");
		ctrlist.addHeader("22","2%");
		ctrlist.addHeader("23","2%");
		ctrlist.addHeader("24","2%");
		ctrlist.addHeader("25","2%");
		ctrlist.addHeader("26","2%");
		ctrlist.addHeader("27","2%");
		ctrlist.addHeader("28","2%");
		ctrlist.addHeader("29","2%");
		ctrlist.addHeader("30","2%");
		ctrlist.addHeader("31","2%");
		ctrlist.addHeader("1","2%");
		ctrlist.addHeader("2","2%");
		ctrlist.addHeader("3","2%");
		ctrlist.addHeader("4","2%");
		ctrlist.addHeader("5","2%");
		ctrlist.addHeader("6","2%");
		ctrlist.addHeader("7","2%");
		ctrlist.addHeader("8","2%");
		ctrlist.addHeader("9","2%");
		ctrlist.addHeader("10","2%");
		ctrlist.addHeader("11","2%");
		ctrlist.addHeader("12","2%");
		ctrlist.addHeader("13","2%");
		ctrlist.addHeader("14","2%");
		ctrlist.addHeader("15","2%");
		ctrlist.addHeader("16","2%");
		ctrlist.addHeader("17","2%");
		ctrlist.addHeader("18","2%");
		ctrlist.addHeader("19","2%");				
		ctrlist.addHeader("20","2%");
                
	}else{	
		
		GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
                int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);	
		
                ctrlist.dataFormat(""+startDatePeriod,"2%","center","center");
		for(int i = 0 ; i < maxDayOfMonth-1 ; i++) {
				if(startDatePeriod == maxDayOfMonth){
					startDatePeriod = 1;
					
                                        ctrlist.dataFormat(""+startDatePeriod,"2%","center","center");
				}
				else {
					startDatePeriod = startDatePeriod +1;
                                        ctrlist.dataFormat(""+startDatePeriod,"2%","center","center");
					
				}
				
		}
	}
        
        ctrlist.dataFormat("PROCESS","2%","center","center");
        
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEditSchedule('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	Hashtable scheduleSymbol = new Hashtable();
	Vector listScd = PstScheduleSymbol.list(0, 0, "", "");
	scheduleSymbol.put("0", "-");
        
	for (int ls = 0; ls < listScd.size(); ls++) 
	{
		ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
		scheduleSymbol.put(String.valueOf(scd.getOID()), scd.getSymbol());
	}
	int index = -1;
        
	startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));// POINT						
	startDatePeriod = startDatePeriod -1;
	GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate); // POINT	
        int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); // POINT	
        
	for (int i = 0; i < objectClass.size(); i++){
            
		Vector temp = (Vector) objectClass.get(i);
                EmpSchedule empSchedule = (EmpSchedule)temp.get(0);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);				
		                
                long employeeId = employee.getOID();
		Date periodStartDate = period.getStartDate();
                
                EmpSchedule objEmpSchedule = new EmpSchedule();
                
                try{
                    objEmpSchedule = PstEmpSchedule.listSchedule(employeeId,periodStartDate);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
		String strFullName = employee.getFullName();

		Vector rowx = new Vector();		
		rowx.add("<input type=\"hidden\" name=\"size\" value=\""+empSchedule.getOID()+ "\">"+"<input type=\"hidden\" name=\"emp_schedule_id_"+i+"\" value=\""+empSchedule.getOID()+ "\">"+period.getPeriod());
		rowx.add(strFullName);
		
		if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
		{
                    //Not define yet
                    
		}else{			
			String strScheduleSymbol ="";
			long idScheduleSymbol1 =0;
			long idScheduleSymbol2 =0;
                        
			for(int j = 0 ; j < maxDayOfMonth; j++){
                                
				if(startDatePeriod == maxDayOfMonth){
                                    
					startDatePeriod = 1;
                                        
                                        ScheduleD1D2 scheduleD1D2 = new ScheduleD1D2();
                                        
                                        scheduleD1D2 = PstEmpSchedule.getSch(objEmpSchedule,startDatePeriod);
                                        
                                        if(scheduleD1D2 != null){
                                            try{
                                                idScheduleSymbol1 = scheduleD1D2.getD();
                                            }catch(Exception e){
                                                idScheduleSymbol1 = 0;
                                            }
                                        
                                            try{
                                                idScheduleSymbol2 = scheduleD1D2.getD2Nd();
                                            }catch(Exception e){
                                                idScheduleSymbol2 = 0;
                                            }
                                        }
					
					strScheduleSymbol = ""+scheduleSymbol.get(""+idScheduleSymbol1) + (idScheduleSymbol2==0 ? "" : "/"+scheduleSymbol.get(""+idScheduleSymbol2));
					
                                        int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);
                                        
                                        if(typeSymbol>0)
					{
						
						if(typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){
							
                                                    strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";												
						}
                                                
                                                
						if(typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE){                                                    
                                                    strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";	
                                                }
                                                
                                                if(typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE){
                                                       
                                                    strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";
                                                                
                                                }
					}
					rowx.add(strScheduleSymbol);
	
				}else {
					
                                        startDatePeriod = startDatePeriod +1;
                                        ScheduleD1D2 scheduleD1D2 = new ScheduleD1D2();
                                        
                                        scheduleD1D2 = PstEmpSchedule.getSch(objEmpSchedule,startDatePeriod);
                                        
                                        if(scheduleD1D2 != null){
                                            try{
                                                idScheduleSymbol1 = scheduleD1D2.getD();
                                            }catch(Exception e){
                                                idScheduleSymbol1 = 0;
                                            }
                                        
                                            try{
                                                idScheduleSymbol2 = scheduleD1D2.getD2Nd();
                                            }catch(Exception e){
                                                idScheduleSymbol2 = 0;
                                            }
                                        }
					strScheduleSymbol = ""+scheduleSymbol.get(""+idScheduleSymbol1) + (idScheduleSymbol2==0 ? "" : "/"+scheduleSymbol.get(""+idScheduleSymbol2));
					
                                        int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);
                                       
                                        if(typeSymbol>0){
                                                			
						if(typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){                                                       
                                                        
							strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";												
                                                        
						}
                                                
						if(typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE){
                                                    
                                                    strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";	
                                                }
                                                
                                                if(typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE ){
                                                    
                                                	strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";
                                                        
                                                }
                                                
					}
					rowx.add(strScheduleSymbol);
				}
				}
												
		}
                rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\"></center>");
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(empSchedule.getOID()));
	}	
        try{
            ctrlist.drawMe(outObj,index);
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
        return "";
}
%>


<%!
    public String drawList(Vector objectClass){
        
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("BIRTHDAY", "6%");
        ctrlist.addHeader("PAYROLL", "5%");
        ctrlist.addHeader("NAME", "12%");
        ctrlist.addHeader("SEX", "4%");
        ctrlist.addHeader("RELIGION", "4%");
        ctrlist.addHeader("DEPARTMENT", "10%");
        ctrlist.addHeader("POSITION", "10%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        for (int i = 0; i < objectClass.size(); i++) {
            
            Vector temp = (Vector) objectClass.get(i);
            Employee employee = (Employee) temp.get(0);
            Department department = (Department) temp.get(1);
            Position position = (Position) temp.get(2);
            Level level = (Level) temp.get(5);
            Religion religion = (Religion) temp.get(6);
            Marital marital = (Marital) temp.get(7);

            Vector rowx = new Vector();
            rowx.add(String.valueOf(Formater.formatDate(employee.getBirthDate(), "dd MMM")));
            rowx.add(employee.getEmployeeNum());
            rowx.add(employee.getFullName());
            rowx.add(PstEmployee.sexKey[employee.getSex()]);
            rowx.add(religion.getReligion());
            rowx.add(department.getDepartment());
            rowx.add(position.getPosition());

            lstData.add(rowx);
        
        }
        return ctrlist.draw();
    }
    
    //priska ended contract 2014-11-08
public String drawListEndedcontract(Vector objectClass, int st){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No.","2%");
	ctrlist.addHeader("Payroll","6%");
	ctrlist.addHeader("Name","15%");
	ctrlist.addHeader("Departemen","18%");
	ctrlist.addHeader("Emp. Cat","10%");
	ctrlist.addHeader("Level","15%");
	ctrlist.addHeader("Position","5%");
        
        ctrlist.addHeader("Grade","15%");
	ctrlist.addHeader("Com. date","10%");
	ctrlist.addHeader("End Contract","10%");
	ctrlist.setLinkRow(1);
	//ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEditEmp('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	for (int i = 0; i < objectClass.size(); i++) {
		
                SessTmpSpecialEmployee sessTmpSpecialEmployee =  (SessTmpSpecialEmployee) objectClass.get(i);
        try{

		Vector rowx = new Vector();
		rowx.add(String.valueOf(st + 1 + i));
		rowx.add(sessTmpSpecialEmployee.getEmployeeNum());
		rowx.add(sessTmpSpecialEmployee.getFullName());
		rowx.add(sessTmpSpecialEmployee.getDepartement()!=null && sessTmpSpecialEmployee.getDepartement().length()>0 && !sessTmpSpecialEmployee.getDepartement().equalsIgnoreCase("null") ?sessTmpSpecialEmployee.getDepartement():"-");
		rowx.add(sessTmpSpecialEmployee.getEmpCategory()!=null && sessTmpSpecialEmployee.getEmpCategory().length()>0 && !sessTmpSpecialEmployee.getEmpCategory().equalsIgnoreCase("null") ?sessTmpSpecialEmployee.getEmpCategory():"-");
		rowx.add(sessTmpSpecialEmployee.getLevel()!=null && sessTmpSpecialEmployee.getLevel().length()>0 && !sessTmpSpecialEmployee.getLevel().equalsIgnoreCase("null") ?sessTmpSpecialEmployee.getLevel():"-");
		rowx.add(sessTmpSpecialEmployee.getPosition()!=null && sessTmpSpecialEmployee.getPosition().length()>0 && !sessTmpSpecialEmployee.getPosition().equalsIgnoreCase("null") ?sessTmpSpecialEmployee.getPosition():"-");
		
                rowx.add(sessTmpSpecialEmployee.getGrade()!=null && sessTmpSpecialEmployee.getGrade().length()>0 && !sessTmpSpecialEmployee.getGrade().equalsIgnoreCase("null") ?sessTmpSpecialEmployee.getGrade():"-");
		
                rowx.add(sessTmpSpecialEmployee.getCommercingDateEmployee()==null?"":"<nobr>" + Formater.formatDate(sessTmpSpecialEmployee.getCommercingDateEmployee(),"dd MMM yyyy") + "</nobr>");
		//ended
                rowx.add(sessTmpSpecialEmployee.getEndContractEmployee()==null?"":"<nobr>" + Formater.formatDate(sessTmpSpecialEmployee.getEndContractEmployee(),"dd MMM yyyy") + "</nobr>");
		
       		lstData.add(rowx);
		lstLinkData.add(String.valueOf(sessTmpSpecialEmployee.getEmployeeId()));
                           }catch(Exception exc){
                               System.out.println("Exception " + exc + " EmpNum "+sessTmpSpecialEmployee.getEmployeeNum());
                           }
	}
	return ctrlist.draw();
}
/*
 * Update by Hendra McHen
 * Date : 2014-11-21
 * Description : drawListReprimand
 */
 
    public String drawListReprimand(Vector objectClass, long empReprimandId) {

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("90%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");

        ctrlist.addHeader("NO.", "3%", "align='center'");
        ctrlist.addHeader("Emp Num", "10%");
        ctrlist.addHeader("Employee Name", "10%");
        ctrlist.addHeader("DESCRIPTION", "20%");
        ctrlist.addHeader("DATE", "10%");
        ctrlist.addHeader("VALID UNTIL", "10%");



        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.reset();
        int index = -1;
        int recordNo = 1;

        for (int i = 0; i < objectClass.size(); i++) {


            EmpReprimand empReprimand = (EmpReprimand) objectClass.get(i);
            Vector rowx = new Vector();
            if (empReprimandId == empReprimand.getOID()) {
                index = i;
            }

            Reprimand reprimand = new Reprimand();
            if (empReprimand.getReprimandLevelId() != -1) {
                try {
                    reprimand = PstReprimand.fetchExc(empReprimand.getReprimandLevelId());
                } catch (Exception exc) {
                    reprimand = new Reprimand();
                }
            }
            // gunakan try catch untuk meng-akses Persistent
            Employee emp = new Employee();
            if (empReprimand.getEmployeeId() != 0 && empReprimand.getEmployeeId() > 0) {
                try {
                    emp = PstEmployee.fetchExc(empReprimand.getEmployeeId());
                } catch (Exception e) {
                    //
                }
            }



            rowx.add(String.valueOf(recordNo++));
            rowx.add(emp.getEmployeeNum());
            rowx.add(emp.getFullName());
            rowx.add((empReprimand.getDescription().length() > 100) ? empReprimand.getDescription().substring(0, 100) + " ..." : empReprimand.getDescription());
            rowx.add(Formater.formatDate(empReprimand.getReprimandDate(), "d-MMM-yyyy"));
            rowx.add(Formater.formatDate(empReprimand.getValidityDate(), "d-MMM-yyyy"));


            lstData.add(rowx);

        }

        return ctrlist.draw(index);

    }
%>

<%!    public String drawListApproval(JspWriter outObj,Vector objectClass,long employeeId){
        I_Leave leaveConfig = null;  
            
    try {
        leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
    }
    catch(Exception e) {
        System.out.println("Exception : " + e.getMessage());
    }


    String useLongLeave ="";
    boolean bUseLL = false;
    try{
        useLongLeave = String.valueOf(PstSystemProperty.getValueByName("USE_LONG_LEAVE"));  
    }catch(Exception E){
        useLongLeave= "1";
        System.out.println("EXCEPTION SYS PROP USE_LONG_LEAVE : "+E.toString());
    }

    if( (useLongLeave==null || useLongLeave.equals("1"))  ){                           
        bUseLL = true;
    }

    
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");        
        
        ctrlist.dataFormat("PAYROLL","3%","center","center");
        ctrlist.dataFormat("EMPLOYEE","10%","center","center");
        ctrlist.dataFormat("DEPARTMENT","10%","center","center");
        ctrlist.dataFormat("DATE OF APPLICATION","10%","center","center");        
        ctrlist.dataFormat("DOC STATUS","10%","center","center");        
        ctrlist.dataFormat("APPROVED BY","10%","center","center");        
        ctrlist.dataFormat("HR APPROVED BY","10%","center","center");        
        if(leaveConfig.isLeaveApprovalLevel(I_Leave.LEAVE_APPROVE_3)){
         ctrlist.dataFormat("GM. APPROVAL","10%","center","center");   
        }            
        ctrlist.dataFormat("ANNUAL LEAVE","4%","center","center");        
        if(bUseLL){
            ctrlist.dataFormat("LONG LEAVE","4%","center","center");        
        }
        ctrlist.dataFormat("DAY OFF PAYMENT","4%","center","center");        
        ctrlist.dataFormat("SPECIAL LEAVE","4%","center","center");        
        ctrlist.dataFormat("UNPAID LEAVE","4%","center","center");   
        ctrlist.dataFormat("Select to  <br> PROCESS <br><a href=\"Javascript:SetAllCheckBoxes('frm','data_app_process', true,'"+ objectClass.size() +"')\">All</a> | <a href=\"Javascript:SetAllCheckBoxes('frm','data_app_process', false,'"+ objectClass.size() +"')\">Deselect All</a> ","4%","center","center");
                   
       // ctrlist.dataFormat("PROCESS","4%","center","center");        
        
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        int index = -1;
        
        for (int i = 0; i < objectClass.size(); i++) {
            
            Vector temp = (Vector) objectClass.get(i);

            Employee employee = (Employee) temp.get(0);
            LeaveApplication objleaveApplication = (LeaveApplication) temp.get(1);
            Department department = (Department) temp.get(2);

            String strSubmissionDate = "";
            try {
                Date dt_SubmitDate = objleaveApplication.getSubmissionDate();
                if (dt_SubmitDate == null) {
                    dt_SubmitDate = new Date();
                }
                strSubmissionDate = Formater.formatDate(dt_SubmitDate, "MMM dd, yyyy");
            } catch (Exception e) {
                strSubmissionDate = "";
            }

            boolean statusSchedule = SessLeaveApplication.CekScheduleExist(objleaveApplication.getOID());
            
            String strApproval = "";

            Vector rowx = new Vector();
            String statusDoc = SessLeaveApplication.getStatusDocument(objleaveApplication.getDocStatus());
            if (objleaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED) {
                rowx.add("" + employee.getEmployeeNum());
            } else {
                rowx.add("<a href=\"javascript:cmdEdit('" + objleaveApplication.getOID() + "','" + employee.getOID() + "')\">" + employee.getEmployeeNum() + "</a>");
            }
            
            if(statusSchedule == true){
                rowx.add("<input type=\"hidden\" name=\"size\" value=\""+objleaveApplication.getOID()+ "\">"+
                    "<input type=\"hidden\" name=\"leave_app_"+i+"\" value=\""+objleaveApplication.getOID()+ "\">"+
                    "<input type=\"hidden\" name=\"employee_approval_"+i+"\" value=\""+employeeId+ "\">"+
                    employee.getFullName());
            }else{
                rowx.add("<input type=\"hidden\" name=\"size\" value=\""+objleaveApplication.getOID()+ "\">"+
                    "<input type=\"hidden\" name=\"leave_app_"+i+"\" value=\""+objleaveApplication.getOID()+ "\">"+
                    "<input type=\"hidden\" name=\"employee_approval_"+i+"\" value=\""+employeeId+ "\">"+
                    "<font color=FF0000>"+employee.getFullName()+"</font>");
            }
            
            rowx.add(department.getDepartment());
            String typeleave = SessLeaveApplication.typeLeave(objleaveApplication.getOID());            
            rowx.add(strSubmissionDate);

            if (statusDoc != null) {
                rowx.add("" + statusDoc);
            } else {
                rowx.add("");
            }

            String depHead = SessLeaveApplication.getEmployeeApp(objleaveApplication.getDepHeadApproval());
            String hrMan = SessLeaveApplication.getEmployeeApp(objleaveApplication.getHrManApproval());
            String gM = SessLeaveApplication.getEmployeeApp(objleaveApplication.getGmApproval());

            if (depHead != null) {
                rowx.add(depHead);
            } else {
                rowx.add("");
            }
            if (hrMan != null) {
                rowx.add(hrMan);
            } else {
                rowx.add("");
            }
            if(leaveConfig.isLeaveApprovalLevel(I_Leave.LEAVE_APPROVE_3)){
            if (gM != null) {
                rowx.add(gM);
            } else {
                rowx.add("");
            }
            }
            
            String tknAl = SessLeaveApplication.tknAL(objleaveApplication.getOID(), employee.getOID());
            String tknLl = SessLeaveApplication.tknLL(objleaveApplication.getOID(), employee.getOID());
            String tknDp = SessLeaveApplication.tknDP(objleaveApplication.getOID(), employee.getOID());
            String tknSp = SessLeaveApplication.TknSP(objleaveApplication.getOID(), employee.getOID());
            String tknUp = SessLeaveApplication.tknUnpLeave(objleaveApplication.getOID(), employee.getOID());
            
            rowx.add("" + tknAl);
            if(bUseLL){
             rowx.add("" + tknLl);
            }
            rowx.add("" + tknDp);
            rowx.add("" + tknSp);
            rowx.add("" + tknUp);
            
            boolean mustApp = false;
            
            try{
                mustApp = SessLeaveApplication.getMustApprove(objleaveApplication, employeeId);
            }catch(Exception E){
                System.out.println("Exception "+E.toString());
            }
            
            if(mustApp == true){ /*jika harus app */
                if(statusSchedule == true){ /* jika schedule sudah di create */
                    rowx.add("<center><input type=\"checkbox\" name=\"data_app_process"+i+"\" value=\"1\"></center>");
                }else{
                    rowx.add("<center><input type=\"checkbox\" disabled=\"true\" name=\"data_app_process"+i+"\" value=\"0\"></center>");                
                }
                
            }else{
                rowx.add("<center><input type=\"checkbox\" disabled=\"true\" name=\"data_app_process"+i+"\" value=\"0\"></center>");                
            }
            lstData.add(rowx);
        }
        
        try{
            ctrlist.drawMe(outObj,index);
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
        return "";
        
    }
%>

<%!    public String drawListMyLeaveApplication(Vector objectClass) {
    
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");

        ctrlist.addHeader("<center>PAYROLL</center>", "5%");
        ctrlist.addHeader("<center>EMPLOYEE</center>", "10%");
        ctrlist.addHeader("<center>DEPARTMENT</center>", "10%");        
        ctrlist.addHeader("<center>DATE OF APPLICATION</center>", "10%");
        ctrlist.addHeader("<center>DOC. STATUS</center>", "8%");
        ctrlist.addHeader("<center>DEP. HEAD APPROVAL</center>", "10%");
        ctrlist.addHeader("<center>HR. MANAGER APPROVAL</center>", "10%");
        ctrlist.addHeader("<center>GM. APPROVAL", "10%");
        ctrlist.addHeader("<center>ANNUAL LEAVE</center>", "4%");
        ctrlist.addHeader("<center>LONG LEAVE</center>", "4%");
        ctrlist.addHeader("<center>DAY OF PAYMENT</center>", "4%");
        ctrlist.addHeader("<center>SPECIAL LEAVE</center>", "4%");
        ctrlist.addHeader("<center>UNPAID LEAVE</center>", "4%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEditMyLeave('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        for (int i = 0; i < objectClass.size(); i++) {
            Vector temp = (Vector) objectClass.get(i);

            Employee employee = (Employee) temp.get(0);
            LeaveApplication objleaveApplication = (LeaveApplication) temp.get(1);
            Department department = (Department) temp.get(2);

            String strSubmissionDate = "";
            try {
                Date dt_SubmitDate = objleaveApplication.getSubmissionDate();
                if (dt_SubmitDate == null) {
                    dt_SubmitDate = new Date();
                }
                strSubmissionDate = Formater.formatDate(dt_SubmitDate, "MMM dd, yyyy");
            } catch (Exception e) {
                strSubmissionDate = "";
            }

            String strApproval = "";

            Vector rowx = new Vector();
            String statusDoc = SessLeaveApplication.getStatusDocument(objleaveApplication.getDocStatus());
            if (objleaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                rowx.add("" + employee.getEmployeeNum());
            } else {
                rowx.add("<a href=\"javascript:cmdEditMyLeave('" + objleaveApplication.getOID() + "','" + employee.getOID() + "')\">" + employee.getEmployeeNum() + "</a>");
            }
            rowx.add(employee.getFullName());
            rowx.add(department.getDepartment());
            String typeleave = SessLeaveApplication.typeLeave(objleaveApplication.getOID());            
            rowx.add(strSubmissionDate);

            if (statusDoc != null) {
                rowx.add("" + statusDoc);
            } else {
                rowx.add("");
            }

            String depHead = SessLeaveApplication.getEmployeeApp(objleaveApplication.getDepHeadApproval());
            String hrMan = SessLeaveApplication.getEmployeeApp(objleaveApplication.getHrManApproval());
            String gM = SessLeaveApplication.getEmployeeApp(objleaveApplication.getGmApproval());

            if (depHead != null) {
                rowx.add(depHead);
            } else {
                rowx.add("");
            }
            if (hrMan != null) {
                rowx.add(hrMan);
            } else {
                rowx.add("");
            }
            if (gM != null) {
                rowx.add(gM);
            } else {
                rowx.add("");
            }

            float sumAL = SessLeaveApplication.countAL(objleaveApplication.getOID(), employee.getOID());
            float sumLL = SessLeaveApplication.countLL(objleaveApplication.getOID(), employee.getOID());
            float sumDP = SessLeaveApplication.countDP(objleaveApplication.getOID(), employee.getOID());
            float sumSpecial = SessLeaveApplication.countSpecialLeave(objleaveApplication.getOID(), employee.getOID());
            float sumUnpaid = SessLeaveApplication.countUnpaidLeave(objleaveApplication.getOID(), employee.getOID());
            rowx.add("" + sumAL);
            rowx.add("" + sumLL);
            rowx.add("" + sumDP);
            rowx.add("" + sumSpecial);
            rowx.add("" + sumUnpaid);

            lstData.add(rowx);
        }
        return ctrlist.draw();
    }
%>

<%
            int iCommand = FRMQueryString.requestCommand(request);
            
            int viewListEndV1= FRMQueryString.requestInt(request,"viewListEndV1");
            
            Vector listEmpBirthday = new Vector(1, 1);
            //priska 20141106
            Vector listEmpEndedContract = new Vector(1, 1);
            Vector listEmpEndedContractDua = new Vector(1, 1);
            
            Vector listEmpEndedContractTiga = new Vector(1, 1);
            //add opie-eyek 20141106
            int sizeEndContractToday=0;
            int sizeEndContractthismonth=0;
            int sizeEndContractfreevalue=0;
            boolean aksesApproval = false;

            Employee objEmployee = new Employee();
            Position position = new Position();
            Section section = new Section();

            I_Atendance attendanceConfig = null;
            try {
                attendanceConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
            }
            catch(Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            try {
               if(emplx.getOID()!=0){
                objEmployee = PstEmployee.fetchExc(emplx.getOID());
               }
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            try {
              if(objEmployee.getPositionId()!=0){
                position = PstPosition.fetchExc(objEmployee.getPositionId());
              }
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
            
            try {
              if(objEmployee.getSectionId()!=0){
                section = PstSection.fetchExc(objEmployee.getSectionId());
              }
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }


            if (position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE || position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE || 
                    position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){

                aksesApproval = true;

            }

            if(viewChangePassword){
                privViewChangePinNPasword = viewChangePassword;
                //update by satrya 2014-01-15 privViewMenuEmployee = aksesApproval;
                
            }
            if (!NonViewBirthday) {
                //Update By Agus 14-02-2014
                SessEmployee sessEmployee = new SessEmployee();
                if(attendanceConfig.getConfigurationBirthDay()== I_Atendance.CONFIGURASI_I_VIEW_BIRTHDAY_A_WEEK){ 
                listEmpBirthday = sessEmployee.getBirthdayReminderDay();
                }else{
                    
                    if(attendanceConfig!=null && attendanceConfig.getConfigurationBirthDay()== I_Atendance.CONFIGURASI_III_VIEW_BIRTHDAY_A_MONTH_NOT_SHOW_ANNYVERSARY_DATE_HAS_PASSED){   
                        listEmpBirthday = sessEmployee.getBirthdayReminderDay();
                    }else{
                        listEmpBirthday = sessEmployee.getBirthdayReminderDay();
                    }
                }            
            }            
//priska 2014-11-06
            
                //Update By Agus priska 06-11-2014
            // emngambil nilai dari system properties
           
               SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
               searchSpecialQuery.setRadioendcontract(1);
               Date now = new Date();
               searchSpecialQuery.addEndcontractfrom(now);
               searchSpecialQuery.addEndcontractto(now);
               searchSpecialQuery.setiSex(2);
               sizeEndContractToday = SessSpecialEmployee.countSearchSpecialEmployee(searchSpecialQuery, 0, 0);
               
               if(viewListEndV1==1){
                    listEmpEndedContract=SessSpecialEmployee.searchSpecialEmployee(searchSpecialQuery, 0, 0);
                    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQuery);
               }
              
               Date dt = new Date();
               dt.setDate(1);
               
               Date dt2 = new Date();
               dt2.setDate(30);
               SearchSpecialQuery searchSpecialQueryDua = new SearchSpecialQuery();
               searchSpecialQueryDua.setRadioendcontract(1);
               searchSpecialQueryDua.addEndcontractfrom(dt);
               searchSpecialQueryDua.addEndcontractto(dt2);
               searchSpecialQueryDua.setiSex(2);
               sizeEndContractthismonth = SessSpecialEmployee.countSearchSpecialEmployee(searchSpecialQueryDua, 0, 0);
               Vector  listEm = new Vector(1,1);
               try{
               listEm = SessSpecialEmployee.searchSpecialEmployee(searchSpecialQueryDua,0,500);     
               } catch (Exception ex){
                   
               }
               
               if(viewListEndV1==2){
                    listEmpEndedContractDua=SessSpecialEmployee.searchSpecialEmployee(searchSpecialQueryDua, 0, 0);
                    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQueryDua);
                }
               
               Date dt3 = new Date();  
               int lengthContractEnd = 3;
               try{
                lengthContractEnd = Integer.valueOf(PstSystemProperty.getValueByName("SET_LENGTH_END_CONTRACT")); 
               }catch (Exception e){
                   
               }
               dt3.setDate(dt3.getDate()+lengthContractEnd);
               SearchSpecialQuery searchSpecialQueryTiga = new SearchSpecialQuery();
               searchSpecialQueryTiga.setRadioendcontract(1);
               searchSpecialQueryTiga.addEndcontractfrom(now);
               searchSpecialQueryTiga.addEndcontractto(dt3);
               searchSpecialQueryTiga.setiSex(2);
               sizeEndContractfreevalue = SessSpecialEmployee.countSearchSpecialEmployee(searchSpecialQueryTiga, 0, 0);
               
               if(viewListEndV1==3){
                    listEmpEndedContractTiga=SessSpecialEmployee.searchSpecialEmployee(searchSpecialQueryTiga, 0, 0);
                    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQueryTiga);
               }
            
               if(iCommand == Command.RETRY){
                
                String[] size_emp_approval      = null;	
                String[] emp_need_approval      = null;          
                String[] emp_approve            = null;
                
                Vector listEmpApproval = new Vector();
                
                try{
                    
                    size_emp_approval = request.getParameterValues("size");
                    emp_need_approval =  new String[size_emp_approval.length];
                    emp_approve =  new String[size_emp_approval.length];
                    
                    Vector listApproval = new Vector(); 
                    long employeeId = 0;                  
                    
                    for(int i = 0 ; i < size_emp_approval.length ; i++){
                        
                        emp_need_approval[i] = FRMQueryString.requestString(request,"leave_app_"+i);                        
                        emp_approve[i] = FRMQueryString.requestString(request,"employee_approval_"+i);   
                          
                        int ix = FRMQueryString.requestInt(request, "data_app_process"+i);
                        
                        if(ix == 1){
                            
                            long appOid = 0;
                            long empOid = 0;
                            
                            try{
                                
                                appOid  = Long.parseLong(emp_need_approval[i]);       
                                employeeId  = Long.parseLong(emp_approve[i]);                               
                                        
                            }catch(Exception E){
                                System.out.println("Exception "+E.toString());
                            }
                            
                            LeaveApplication objleaveApplication = new LeaveApplication();
                            
                            objleaveApplication.setOID(appOid);                            
                            
                            listApproval.add(objleaveApplication);
                            
                        }
                        
                    }
                   
                    SessLeaveApplication.approvalCheckBox(employeeId,listApproval);
                    
                }catch(Exception E){
                    System.out.println();
                }
            }
            
            if(iCommand ==Command.LOCK){
                
                String[] size_emp_sch   = null;	
                String[] emp_sch_id     = null;             	                
                
                Vector listEmpSchedule = new Vector();
                
                try{
                    
                    size_emp_sch = request.getParameterValues("size");
                    emp_sch_id =  new String[size_emp_sch.length];
                    
                    for(int i = 0 ; i < size_emp_sch.length ; i++){
                        
                        emp_sch_id[i] = FRMQueryString.requestString(request,"emp_schedule_id_"+i);
                        EmpSchedule empSchedule = new EmpSchedule();
                        int ix = FRMQueryString.requestInt(request, "data_is_process"+i);
                        
                        if(ix==1){
                        
                                long schOid = 0;
                                
                                try{
                                    schOid = Long.parseLong(emp_sch_id[i]);
                                }catch(Exception E){
                                    System.out.println("Exception "+E.toString());
                                }
                                
                                empSchedule.setOID(schOid);
                            
                        }
                        
                        listEmpSchedule.add(empSchedule);
                    }
                    
                    SessEmpSchedule.updateScheduleToBeCheck(listEmpSchedule);
                    
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
            }

            //Update By Agus 14-02-2014
            if (iCommand == Command.SUBMIT) {
                SessEmployee sessEmployee = new SessEmployee();
                if(attendanceConfig!=null && attendanceConfig.getConfigurationBirthDay()==I_Atendance.CONFIGURASI_I_VIEW_BIRTHDAY_A_WEEK){ 
                listEmpBirthday = sessEmployee.getBirthdayReminderDay();
                }else{
                    if(attendanceConfig!=null && attendanceConfig.getConfigurationBirthDay()== I_Atendance.CONFIGURASI_III_VIEW_BIRTHDAY_A_MONTH_NOT_SHOW_ANNYVERSARY_DATE_HAS_PASSED){   
                        listEmpBirthday = sessEmployee.getBirthdayReminderDay();
                    }else{
                        listEmpBirthday = sessEmployee.getBirthdayReminderDay();
                    }
            }
            }

            
            Vector listNeedApprove = new Vector();

            if (iCommand == Command.ACTIVATE && aksesApproval == true){
                
                long LevelExcomLocal = 0;
                long LevelExcomExpatriat = 0;
                
                try{
                    LevelExcomLocal = Long.parseLong(PstSystemProperty.getValueByName("OID_LEVEL_EXCOM_LOCAL"));
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                try{
                    LevelExcomExpatriat = Long.parseLong(PstSystemProperty.getValueByName("OID_LEVEL_EXCOM_EXPATRIAT"));
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                
                boolean approveConfiguration=false;
                try{
                    approveConfiguration = Boolean.parseBoolean(PstSystemProperty.getValueByName("LEAVE_CONFIGURATION_APPROVAL_HOME"));
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                
                boolean levelExpat = false;
                
                if(objEmployee.getLevelId() == LevelExcomLocal || objEmployee.getLevelId() == LevelExcomExpatriat){
                    levelExpat = true;
                }
                if(approveConfiguration){
                     if(position.getPositionLevel() == PstPosition.LEVEL_MANAGER){
                         if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId(),PstPosition.LEVEL_GENERAL,PstPosition.LEVEL_MANAGER);
                        }
                     }else if(position.getPositionLevel() == PstPosition.LEVEL_ASST_DIRECTOR){
                         if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId(),PstPosition.LEVEL_GENERAL,PstPosition.LEVEL_ASST_DIRECTOR);
                        }
                     }else if(position.getPositionLevel() == PstPosition.LEVEL_DIRECTOR){
                         if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId(),PstPosition.LEVEL_GENERAL,PstPosition.LEVEL_DIRECTOR);
                        }
                     }else if(position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER){
                         if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId(),PstPosition.LEVEL_GENERAL,PstPosition.LEVEL_GENERAL_MANAGER);
                        }
                     }
                }else{
                   String employeeIds = PstLeaveConfigurationMain.listJoinDetail(emplx.getOID()); 
                    if(employeeIds!=null && employeeIds.length()>0){
                        listNeedApprove = SessLeaveApplication.getListApprovallConfig(employeeIds,emplx.getOID());
                    }else{
                        if(position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER){
                    
                    if(position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListGmExcomAndExpat(objEmployee.getSectionId(), SessLeaveApplication.GM_SECTION_SCOPE);
                        
                    }else if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListGmExcomAndExpat(objEmployee.getDepartmentId(), SessLeaveApplication.GM_DEPARTMENT_SCOPE);
                        
                    }else if(position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListGmExcomAndExpat(objEmployee.getDivisionId(), SessLeaveApplication.GM_DIVISION_SCOPE);
                        
                    }    
                    //listNeedApprove = SessLeaveApplication.getListApprovalGM();  
                    
                }else if(position.getAllDepartment() == PstPosition.ALL_DEPARTMENT_TRUE){
                    
                    if(position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListApprovalSection(objEmployee.getSectionId(), 0 /*objEmployee.getDepartmentId()*/ ,objEmployee.getOID());                        
                        
                    }else if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE || position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListApprovalAllDepartment(objEmployee.getOID(),levelExpat);
                        
                    }
                    
                }else{
                    if(position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){                        
                        listNeedApprove = SessLeaveApplication.getListApprovalDivision(objEmployee.getOID(), objEmployee.getDivisionId());                                                
                    } else {                    
                        if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId());
                        }else{                   
                            if(position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE){                    

                                listNeedApprove = SessLeaveApplication.getListApprovalSection(objEmployee.getSectionId(), objEmployee.getDepartmentId() ,objEmployee.getOID());  

                            }
                       }
                    }
                    
                    
                }
                    }
              }//end else
            }

            if (iCommand == Command.LOAD) { 

                listNeedApprove = SessLeaveApplication.getListEmployeeLeaveApplication(emplx.getOID());

            }
            if(iCommand == Command.GET){
                
                SessEmployee.insertToAccess();
                
            }
            
            if(iCommand == Command.RESET){
                
                SessEmployee.setBarcodeNumber();
                
            }

            Employee appUser = new Employee();

            AppUser objAppUser = new AppUser();
            String msg = "";
            int status_update = 0;
            if (iCommand == Command.UPDATE){

              
                status_update = FRMQueryString.requestInt(request, "status");

                if (status_update == 1) {

                    String employeeNum = FRMQueryString.requestString(request, "FRM_EMP_NUM");
                    String empPaswd = FRMQueryString.requestString(request, "FRM_EMP_PIN");
                    String empConfirmPaswd = FRMQueryString.requestString(request, "FRM_EMP_CINFIRM_PIN");

                    if (empPaswd.equals(empConfirmPaswd)) {

                        SessEmployee.updateEmpNum_Pin(emplx.getOID(), empPaswd);

                        msg = "Update Success";

                    } else {

                        msg = "Confirm password wrong";

                    }

                    try {
                        appUser = PstEmployee.fetchExc(emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }
                
                }//update by satrya 2013-12-23
                else if (status_update == 2) {

                    String employeeNum = FRMQueryString.requestString(request, "FRM_USER_NAME");
                    String empPaswd = FRMQueryString.requestString(request, "FRM_EMP_PASSWORD");
                    String empConfirmPaswd = FRMQueryString.requestString(request, "FRM_EMP_CINFIRM_PASSWORD");

                    if (empPaswd.equals(empConfirmPaswd)) {

                        SessEmployee.updateEmpPassword(emplx.getOID(), empPaswd);

                        msg = "Update Success";
                        
                    } else {

                        msg = "Confirm password wrong";

                    }
                    try {
                        objAppUser = PstAppUser.getByEmployeeId(""+emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }
                    status_update = 3;
                    
                }else if(status_update == 3){
                    try {
                        objAppUser = PstAppUser.getByEmployeeId(""+emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }
                }
                //end update by satrya 
                else {

                    try {
                        appUser = PstEmployee.fetchExc(emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }
                }
            }

            if (iCommand == Command.YES) {

                SessEmployee.setEmployeePin();
            }
            
            if (iCommand == Command.START) {

                SessEmployee.setEmployeePin();
            }
            
            if (iCommand == Command.CONFIRM) {

                SessEmployee.UpdateSts();
            }
            
            if (iCommand == Command.UNLOCK) {

                SessEmployee.updateBarcodeNumberEmployee();
                
            }

            if (iCommand == Command.REFRESH){

                SessEmployee.delCheckInOut();
                
            }
            
            if (iCommand == Command.STOP){

                SessEmployee.UpdateStsUncheck();
                
            }


            if (iCommand == Command.SAVE){

                String employeeNum = FRMQueryString.requestString(request, "FRM_EMP_NUM");
                String empPaswd = FRMQueryString.requestString(request, "FRM_EMP_PIN");
                String empConfirmPaswd = FRMQueryString.requestString(request, "FRM_EMP_CINFIRM_PIN");

                if (empPaswd.equals(empConfirmPaswd)) {

                    SessEmployee.updateEmpNum_Pin(emplx.getOID(), empPaswd);
                    msg = "Update Success";

                } else {

                    try {
                        appUser = PstEmployee.fetchExc(emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }

                    msg = "Confirm password wrong";

                }
            }
        
       Vector listScheduleCheck = new Vector();     
       
       if (iCommand == Command.ASSIGN  || iCommand == Command.LOCK) {

            Vector dept_value = new Vector(1, 1);
            Vector dept_key = new Vector(1, 1);
            Vector listDept = new Vector(1, 1);
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
                        dept_value.add("0");
                        dept_key.add("select ...");
                        listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                    } else {
                        String whereClsDep="(DEPARTMENT_ID = " + departmentOid+")";
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
                                    if(comp.trim().compareToIgnoreCase(""+departmentOid)==0){
                                      grpIdx = countIdx;   
                                    }
                                }
                                countIdx++;
                            } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop<MAX_LOOP)); // if found then exit
                            
                            // compose where clause
                            if(grpIdx>=0){
                                String[] grp = (String[]) depGroup.get(grpIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    whereClsDep=whereClsDep+ " OR (DEPARTMENT_ID = " + comp+")"; 
                                }         
                               }                                                  
                        } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);
                        }

                        listDept = PstDepartment.list(0, 0,whereClsDep, "");
                    }
                } else {
                    dept_value.add("0");
                    dept_key.add("select ...");
                    listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                }
            } else {
                dept_value.add("0");
                dept_key.add("select ...");
                listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
            }
            
            listScheduleCheck = SessEmpSchedule.searchEmpScheduleToBeCheck(listDept);            
                        
        }

%>

<%
  /*
   * Update by Hendra McHen
   * Date : 2014-11-21
   * Description : get list reprimand by Today, and This month
   */
    Vector listReprimand = new Vector();
    Vector listEmpMemo = new Vector();
    Date dateT = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateT);
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1; // tambah 1 karena month dimulai dari 0
    int day = cal.get(Calendar.DAY_OF_MONTH);
    String dateNow = year+"-"+month+"-"+day;
    String whereClause = "";
    String orderClause = "";
    int vectSize = 0;
    if (viewListEndV1 == 4){
        whereClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE] + " LIKE " + "'"+dateNow+"'";  
    }

    if (viewListEndV1 == 5){
        whereClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE] + " LIKE " + "'"+year+"-"+month+"%'";     
    }
    orderClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE];
    vectSize = PstEmpReprimand.getCount(whereClause);
    listReprimand = PstEmpReprimand.list(0, vectSize, whereClause, orderClause);
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dtnow = new Date();
    String datenow = sdf.format(dtnow);
    
    long employeeId = emplx.getOID();
    String whereStatus = "" +PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID]+" = "+employeeId;
    listEmpMemo = PstEmpDocGeneral.list(0, 10, whereStatus, "");
    

    
%>

<%
    String url= request.getParameter("menu");
    String urlC = request.getRequestURL().toString();
        String baseURL = urlC.substring(0, urlC.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

%>
<!-- JSP Block -->
<!-- End of JSP Block -->

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Dashboard | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 --> 
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/css/bootstrap.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="<%= approot%>/assets/font-awesome/4.6.1/css/font-awesome.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="<%= approot%>/assets/ionicons/2.0.1/css/ionicons.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/AdminLTE.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/skins/skin-blue.css">
        <link rel="stylesheet" href="<%= approot%>/styles/toastr/toastr.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%= approot%>/assets/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="<%= approot%>/assets/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .finger{
                width:20%; 
                height:90px;
                padding : 2%;
                float:left;
             }
            .finger_spot{
                width:90%;
                height: 80px;
                background-color :#e5e5e5;
                border : thin solid #c5c5c5;
                font-size: 14px;
                font-family:calibri;
                text-align:center;
                color :#FFF;
                border-radius: 3px;
            }

            .green{
               background-color : #5CB85C;
               border : thin solid #4CAE4C;
            }
            .list-group-item {
                padding: 3px 10px
            }
        </style>
        <SCRIPT language=JavaScript>
            
            function cmdRunFile() {
                WshShell = new ActiveXObject("WScript.Shell");
                WshShell.Run("C:/Program Files (x86)/WinSCP/WinSCP.exe", 1, false);
            }
            
            function SetAllCheckBoxes(FormName, FieldName, CheckValue, nilai)
            {
                   for(var i = 0; i < nilai; i++){
                                    var objCheckBoxes = document.forms[FormName].elements[FieldName+i];
                                    objCheckBoxes.checked = CheckValue;    
                                    }
                                    
            }
            
            function cmdEdit(oidLeave, oidEmployee)
            {
                document.frm.command.value="<%=Command.EDIT%>";
                document.frm.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
                document.frm.oid_employee.value = oidEmployee;
                document.frm.action="employee/leave/leave_app_edit.jsp";
                document.frm.submit();
            }    
           // from priska 07-11-2014
            function cmdEditEmp(oidEmployee){
		document.frm.employee_oid.value=oidEmployee;
		document.frm.command.value="<%=Command.EDIT %>";
		document.frm.action="employee/databank/employee_edit.jsp";
		document.frm.submit();
            }
            
            function cmdEditSchedule(oid){
                document.frm.command.value="<%=String.valueOf(Command.EDIT)%>";                
                document.frm.hidden_emp_schedule_id.value = oid;
		document.frm.action="employee/attendance/empschedule_edit.jsp";
		document.frm.submit();
            }
            
            function cmdEditMyLeave(oidLeave, oidEmployee)
            {
                document.frm.command.value="<%=Command.EDIT%>";
                document.frm.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
                document.frm.oid_employee.value = oidEmployee;
                document.frm.action="employee/leave/leave_app_edit.jsp";
                document.frm.submit();
            }    
            
            function cmdSetPin(){
                document.frm.command.value="<%=Command.YES %>";        
                document.frm.action="home.jsp";
                document.frm.submit();
            }
            
            function cmdView(){
                document.frm.command.value="<%=Command.SUBMIT%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function checkview(idxview){
                document.frm.command.value="<%=Command.SUBMITENDEDCONTRACT%>";
                 document.frm.viewListEndV1.value=idxview;
                document.frm.action="home.jsp";	
                document.frm.submit(); 
            }
            
            function cmdViewEC(){
                document.frm.command.value="<%=Command.SUBMITENDEDCONTRACT%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdViewApprovalLeave(){
                document.frm.command.value="<%=Command.ACTIVATE %>";        
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdViewApplyLeave(){
                document.frm.command.value="<%=Command.EDIT%>";        
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="employee/leave/leave_app_edit.jsp";
                document.frm.submit();
            }
            
            function cmdViewMyLeave(){
                document.frm.command.value="<%=Command.LOAD %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdChangePassword(){
                document.frm.command.value="<%=Command.UPDATE %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdChangePasswordNew(){
                document.frm.command.value="<%=Command.UPDATE %>";
                 document.frm.status.value="<%=3 %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdProccessSchedule(){
                document.frm.command.value="<%=Command.LOCK %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdProccessApproval(){
                document.frm.command.value="<%=Command.RETRY %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdSavePassword(){
                document.frm.command.value="<%=Command.UPDATE %>";
                document.frm.status.value="<%=1 %>";
                document.frm.oid_employee.value ="<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
             function cmdSavePasswordNew(){
                document.frm.command.value="<%=Command.UPDATE %>";
                document.frm.status.value="<%=2 %>";
                document.frm.oid_employee.value ="<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdCancelSavePassword(){
                document.frm.command.value="<%=Command.NONE %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdScheduleCheck(){
                document.frm.command.value="<%=Command.ASSIGN %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function InsertDatabase(){
                document.frm.command.value="<%=Command.GET %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            } 
            
            function updateBarcodeNumber(){
                document.frm.command.value="<%=Command.RESET %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            } 
            
            function UpdateDatabase(){
                document.frm.command.value="<%=Command.DETAIL %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            } 
            
            function ChangeBarcode(){
                document.frm.command.value="<%=Command.UNLOCK %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function delCheckInOut(){
                document.frm.command.value="<%=Command.REFRESH %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }

            function SetProces(){
                document.frm.command.value="<%=Command.STOP %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";
                document.frm.submit();
            }

           function cmdPrintXLS(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListXLS");
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
            
        </SCRIPT>
        <!-- #EndEditable -->
        
        <style type="text/css">
.inputReadOnly {         
    background-color  : #C0C0C0;
}
</style>
<%@ include file="template/css.jsp" %>
<%@ include file="template/header.jsp" %>
    </head> 
    <body class="hold-transition skin-blue <%=sidebar%>">
        <div class="wrapper">
            
            <% if (!(namaUser1.equals("Employee"))){ %>
                <%@ include file="template/sidebar.jsp" %>
            <% } %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Dashboard
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Dashboard</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-6" style="border-right: 1px solid black">
                            
                            <div class="panel-group" id="memo">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#memo" href="#memoCollapse">Today's Memo :</a>
                                        </h4>
                                    </div>
                                    <div id="memoCollapse" class="panel-collapse collapse in">        
                                    <div class="list-group">
                                        <%
                                           
                                           EmpDoc empDoc = new EmpDoc();
                                           if (listEmpMemo.size()>0){
                                               for (int i=0;i < listEmpMemo.size(); i++){
                                                   EmpDocGeneral empDocGeneral = (EmpDocGeneral) listEmpMemo.get(i);
                                                   try{
                                                       empDoc = PstEmpDoc.fetchExc(empDocGeneral.getEmpDocId());
                                                   } catch (Exception exc){}
                                             
                                        %>
                                        <% if (empDocGeneral.getAcknowledgeStatus() == 1){ %>
                                        <a href='#' id='memo' data-oid='<%=empDoc.getOID()%>' data-for='viewDoc' data-target="editor1" data-status="1" class="list-group-item list-group-item-action memo_<%=empDoc.getOID()%>">
                                            <%=empDoc.getDoc_title()%></a>
                                        <% } else { %>
                                        <a href='#' id='memo' data-oid='<%=empDoc.getOID()%>' data-for='viewDoc' data-target="editor1" class="list-group-item list-group-item-success memo_<%=empDoc.getOID()%>">
                                            <strong id="<%=empDoc.getOID()%>"><%=empDoc.getDoc_title()%></strong>
                                        </a>
                                         
                                        <%       }
                                           }
                                        } else { %><li class="list-group-item">No Memo Today</li> <%
                                        }%>    
                                    </div>
                                    </div>
                                </div>
                            </div>
                           
                        </div>
                        <div class="col-md-6">
                                <div class="panel-group" id="memo">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#workplan" href="#workplanCollapse">Today's Work Plan :</a>
                                        </h4>
                                    </div>
                                    <div id="workplanCollapse" class="panel-collapse collapse in">        
                                    <div class="list-group">
                                        <li class="list-group-item">No Work Plan for Today</li>
                                        
                                    </div>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                    <br />
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="col-sm-3">
                                        <div class="text-center">
                                            <a id="masterdata_icon" href="<%= approot%>/employee/databank/view_databank.jsp?oid=<%=empOIDHeader%>">
                                                <i class="fa fa-user fa-5x"></i>
                                                <br />
                                                <br />
                                                My Profiles
                                            </a>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="text-center">
                                            <a id="masterdata_icon" href="#">
                                                <i class="fa fa-money fa-5x"></i>
                                                <br />
                                                <br />
                                                My Salary
                                            </a>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="text-center">
                                            <a id="masterdata_icon" href="#">
                                                <i class="fa fa-file fa-5x"></i>
                                                <br />
                                                <br />
                                                My Cashbon
                                            </a>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="text-center">
                                            <a id="masterdata_icon" href="#">
                                                <i class="fa fa-calendar fa-5x"></i>
                                                <br />
                                                <br />
                                                Schedule & Attendance
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <br />
                                <div class="box-body">
                                    <div class="col-sm-3">
                                        <div class="text-center">
                                            <a id="masterdata_icon" href="#">
                                                <i class="fa fa-calendar-check-o fa-5x"></i>
                                                <br />
                                                <br />
                                                Leave
                                            </a>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="text-center">
                                            <a id="masterdata_icon" href="#">
                                                <i class="fa fa-files-o fa-5x"></i>
                                                <br />
                                                <br />
                                                Form Management
                                            </a>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="text-center">
                                            <a id="masterdata_icon" href="#">
                                                <i class="fa fa-clock-o fa-5x"></i>
                                                <br />
                                                <br />
                                                Work Plan
                                            </a>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="text-center">
                                            <a id="masterdata_icon" href="#">
                                                <i class="fa fa-check fa-5x"></i>
                                                <br />
                                                <br />
                                                Memo
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>   
                </section>
            
        </div><!-- /.content-wrapper -->
             <%@ include file="template/plugin.jsp" %>
            <%@ include file="template/footer.jsp" %>
        </div>
 <script type="text/javascript">
            $(document).ready(function() {
            
            
            var approot = "<%= approot %>";
	    var command = $("#command").val();
	    var dataSend = null;
	    
	    var oid = null;
	    var dataFor = null;
            
            //FUNCTION VARIABLE
	    var onDone = null;
	    var onSuccess = null;
	    var callBackDataTables = null;
	    var iCheckBox = null;
	    var addeditgeneral = null;
            var addempsingle = null;
            var addtext = null;
	    var areaTypeForm = null;
	    var deletegeneral = null;
            var deletesingle = null;
            var editormodal = null;
            var approve = null;
            var editor = "";
            var interval2 =0;
            
            //MODAL SETTING
	    var modalSetting = function(elementId, backdrop, keyboard, show){
		$(elementId).modal({
		    backdrop	: backdrop,
		    keyboard	: keyboard,
		    show	: show
		});
	    };
            
            function datePicker(contentId, formatDate){
		$(contentId).datepicker({
		    format : formatDate
		});
		$(contentId).on('changeDate', function(ev){
		    $(this).datepicker('hide');
		});
	    }
	    
	    var getDataFunction = function(onDone, onSuccess, approot, command, dataSend, servletName, dataAppendTo, notification, dataType){
		/*
		 * getDataFor	: # FOR PROCCESS FILTER
		 * onDone	: # ON DONE FUNCTION,
		 * onSuccess	: # ON ON SUCCESS FUNCTION,
		 * approot	: # APPLICATION ROOT,
		 * dataSend	: # DATA TO SEND TO THE SERVLET,
		 * servletName  : # SERVLET'S NAME,
		 * dataType	: # Data Type (JSON, HTML, TEXT)
		 */
		$(this).getData({
		   onDone	: function(data){
		       onDone(data);
		   },
		   onSuccess	: function(data){
			onSuccess(data);
		   },
		   approot	: approot,
		   dataSend	: dataSend,
		   servletName	: servletName,
		   dataAppendTo	: dataAppendTo,
		   notification : notification,
		   ajaxDataType	: dataType
		});
	    }
	    
	    //SHOW ADD OR EDIT FORM
	    addeditgeneral = function(elementId){
		$(elementId).click(function(){
		    $("#addeditgeneral").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    dataFor = $(this).data('for');
                    var title = $(this).html();
                    var target = $(this).data('target');
                    var status = $(this).data('status');
		    $("#generalform #generaldatafor").val(dataFor);
		    $("#generalform #oid").val(oid);
                    $("#addeditgeneral .modal-dialog").css("width", "90%");
                    $(".btnacknowledge").attr("data-docid", oid);
		    //SET TITLE MODAL
                    $(".addeditgeneral-title").html(title);
                    if (status === 1){
                        $("#btnacknowledgeemp").html('<i class="fa fa-check-circle"></i> Acknowledged');
                        $("#btnacknowledgeemp").removeClass('btn-warning').addClass('btn-success ');
                        $(".btnacknowledge").removeAttr("id");
                        $(".btnacknowledge").prop("disabled", true);
                    }
		
		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command
		    }
		    onDone = function(data){
                        $(target).html(data.FRM_FIELD_HTML);
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".addeditgeneral-body", false, "json");
		});
	    };
            
        var approvalModal = function(elementId,showModal){
            $(elementId).click(function(){
                oid = $(this).data('oid');
                var docId = $(this).data('docid');
                dataFor = $(this).data('for');
                $("#fingerdatafor").val(dataFor);
                $("#docid").val(docId);
                if (showModal==="1"){
                    $("#modalApproveFinger").modal("show");
                    $("#modalApproveFinger .modal-dialog").css("width", "50%");
                }
                loadFingerPatern(oid,dataFor);
            });
        };  

        approvalModal(".btnacknowledge","1");

        var loadFingerPatern = function(oid,dataFor){
            oid = oid;
            dataFor = "showAcknowledgeForm";
            var empId = "<%= employeeId%>";
            var url = "<%=baseURL%>";
                dataSend = {
                    "FRM_FIELD_DATA_FOR"       : dataFor,                                       
                    "FRM_FIELD_OID"            : oid, 
                    "empId"                    : empId,
                    "FRM_FIELD_APPROOT"        : url
                };

                onSuccess = function(data){

                };

                onDone = function(data){
                   fingerClick(".loginFinger");
                };

                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmployee", "#dynamicFingerPatern", false, "json");
            };

        var fingerClick = function (elementId){
            //alert('test');
            $(elementId).click(function(){
                var empId= "<%= employeeId%>";
                interval2 =  setInterval(function() {
                    checkStatusUser(empId);
                },5000);

            });
        };

        var checkStatusUser = function(empId){
            var dataFor = "checkStatusUser";                                  
            command = "<%= Command.NONE%>";
            dataSend = {
                "FRM_FIELD_DATA_FOR"       : dataFor,                                       
                "FRM_FIELD_LOGIN_ID"       : empId,                                   
                "command"                  : command
            };

            onSuccess = function(data){

            };

            onDone = function(data){
                if (data.FRM_FIELD_HTML=="1"){
                    clearInterval(interval2);                                      
                    alert('Verification Success...');  
                    //PROSES DILANJUTKAN
                    processingApproveFinger();
                }
            };

            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmployee", "", false, "json");

        };

        var processingApproveFinger= function(){
            var oidDetail = $("#docid").val();
            var empId = $("#empid").val();
            command = "<%= Command.SAVE%>";
            var dataFor = "approveFinger";
            var generaldatafor = $("#fingerdatafor").val();
            
            dataSend = {
                "FRM_FIELD_DATA_FOR"       : dataFor,                                       
                "FRM_FIELD_OID_DETAIL"     : oidDetail,
                "FRM_FIELD_EMP_ID"         : empId,
                "command"                  : command
            };

            onSuccess = function(data){

            };

            onDone = function(data){
                
                $('strong#'+oidDetail).contents().unwrap();
                $("#btnacknowledgeemp").html('<i class="fa fa-check-circle"></i> Acknowledged');
                $("#btnacknowledgeemp").removeClass('btn-warning').addClass('btn-success ');
                $(".btnacknowledge").removeAttr("id");
                
                $("#modalApproveFinger").modal("hide");
            };

            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", "", false, "json");

        };
            
            modalSetting("#addeditgeneral", "static", false, false);
            addeditgeneral("a#memo");

         

            });
        </script>       
        <div id="addeditgeneral" class="modal fade nonprint" tabindex="-1" style="overflow-y: auto">
          <div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addeditgeneral-title"></h4>
		</div>
                <form id="generalform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="template_id" id="template_id">
                    <input type="hidden" name="empId" id="empId">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
<!--                                <textarea id="editor2"></textarea>-->
				<div class="box-body addeditgeneral-body">
                                    
				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
                        <button type="button" class='btn btn-warning btnacknowledge' id="btnacknowledgeemp" data-oid="<%=employeeId%>" data-for='showEmpAcknowledge' ><i class='fa fa-thumbs-up'></i> Acknowledge</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div> 
    <!-- MODAL FINGER-->
    <div id="modalApproveFinger" class="modal fade" tabindex="-1">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="modal-title-approve-finger" value="Select Finger"></h4>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <input type="hidden" name="FRM_FIELD_OID_DOC" id="docid">
                        <input type="hidden" name="FRM_FIELD_EMP_ID" id="empid" value="<%=employeeId%>">
                        <input type="hidden" name="FRM_FIELD_DATA_FOR" id="fingerdatafor">
                        <div class="col-md-12" id="dynamicFingerPatern">

                        </div>
                    </div>
                </div>
                <div class="modal-footer">                              
                    <button type="button" data-dismiss="modal" class="btn btn-danger">Close</button>
                </div>
            </div>
        </div>
    </div>                      
    </body>
</html>