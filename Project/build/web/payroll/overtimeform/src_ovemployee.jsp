<%@page import="com.dimata.harisma.entity.attendance.EmpScheduleReport"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertime"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.overtime.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.form.search.FrmSrcEmployee" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.AlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstAlUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessAlUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.form.overtime.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%//@include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%!
public String drawList(Vector objectClass, int st,Hashtable hashCekOverlap){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No.","4%");
        ctrlist.addHeader("Select","4%");
	ctrlist.addHeader("Payroll","10%");
	ctrlist.addHeader("Name","27%");
	ctrlist.addHeader("Commencing Date","11%");
	ctrlist.addHeader("Division","12%");
	ctrlist.addHeader("Department","12%");
	ctrlist.addHeader("Position","12%");
	ctrlist.addHeader("Section","12%");
		
	ctrlist.setLinkRow(2);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
        
	for (int i = 0; i < objectClass.size(); i++) {
                String sScheduleName="";
		Vector temp = (Vector)objectClass.get(i);
		Employee employee = (Employee)temp.get(0);
		Department department = (Department)temp.get(1);
		Position position = (Position)temp.get(2);
		Section section = (Section)temp.get(3);
		//EmpCategory empCategory = (EmpCategory)temp.get(4);
		//Level level = (Level)temp.get(5);
		//Religion religion = (Religion)temp.get(6);
		//Marital marital = (Marital)temp.get(7);
		Division division = (Division)temp.get(9);

		Vector rowx = new Vector();
		rowx.add(String.valueOf(st + 1 + i));
                
               if(hashCekOverlap!=null && hashCekOverlap.size()>0 &&  hashCekOverlap.containsKey(employee.getOID())){
                    ScheduleSymbol scheduleSymbol = (ScheduleSymbol)hashCekOverlap.get(employee.getOID());
                   
                    long oidSch= 0;//Long.parseLong(sOidSchx);
                    if(scheduleSymbol!=null && scheduleSymbol.getOID()!=0){
                        oidSch = scheduleSymbol.getOID();
                    }
                    rowx.add("<input type=\"checkbox\" name=\"empoid\" checked=\"checked\" value=\""+employee.getOID()+"\" >");
                    
                     if(oidSch!=0){
                         try{
                         scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch); 
                         int iErrCode = CtrlOvertimeDetail.RSLT_OVERTIME_OVERLAP_THIS_SCHEDULE ;
                         sScheduleName =   CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode] +" "+ (scheduleSymbol.getSymbol()!=null ? scheduleSymbol.getSymbol() :"") + " = (" + (scheduleSymbol.getTimeIn()!=null? Formater.formatDate(scheduleSymbol.getTimeIn(), " HH:mm "):"")+" : "+(scheduleSymbol.getTimeOut()!=null ? Formater.formatDate(scheduleSymbol.getTimeOut(), " HH:mm "):"")+")";
                        
                        }catch(Exception exc){
                            System.out.println("Exception"+exc); 
                        }
                     }
                    rowx.add("<p title=\""+sScheduleName+"\" class=\"masterTooltip\">"+employee.getEmployeeNum() +" <font color=\"#FF0000\"> (Overlap!) </font> "+"</p>");
               }else{
                     rowx.add("<input type=\"checkbox\" name=\"empoid\" value=\""+employee.getOID()+"\" >");
                     rowx.add(employee.getEmployeeNum());
               }
               
		
		rowx.add(employee.getFullName());
                if(employee.getCommencingDate()!=null){
                    rowx.add(Formater.formatDate(employee.getCommencingDate(),"yyyy-MM-dd"));
                }else{
                    rowx.add("");
                }
		rowx.add(division.getDivision());
		rowx.add(department.getDepartment());
		rowx.add(position.getPosition());
		rowx.add(section.getSection());
		
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + department.getDepartment());
	}
	return ctrlist.draw();
}
%>

<%
    String[][] message = {
        {"Tetap Memilih Employee Tersebut Tanpa melihat schedule yang bentrok"},
        {"Select the employee without schedule overlap"}
    };
    int ErrOverlap=0;
    boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
    long oidOvertime = FRMQueryString.requestLong(request, "overtime_oid");
    Overtime overtime = new Overtime();
    try{
        overtime = PstOvertime.fetchExc(oidOvertime);
    }catch(Exception exc){
        System.out.println(exc);
    }
    boolean submitIsOk = false;
    boolean empLstIsNotNull=false;
    boolean noErorrOvertime=false;
    //long oidDepartment = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID]);    

    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]);
    long oidSection = FRMQueryString.requestLong(request,FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION]);
    
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    int iErrCode = FRMMessage.ERR_NONE;
    boolean cekError=true; 
    String formName = FRMQueryString.requestString(request,"formName");
    String empPathId = FRMQueryString.requestString(request,"empPathId");
    
    ControlLine ctrLine = new ControlLine();
    SrcEmployee srcEmployee = new SrcEmployee();
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
    frmSrcEmployee.requestEntityObject(srcEmployee);
    srcEmployee.setGender(2);
    String msgString =""; 
    
     long oidDayOff = 0;         
        try{
            oidDayOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
        }catch(Exception ex){
            System.out.println("Execption OID_DAY_OFF: " + ex.toString());
        }
   Hashtable hashCekOverlap = new Hashtable();
   /**
update by satrya 2013-06-13
**/
            Date dateFrom =  FRMQueryString.requestDateVer5(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]);
            Date dateTo = FRMQueryString.requestDateVer5(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]);
            Date restStart = FRMQueryString.requestDateVer5(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_START]);
            Double restTime= FRMQueryString.requestDouble(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_HR]);
            String employeeName=FRMQueryString.requestString(request, FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]);
            String employeeNumber=FRMQueryString.requestString(request, FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER]);
    if(iCommand==Command.SUBMIT){  
        String empLst[] = request.getParameterValues("empoid");
        if(empLst!=null && empLst.length > 0 && oidOvertime!=0){
            
            for(int i=0; i< empLst.length;i++ ){
             try{
                 
                 OvertimeDetail ovd = new OvertimeDetail();
                 ovd.setOvertimeId(oidOvertime);
                 ovd.setDateFrom(dateFrom);
                 ovd.setDateTo(dateTo);
                 ovd.setRestStart(restStart);
                 ovd.setRestTimeinHr(restTime);
                 ovd.setEmployeeId(Long.parseLong(empLst[i]));                                 
                 ovd.setPaidBy(OvertimeDetail.PAID_BY_SALARY);
                 ovd.setAllowance(overtime.getAllowence());
                 //update by satrya 2013-06-13
                 iErrCode = CtrlOvertimeDetail.RSLT_OK;
                 cekError=true; 
                if(ovd.getDateFrom()!=null && ovd.getDateTo()!=null){
                    //update by satrya 2013-06-12
                    //cek jika ada schedulenya dia overlap dengan plan OT
                    Date fromDate = null;
                    if(ovd.getDateFrom()!=null){
                        fromDate = new Date(ovd.getDateFrom().getTime() - 24*60*60*1000);  
                    }
                    Date toDate = null;
                    if(ovd.getDateTo()!=null){ 
                        toDate = new Date(ovd.getDateTo().getTime() + 24*60*60*1000);  
                    }
                    
                   Vector vListOverlapSchedule = PstEmpSchedule.getListSchedule(fromDate, toDate, ovd.getEmployeeId());
                   EmpScheduleReport empScheduleReport = new EmpScheduleReport();
                  if(vListOverlapSchedule!=null && vListOverlapSchedule.size()>0){
                      empScheduleReport =(EmpScheduleReport)vListOverlapSchedule.get(0);
                  }
                   Hashtable hasOverlapOT = PstOvertime.isScheduleOverlapOvertime(ovd.getEmployeeId(), vListOverlapSchedule, oidDayOff, ovd.getDateFrom(),  ovd.getDateTo(), empScheduleReport); 
                   if(hasOverlapOT!=null && hasOverlapOT.get("true")!=null){ 
                       String sOidSch = (String)hasOverlapOT.get("true"); 
                      String sOidSchx = sOidSch!=null?sOidSch.split("_")[0]:"0";  
                      
                       long oidSch= Long.parseLong(sOidSchx);
                       ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                     if(oidSch!=0){
                         scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch); 
                     }
                       
                    String sOidEmp = (String)hasOverlapOT.get("true"); 
                    String sOidEmpx = sOidEmp!=null?sOidEmp.split("_")[1]:"0";   
                    long oidEmp= Long.parseLong(sOidEmpx);
                    if(oidEmp!=0 && oidSch!=0){ 
                      hashCekOverlap.put(oidEmp, scheduleSymbol);
                    }
                    cekError=false;
                    Employee emp = PstEmployee.fetchExc(ovd.getEmployeeId()); 
                        //iErrCode = CtrlOvertimeDetail.RSLT_OVERTIME_OVERLAP_THIS_SCHEDULE ;
                    msgString = msgString+ " Overtime is overlap with schedule for this day  " + "<B>"+ emp.getFullName() + " "+ (scheduleSymbol.getTimeIn()!=null? Formater.formatDate(scheduleSymbol.getTimeIn(), " HH:mm "):"")+" : "+(scheduleSymbol.getTimeOut()!=null ? Formater.formatDate(scheduleSymbol.getTimeOut(), " HH:mm "):"")+"</B>"+ " " + "<br>";
                   }
                    Vector ovLst = PstOvertimeDetail.listOvertimeOverlapVer3(oidOvertime,0, 1, 0, "", ovd.getDateFrom(), ovd.getDateTo(),
                                        0, "", ovd.getEmployeeId(), ""); 
                    //update by satrya 2013-09-04
                    // Vector ovLst = PstOvertimeDetail.listOvertimeOverlap(oidOvertime,0, 1, 0, "", ovd.getDateFrom(), ovd.getDateTo(),
                                      //  0, "", ovd.getEmployeeId(), ""); 
                    if(ovLst!=null && ovLst.size()>0){
                        Employee emp = PstEmployee.fetchExc(ovd.getEmployeeId()); 
                        OvertimeDetail overtimeDetail = (OvertimeDetail)ovLst.get(0);
                        iErrCode = CtrlOvertimeDetail.RSLT_EST_CODE_EXIST ;
                        msgString = msgString+ " Overtime detail exist for " + "<B>"+ emp.getFullName() +"</B>"+ " " + CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode] +" "+ "<a href=\"javascript:openOvertime(\'" + overtimeDetail.getOvertimeId() + "\');\">" + overtimeDetail.getOvt_doc_nr() + "</a> ; <br>";
                        
                    }
                    
                    Vector ovLstSameHour = PstOvertimeDetail.listOvertimeOverlapVer4(oidOvertime,0, 1, 0, "", ovd.getDateFrom(), ovd.getDateTo(),
                                        0, "", ovd.getEmployeeId(), "");  
                    //update by satrya 2013-09-04
                    // Vector ovLst = PstOvertimeDetail.listOvertimeOverlap(oidOvertime,0, 1, 0, "", ovd.getDateFrom(), ovd.getDateTo(),
                                      //  0, "", ovd.getEmployeeId(), ""); 
                    if(ovLstSameHour!=null && ovLstSameHour.size()>0){
                        Employee emp = PstEmployee.fetchExc(ovd.getEmployeeId());
                        OvertimeDetail overtimeDetail = (OvertimeDetail)ovLstSameHour.get(0);
                        iErrCode = CtrlOvertimeDetail.RSLT_EST_CODE_EXIST ;
                        msgString = msgString+ " Overtime detail exist for Hour " + "<B>"+ emp.getFullName() +"</B>"+ " " + CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode] +" "+ "<a href=\"javascript:openOvertime(\'" + overtimeDetail.getOvertimeId() + "\');\">" + overtimeDetail.getOvt_doc_nr() + "</a> ; <br>";
                        
                    }
                    //update by satrya 2013-01-14
                    //pengecekan jika tanggal date form lebih besar
                   if ((ovd.getDateFrom().getTime() >= ovd.getDateTo().getTime())) {
                    Employee emp = PstEmployee.fetchExc(ovd.getEmployeeId());
                    iErrCode = CtrlOvertimeDetail.RSLT_FORM_END_TIME_PRIOR_TO_START_TIME ;
                    msgString = msgString + " Overtime detail " + "<B>"+emp.getFullName() +"</B>"+ " " + CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode]+ "<br> ";
                    
                  }
                }                 
               if((iErrCode == CtrlOvertimeDetail.RSLT_OK || (msgString!=null && msgString.length()<0)) && cekError){   
                 PstOvertimeDetail.insertExc(ovd);
                 //update by satrya 2014-05-15 karena jika true saja dan ada eror salah satu jadi tidak muncul errornya
                 if((msgString!=null && msgString.length()<0) && cekError){
                     
                     noErorrOvertime=true;
                 }
                 submitIsOk=true;
              }
             } catch(Exception exc){
                 System.out.println(exc);
                 submitIsOk = false;
             }
            }            
        }
        ///update by satrya 2013-01-14
        else{
            empLstIsNotNull=true;
        }
    }
    if(iCommand==Command.CONFIRM){  
        String empLst[] = request.getParameterValues("empoid");
        if(empLst!=null && empLst.length > 0 && oidOvertime!=0){
            /*Date dateFrom =  FRMQueryString.requestDateVer2(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]);
            Date dateTo = FRMQueryString.requestDateVer2(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]);
            Date restStart = FRMQueryString.requestDateVer2(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_START]);
            Double restTime= FRMQueryString.requestDouble(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_HR]);*/
            for(int i=0; i< empLst.length;i++ ){
             try{
                 
                 OvertimeDetail ovd = new OvertimeDetail();
                 ovd.setOvertimeId(oidOvertime);
                 ovd.setDateFrom(dateFrom);
                 ovd.setDateTo(dateTo);
                 ovd.setRestStart(restStart);
                 ovd.setRestTimeinHr(restTime);
                 ovd.setEmployeeId(Long.parseLong(empLst[i]));                                 
                 ovd.setPaidBy(OvertimeDetail.PAID_BY_SALARY);
                 ovd.setAllowance(overtime.getAllowence());
                 //update by satrya 2013-06-13
                 iErrCode = CtrlOvertimeDetail.RSLT_OK;
                 cekError=true; 
                if(ovd.getDateFrom()!=null && ovd.getDateTo()!=null){
                   
                    Vector ovLst = PstOvertimeDetail.listOvertimeOverlapVer3(oidOvertime,0, 1, 0, "", ovd.getDateFrom(), ovd.getDateTo(),
                                        0, "", ovd.getEmployeeId(), ""); 
                    if(ovLst!=null && ovLst.size()>0){
                        Employee emp = PstEmployee.fetchExc(ovd.getEmployeeId()); 
                         OvertimeDetail overtimeDetail = (OvertimeDetail)ovLst.get(0);
                        iErrCode = CtrlOvertimeDetail.RSLT_EST_CODE_EXIST ;
                        msgString = msgString+ " Overtime detail exist for " + "<B>"+ emp.getFullName() +"</B>"+ " " + CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode] +" "+ "<a href=\"javascript:openOvertime(\'" + overtimeDetail.getOvertimeId() + "\');\">" + overtimeDetail.getOvt_doc_nr() + "</a> ; <br>";
                        
                    }
                    //update by satrya 2013-01-14
                    //pengecekan jika tanggal date form lebih besar
                   if ((ovd.getDateFrom().getTime() >= ovd.getDateTo().getTime())) {
                    Employee emp = PstEmployee.fetchExc(ovd.getEmployeeId());
                 
                    iErrCode = CtrlOvertimeDetail.RSLT_FORM_END_TIME_PRIOR_TO_START_TIME ;
                    msgString = msgString + " Overtime detail " + "<B>"+emp.getFullName() +"</B>"+ " " + CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode]+"<br> ";
                    
                  }
                    
                     Vector ovLstSameHour = PstOvertimeDetail.listOvertimeOverlapVer4(oidOvertime,0, 1, 0, "", ovd.getDateFrom(), ovd.getDateTo(),
                                        0, "", ovd.getEmployeeId(), "");  
                    //update by satrya 2013-09-04
                    // Vector ovLst = PstOvertimeDetail.listOvertimeOverlap(oidOvertime,0, 1, 0, "", ovd.getDateFrom(), ovd.getDateTo(),
                                      //  0, "", ovd.getEmployeeId(), ""); 
                    if(ovLstSameHour!=null && ovLstSameHour.size()>0){
                        Employee emp = PstEmployee.fetchExc(ovd.getEmployeeId());
                        OvertimeDetail overtimeDetail = (OvertimeDetail)ovLstSameHour.get(0);
                        iErrCode = CtrlOvertimeDetail.RSLT_EST_CODE_EXIST ;
                        msgString = msgString+ " Overtime detail exist for Hour " + "<B>"+ emp.getFullName() +"</B>"+ " " + CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode] +" "+ "<a href=\"javascript:openOvertime(\'" + overtimeDetail.getOvertimeId() + "\');\">" + overtimeDetail.getOvt_doc_nr() + "</a> ; <br>";
                        
                    }
                     
                    if((iErrCode == CtrlOvertimeDetail.RSLT_OK || (msgString!=null && msgString.length()<0)) && cekError){   
                     PstOvertimeDetail.insertExc(ovd);
                     //update by satrya 2014-05-15 karena jika true saja dan ada eror salah satu jadi tidak muncul errornya
                     if((msgString!=null && msgString.length()<0) && cekError){
                         noErorrOvertime=true;
                     }
                     submitIsOk=true;

                  }
                }                 
               
             } catch(Exception exc){
                 System.out.println(exc);
                 submitIsOk = false;
             }
            }            
        }
        ///update by satrya 2013-01-14
        else{
            empLstIsNotNull=true;
        }
    }    
    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 100;
    int vectSize = 0;
    
    String msgStr = "";
    String orderClause = "";
//    String whereClause = "RESIGNED = 0";

    SessEmployee sessEmployee = new SessEmployee();
    srcEmployee.setAddWhere( "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " NOT IN ( SELECT ov."+
        PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID]+" FROM "+ PstOvertimeDetail.TBL_OVERTIME_DETAIL+
        " ov WHERE " + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID]+ "="+oidOvertime +") ");
           
    vectSize = sessEmployee.countEmployee(srcEmployee);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);
    }else{
        start = 0;
    }
   Vector listEmployee = new Vector(1,1);
   try{
        listEmployee = sessEmployee.searchEmployee(srcEmployee, start, recordToGet, " AND "+srcEmployee.getAddWhere());
   }catch(Exception ex){
   
   }
%>


<!DOCTYPE HTML>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search Employee</title>
<style type="text/css">
.tooltip {
	display:none;
	position:absolute;
	border:1px solid #333;
	background-color:#161616;
	border-radius:5px;
	padding:10px;
	color:#fff;
	font-size:12px Arial;
}
</style>
<script type="text/javascript" src="../../javascripts/jquery.min-1.6.2.js"></script>

<script type="text/javascript">
$(document).ready(function() {
        // Tooltip only Text
        $('.masterTooltip').hover(function(){
                // Hover over code
                var title = $(this).attr('title');
                $(this).data('tipText', title).removeAttr('title');
                $('<p class="tooltip"></p>')
                .text(title)
                .appendTo('body')
                .fadeIn('fast');
        }, function() {
                // Hover out code
                $(this).attr('title', $(this).data('tipText'));
                $('.tooltip').remove();
        }).mousemove(function(e) {
                var mousex = e.pageX + 20; //Get X coordinates
                var mousey = e.pageY + 10; //Get Y coordinates
                $('.tooltip')
                .css({ top: mousey, left: mousex })
        });
});
</script>
<script language="JavaScript">
    
<% if(submitIsOk) {%>
    
   //self.opener.cmdBackOv();
   window.opener.location.reload(true);
    
    //window.close();//in IE
    
   // document.searchEmp.action= window.close();///firefox mau
    <%}%>
<%if(hashCekOverlap==null || submitIsOk && hashCekOverlap.size()==0 && noErorrOvertime){%>       
    window.close();
<%}%>
<%if(empLstIsNotNull){%>
    alert("You have not selected employee");
    window.close();//in IE
<%}%>
function cmdEdit(oid, number, fullname, department) {
        self.opener.document.<%=formName%>.<%=empPathId%>.value = oid;
        self.opener.document.<%=formName%>.<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_PAYROLL]%>.value = number;
        self.opener.document.<%=formName%>.<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_NAME]%>.value = fullname;
        //self.opener.document.<%=formName%>.EMP_DEPARTMENT.value = department;
        self.close();
    }
    
function submitSelected(){

	document.searchEmp.command.value="<%=String.valueOf(Command.SUBMIT)%>";
	document.searchEmp.action="src_ovemployee.jsp"; 
	document.searchEmp.submit();     
}
//update by satrya 2013-06-13
function submitSelectedOverlap(){
        document.searchEmp.command.value="<%=String.valueOf(Command.CONFIRM)%>";
	document.searchEmp.action="src_ovemployee.jsp"; 
	document.searchEmp.submit();     
}

function cmdAdd() {
        window.open("editEmployee.jsp", "edit_employee", "height=580,width=900, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes,resizable=no,top=50,left=50");
    }
    
function cmdUpdateDep(){
	document.searchEmp.command.value="<%=String.valueOf(Command.ADD)%>";
	document.searchEmp.action="src_ovemployee.jsp"; 
	document.searchEmp.submit();
}

function deptChange() {
    document.searchEmp.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.searchEmp.hidden_goto_dept.value = document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>.value;
    document.searchEmp.action = "src_ovemployee.jsp";
    document.searchEmp.submit();
}

function cmdSearch() {
    document.searchEmp.command.value = "<%=String.valueOf(Command.LIST)%>";									
    document.searchEmp.action = "src_ovemployee.jsp";
    document.searchEmp.submit();
}

//-------------- script control line -------------------
        function cmdListFirst(){
		document.searchEmp.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.searchEmp.action="src_ovemployee.jsp";
		document.searchEmp.submit();
	}

	function cmdListPrev(){
		document.searchEmp.command.value="<%=String.valueOf(Command.PREV)%>";
		document.searchEmp.action="src_ovemployee.jsp";
		document.searchEmp.submit();
	}

	function cmdListNext(){
		document.searchEmp.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.searchEmp.action="src_ovemployee.jsp";
		document.searchEmp.submit();
	}

	function cmdListLast(){
		document.searchEmp.command.value="<%=String.valueOf(Command.LAST)%>";
		document.searchEmp.action="src_ovemployee.jsp";
		document.searchEmp.submit();
	}
        
         function openOvertime(oidOvertimeForm)
{
	newWindow = window.open("ovdetail.jsp?overtime_oid="+ oidOvertimeForm +"&command="+<%=Command.EDIT%>+"&prev_command="+<%=Command.EDIT%>+"\""
                    ,"OpenDetailOvertime", "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
                newWindow.focus();  
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
// -->



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

</script>


<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
    
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
    <tr><td id="ds_calclass">
    </td></tr>
</table> 
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >

  
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                Search Employee
                <!-- #EndEditable --> 
                  </strong></font> </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                <% if (privStart) { %>
                                    <form name="searchEmp" method="post" action="">
                                    <%if(iCommand == Command.SAVE || iCommand == Command.ACTIVATE){ %>
                                        <input type="hidden" name="command" value="<%=String.valueOf(Command.LIST)%>">
                                    <%}else{%>
                                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <%}%>
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>" value="<%=oidDepartment%>">
                                    <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION]%>" value="<%=oidSection%>">
                                    <input type="hidden" name="formName" value="<%=String.valueOf(formName)%>">
                                    <input type="hidden" name="empPathId" value="<%=String.valueOf(empPathId)%>">
                                    <!--<input type="hidden"  name="<%//=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>" value="">-->
                                    <!--<input type="hidden"  name="<%//=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION]%>" value="">-->									
                                    <input type="hidden" name="overtime_oid" value="<%=oidOvertime%>">
                                    
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="19%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>"  value="<%=employeeName!=null && employeeName.length()>0?employeeName:String.valueOf(srcEmployee.getName())%>" class="elemenForm" size="40">
                                                  <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>"  value="<%=employeeName%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER]%>"  value="<%=employeeNumber!=null && employeeNumber.length()>0?employeeNumber:String.valueOf(srcEmployee.getEmpnumber())%>" class="elemenForm">
                                                  <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER]%>"  value="<%=employeeNumber%>" class="elemenForm">
                                                </td>
                                              </tr>
                                             
                                              <tr> 
                                                <td width="19%">Category</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <% 
							Vector cat_value = new Vector(1,1);
							Vector cat_key = new Vector(1,1);        
							cat_value.add("0");
							cat_key.add("all category ...");                                                          
							Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
							for (int i = 0; i < listCat.size(); i++) {
								EmpCategory cat = (EmpCategory) listCat.get(i);
								cat_key.add(cat.getEmpCategory());
								cat_value.add(String.valueOf(cat.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMP_CATEGORY],"formElemen",null,String.valueOf(srcEmployee.getEmpCategory()), cat_value, cat_key, "") %> </td>
                                              </tr>
					      
                                              <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RESIGNED]%>" value="0">
                                              <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SEX]%>" value="3">
                                              
                                              <tr> 
                                                <td width="19%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="80%"> 
                                                  <input type="submit" name="Submit" value="Search Employee" onClick="javascript:cmdSearch()">
                                                <!--  <input type="submit" name="Submit" value="Add Employee" onClick="javascript:cmdAdd()">
                                                -->                                                </td>
                                              </tr>
                                            </table>
                                        </td>
                                      </tr>
                                      <%if((msgString!=null && msgString.length()>0) || (iErrCode != CtrlOvertimeDetail.RSLT_OK)){%>
                                      <tr bgcolor="#FFFF00">
                                            <td>
                                                <table>
                                                    <tr>
							<td width="17"><img src="<%=approot%>/images/warning.png" width="17" height="17%"></td>
                                                        <td width="1"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                        <td><%=msgString %></td>
                                                    </tr>
						</table>
                                            </td>
                                       </tr>
                                      <%}%>
                                      <tr>
                                        <td> 
                                            
                                          <% if (listEmployee.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST) || (iCommand==Command.SUBMIT))) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <%if((listEmployee!=null)&&(listEmployee.size()>0)){%>
                                                <tr> 
                                                  <td height="8" colspan="8">
                                                <table>
                                                 <tr>  
                                                  <td nowrap>
                                                      <%
                                                        Date startTimeX = new Date();
                                                        startTimeX.setHours(17);
                                                        startTimeX.setMinutes(0);
                                                        Date endTimeX = new Date();
                                                        endTimeX.setHours(18);
                                                        endTimeX.setMinutes(0);                
                                                      %>
                                                  From <%=ControlDate.drawDate(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], dateFrom!=null ? dateFrom:startTimeX, "formElemen", 1, -5)%></td>
                                                  <td align="left" nowrap><%=ControlDate.drawTime(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], dateFrom!=null ? dateFrom:startTimeX, "formElemen", 24, 1, 0)%></td>
                                                  <%out.println(ControlDatePopup.writeDateDisabled(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM],dateFrom));%>
                                                  <td >&nbsp;</td>
                                                  <td nowrap>To <%=ControlDate.drawDate(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], dateTo!=null ?dateTo:endTimeX, "formElemen", 1, -5)%></td>
                                                  <td align="left" nowrap><%=ControlDate.drawTime(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], dateTo!=null ?dateTo:endTimeX, "formElemen", 24, 1, 0)%></td>                                                  
                                                  <%out.println(ControlDatePopup.writeDateDisabled(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO],dateTo));%>
                                                  <!--<td >&nbsp;</td>
                                                  <td nowrap>Rest time </td>
                                                  <td >&nbsp;<input type="text" value="0" name="<%//=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_HR] %>" size="4" maxlength="4"></td>
                                                  <td >(hour)</td>-->
                                                </tr>
                                            <%if(isHRDLogin){%>
                                                <tr>
                                                  <td nowrap>
                                                  Rest from <%=ControlDate.drawDate(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_START],restStart!=null ?restStart: startTimeX, "formElemen", 1, -5)%></td>
                                                  <td align="left" nowrap><%=ControlDate.drawTime(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_START], restStart!=null ?restStart:startTimeX, "formElemen", 24, 1, 0)%></td>
                                                  <%out.println(ControlDatePopup.writeDateDisabled(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_START],restStart));%>
                                                  <td >&nbsp;</td>
                                                  <td nowrap>for <input type="text" value="0" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_HR] %>" size="4" maxlength="4">hours</td>
                                                <input type="hidden" value="<%=restTime%>" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_HR] %>" size="4" maxlength="4">
                                                  <td align="left" nowrap></td>                                                                                                      
                                                <td >&nbsp;</td>
                                                  <td nowrap>&nbsp;</td>
                                                  <td >&nbsp;</td>
                                                  <td >&nbsp;</td>
                                                </tr>
                                            <%}else if(!isHRDLogin){ 
                                    Date restDt = new Date();//jika overtime.getRestTimeStart() nilainya null
                                    restDt.setHours(0);
                                    restDt.setMinutes(0);
                                    restDt.setSeconds(0);
                                     out.println(ControlDatePopup.writeDateTimeDisabled(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_START],(restStart!=null ?restStart: restDt)));
                                %>                                                                
                                <%}%>
                                               </table>
												  </td>
                                                </tr>
                                                                                              
                                                <tr> 
                                                  <td height="8" colspan="8">&nbsp;</td>
                                                </tr>
                                                <tr> 
                                                 <%if(hashCekOverlap!=null && hashCekOverlap.size()>0){%>
                                                  <td height="8" colspan="8"><a href="Javascript:SetAllCheckBoxes('searchEmp','empoid', true)">Select All</a> | <a href="Javascript:SetAllCheckBoxes('searchEmp','empoid', false)">Deselect All</a>
                                                   | <a href="Javascript:submitSelectedOverlap()" title="<%=message[CtrlOvertimeDetail.LANGUAGE_FOREIGN][ErrOverlap]%>" class="masterTooltip">Submit Selected Employee Without Overlap Schedule</a></td>
                                                <%}else{%>
                                                   <td height="8" colspan="8"><a href="Javascript:SetAllCheckBoxes('searchEmp','empoid', true)">Select All</a> | <a href="Javascript:SetAllCheckBoxes('searchEmp','empoid', false)">Deselect All</a>
                                                   | <a href="Javascript:submitSelected()">Submit Selected Employee</a></td>
                                                <%}%>
                                                </tr>
                                                <tr> 
                                                  <td height="8" colspan="8"><%=drawList(listEmployee, start,hashCekOverlap)%></td>
                                                </tr>
                                                <%}else{%>
                                                <tr> 
                                                  <td height="8" width="67%" class="comment"><span class="comment"><br>
                                                    &nbsp;No Employee available</span>                                                  </td>
                                                </tr>
                                                <%}%>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>                                                </td>
                                              </tr>
                                            </table>
                                          <% } %>
                                          </td>
                                          </tr>
                                        </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
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
</table>
<sript language="Javascript" >                                
<% if(submitIsOk) {%>
    
    self.close();
<%}%>
</sript>

</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

