<%-- 
    Document   : outlet
    Created on : Feb 25, 2014, 3:05:26 PM
    Author     : Satrya Ramayu
--%>


<%@page import="com.dimata.harisma.entity.attendance.PstPresence"%>
<%@page import="com.dimata.harisma.entity.overtime.TmpOvertimeReportDaily"%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApp"%>
<%@page import="com.dimata.harisma.utility.service.presence.PresenceAnalyser"%>
<%@page import="com.dimata.harisma.session.payroll.SessOvertime"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertime"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.session.payroll.I_PayrollCalculator"%>
<%@page import="com.lowagie.text.Document"%>
<%@page import="com.dimata.qdep.db.DBHandler"%>
<%@page import="org.apache.poi.hssf.record.ContinueRecord"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.overtime.Overtime"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@ page language="java" %>


<%@ page import ="java.util.*"%>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.*" %>

<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>

<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import ="com.dimata.harisma.session.attendance.*"%>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>

<%@ include file = "../../main/checkuser.jsp" %>
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

<%!
    public String drawList(Vector employee, Hashtable hashSymbol,Date dateFrom,Hashtable msgSuccess) {

       ControlList ctrlist = new ControlList(); //membuat new class ControlList

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("tableheader");
         ctrlist.setMaxFreezingTable(2);
        ctrlist.setRowSelectedStyle("tabtitlehidden");
        ctrlist.addHeader("Payroll Number", "10%");
        ctrlist.addHeader("FullName", "20%");
        ctrlist.addHeader("Tanggal", "20%");
        ctrlist.addHeader("Symbol", "20%");
        ctrlist.addHeader("<a href=\"Javascript:SetAllCheckBoxes('frpresence','cek', true)\">All</a> | <a href=\"Javascript:SetAllCheckBoxes('frpresence','cek', false)\">Deselect All</a> ", "60%");
         
       // ctrlist.setLinkRow(0);
        
        
        Vector lstData = ctrlist.getData();
        

        Vector lstLinkData = ctrlist.getLinkData();

        //ctrlist.setLinkPrefix("");
       // ctrlist.setLinkPrefix("javascript:cmdEdit('");
       

        //ctrlist.setLinkSufix("");
        ctrlist.setLinkSufix("')");

        ctrlist.reset();

        int index = -1;
        //int no = start+1;

        for (int i = 0; i < employee.size(); i++) {

            Employee emp = (Employee) employee.get(i); 

            Vector rowx = new Vector();

            if (emp.getEmployeeNum() !=null) {
                index = i;
            }
            String date="";
            date=Formater.formatDate(dateFrom, "yyyy-MM-dd"); 
            String syymbol="";
            String msgPesan="";
        if(hashSymbol !=null && hashSymbol.containsKey(emp.getOID())){  
            syymbol=(String)hashSymbol.get(emp.getOID());
        }
            //rowx.add(""+no); no++;
            rowx.add(emp.getEmployeeNum());
            rowx.add(emp.getFullName());
            rowx.add(date);
             rowx.add(syymbol);
            if(msgSuccess !=null && msgSuccess.containsKey(emp.getOID())){
                msgPesan=(String)msgSuccess.get(emp.getOID());
            }
            rowx.add("<input type=\"checkbox\" name=\"cek\" value=\""+emp.getOID()+"\"/><input type=\"hidden\" name=\"symbol\" value=\""+syymbol+"\" ><x style=\"color:blue\"><b>" + msgPesan +"</b></x>"); 
            
               
             
           
            
            

            //rowx.add("<a href=javascript:cmdDelete('"+siswa.getOID()+"')>"+"<img src=GAMBAR/Symbol-Delete.png>"+"</a>");
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(emp.getOID()));

        }

        return ctrlist.draw(index);//index diatas ditampilkan

    }

%>



<%
  CtrlPresence ctrlPresence = new CtrlPresence(request); 
 int oidSchedule = FRMQueryString.requestInt(request, "oidSchedule");
 
 Date searchDateParam = FRMQueryString.requestDateVer3(request, "check_date_start");
    
     Date inputDateFrom = FRMQueryString.requestDateVer3(request, FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME]);
    Date  inputDateTo = FRMQueryString.requestDateVer3(request, FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME_END]);
    Date  inputTime = FRMQueryString.requestDateVer3(request, FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_TIME]);
   
    String empNum = FRMQueryString.requestString(request, "emp_number");
    
long diffStartToFinish = 0;

int itDate = 0;
if(inputDateFrom !=null && inputDateTo !=null){ 
    
 diffStartToFinish= inputDateTo.getTime() - inputDateFrom.getTime();  
 itDate= Integer.parseInt(String.valueOf(diffStartToFinish / 86400000))+1;
       
} 

 
  String  fullname = FRMQueryString.requestString(request, "full_name");  
 FrmPresence frmPresence = new FrmPresence();  
 String errMsg = "";
      
    Employee employeee = new Employee();  
    //FrmPresence frpresence = new FrmPresence();
    int iCommand = FRMQueryString.requestCommand(request);
    int resign=PstEmployee.NO_RESIGN; 
   
     int start = FRMQueryString.requestInt(request, "start");
    //String  whereClause ="";
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidPresence = FRMQueryString.requestLong(request, "presence_id");
    
    //update by satrya 2013-1202
    long oidCompany = FRMQueryString.requestLong(request, "hidden_companyId");
     long oidDivision = FRMQueryString.requestLong(request, "hidden_divisionId");
    
    long oidSection = FRMQueryString.requestLong(request, "section");
  
    String source = FRMQueryString.requestString(request, "source");
    
    
     
  %>
  <%
  Vector listEmployee = new Vector();  
  String schSymbol = "";  
  Hashtable symbol = new Hashtable();
  Vector kumSymbol = new Vector(); 
  String orderEmp = "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" ASC ";
  if(iCommand==Command.LIST || iCommand==Command.SAVE){
      
      String order = "DATE("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, "
                        + "TIME("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, " 
                        + PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]
                       + " ASC ";  

          if(employeee!=null){
	  listEmployee = PstEmployee.listEmp(0, 0, orderEmp , empNum.trim(), fullname.trim(), oidDepartment, oidSection,oidCompany,oidDivision,resign);    
                 
                 
          }else{
              listEmployee = PstEmployee.listEmp(0,0,orderEmp , empNum.trim(), fullname.trim(), oidDepartment, oidSection,oidCompany,oidDivision,resign);      
          }
  }
  if(listEmployee!=null && listEmployee.size()>0){
     
          symbol = PstEmpSchedule.cariSymbol(searchDateParam);
          if(symbol!=null && symbol.size()>0){
              
          }
          
      
  }

       
    if(iCommand==Command.SAVE){
 
       int iErrCode = ctrlPresence.actionn(iCommand , oidPresence, request,itDate,inputTime,inputDateFrom);   
        
        }
  Hashtable msgSuccess = ctrlPresence.msgSuccess();  

errMsg = ctrlPresence.getMessage();  
FrmPresence frmPresencee = ctrlPresence.getForm();     
Presence presence = ctrlPresence.getPresence();
%>
       
<!DOCTYPE html>

<html>
    <head>
        <title>Harisma - Many Employee Manual Presence</title>
      
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
               <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
    <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
    <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
             <!-- #BeginEdiable "doctitle" --> 
      
        <!--update by satrya 2012-08-12 -->
        <script type="text/javascript" src="../../javascripts/jquery.min-1.6.2.js"></script>

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
<style type="text/css">
      
            .bdr{border-bottom:2px dotted #0099FF;}
		
		.highlight {
			color: #090;
		}
		
		.example {
			color: #08C;
			cursor: pointer;
			padding: 4px;
			border-radius: 4px;
		}
		
		.example:after {
			font-family: Consolas, Courier New, Arial, sans-serif;
			content: '?';
			margin-left: 6px;
			color: #08C;
		}
		
		.example:hover {
			background: #F2F2F2;
		}
		
		.example.dropdown-open {
			background: #888;
			color: #FFF;
		}
		
		.example.dropdown-open:after {
			color: #FFF;
		}
		
	</style>
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

            function fnTrapKD(){
                if (event.keyCode == 13) {
                    //document.all.aSearch.focus();
                    cmdView();
                }
            }
                      //update by satrya 2012-08-21
        <%
            Vector listScheduleSymbol = new Vector(1, 1);
            listScheduleSymbol = PstScheduleSymbol.list(0, 500, "", PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]);

        %>
//script untuk cek resolusi browser yg di gunakan
  /*if (window.screen){
var w = screen.width;
tmt_url_640 = "presence_report_daily_20130522.jsp";
    if(w>=1250){
    self.location.replace(tmt_url_640);
    }
}*/
  
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

         function  checkSymbol(obj){
             if(obj==null)
                 return;
             var symbol= obj.value;
             if(symbol!=null){
                 <% if(listScheduleSymbol!=null){ 
                     for(int i=0;i < listScheduleSymbol.size(); i++ ){
                    ScheduleSymbol sch = (ScheduleSymbol)listScheduleSymbol.get(i);
                    %>
                 if(symbol=="<%=sch.getSymbol()%>"){
                     return;
                 }
               
                 <%}
                    }%>
                    alert(symbol+" is not a schedule symbol / please check the capital letters " + symbol.toUpperCase() );
                 obj.focus();
             }
         }
            function cmdEditAbsence( scheduleId, dateIdx){
                var linkPage = "<%=approot%>/employee/absence/absence_edit.jsp?source=presence&command=<%=(Command.EDIT)%>&hidden_absence_id="+scheduleId+"&hidden_absence_date="+dateIdx; 
                //window.open(linkPage,"Absence Edit","height=600,width=800,status=yes,toolbar=yes,menubar=yes,location=yes");  			
                var newWin = window.open(linkPage,"Absence_Edit","height=600,width=800,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=no");  			
                newWin.focus();
            }

            function cmdEditPresence(empNum,symbol,date){
                document.frpresence.action="edit_presence.jsp?empNum=" + empNum+ "&symbol=" + symbol+ "&date=" +date;
                document.frpresence.submit();
            }

            function cmdNewLeave(employeeId, requestDateDaily){            
                var linkPage = "<%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<%=(Command.ADD)%>&employeeId="+employeeId+"&requestDateDaily="+requestDateDaily; 
                var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }

            function cmdEditLeave(employeeId, requestDateDaily){            
                //var linkPage = "<!--%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<!--%=(Command.EDIT)%-->"; 
                var linkPage = "<%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<%=(Command.EDIT)%>&employeeId="+employeeId+"&requestDateDaily="+requestDateDaily; 
                //window.open(linkPage,"Leave","height=600,width=800,status=yes,toolbar=yes,menubar=no,location=no");  			
                var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }
            //update by satrya 2012-08-011
            function cmdEditAttendace(employeeId, requestDateDaily){            
                //var linkPage = "<!--%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<!--%=(Command.EDIT)%-->"; 
                var linkPage = "<%=approot%>/report/presence/attendance_edit.jsp?source=presence&command=<%=(Command.EDIT)%>&employeeId="+employeeId+"&requestDateDaily="+requestDateDaily; 
                //window.open(linkPage,"Leave","height=600,width=800,status=yes,toolbar=yes,menubar=no,location=no");  			
                var newWin = window.open(linkPage,"Leave","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }
             function cmdEditAttendaceManual(employeeId, requestDateDaily){            
                //var linkPage = "<!--%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<!--%=(Command.EDIT)%-->"; 
                var linkPage = "<%=approot%>/employee/presence/attd_edit_dutty.jsp?source=presence&employeeId="+employeeId+"&requestDateDaily="+requestDateDaily; 
                //window.open(linkPage,"Leave","height=600,width=800,status=yes,toolbar=yes,menubar=no,location=no");  			
                var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }
        

            function cmdView(){
                document.frpresence.command.value="<%=Command.LIST%>";
                document.frpresence.start.value=0; 
                 document.frpresence.source.value="";
                document.frpresence.action="manual_attendance_all_employee.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }
             function cmdRefresh(){
                document.frpresence.command.value="<%=Command.REFRESH%>";
                  document.frpresence.source.value="";
                //document.frpresence.start.value=0; 
                document.frpresence.action="manual_attendance_all_employee.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }
            function cmdSave(){
		document.frpresence.command.value="<%=String.valueOf(Command.SAVE)%>"; 
		document.frpresence.action="manual_attendance_all_employee.jsp";
		document.frpresence.submit();
                
	}
            

            
function cmdUpdateDiv(){
    document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frpresence.action="manual_attendance_all_employee.jsp";
    document.frpresence.target = "";
    document.frpresence.submit();
}
function cmdUpdateDep(){
    document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frpresence.action="manual_attendance_all_employee.jsp";
    document.frpresence.target = "";
    document.frpresence.submit();
}
function cmdUpdatePos(){
    document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frpresence.action="manual_attendance_all_employee.jsp";
    document.frpresence.target = "";
    document.frpresence.submit();
}

            function updateDaily(oid){
                document.frpresence.command.value="<%=Command.SAVE%>";
                 document.frpresence.source.value="updateSchedule";
                document.frpresence.hidden_empschedule_id.value = oid;
                document.frpresence.action="manual_attendance_all_employee.jsp"; 
                document.frpresence.target = "";
                document.frpresence.submit();        
            }
            function updateAndAnalyzeDaily(oid){
                document.frpresence.command.value="<%=Command.SAVE%>";
                document.frpresence.source.value="analyzePresence";
                document.frpresence.hidden_empschedule_id.value = oid;
                document.frpresence.action="manual_attendance_all_employee.jsp"; 
                document.frpresence.target = "";
                document.frpresence.submit();   
                //alert("test");
            }
            
            function updateOvertime(oid){
                document.frpresence.command.value="<%=Command.POST%>";
                 document.frpresence.source.value="";
                document.frpresence.hidden_empschedule_id.value = oid;
                document.frpresence.action="manual_attendance_all_employee.jsp"; 
                document.frpresence.target = "";
                document.frpresence.submit();        
            }
            function cmdListFirst(){
		document.frpresence.command.value="<%=Command.FIRST%>";
		document.frpresence.action="manual_attendance_all_employee.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListPrev(){
		document.frpresence.command.value="<%=Command.PREV%>";
		document.frpresence.action="manual_attendance_all_employee.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListNext(){
		document.frpresence.command.value="<%=Command.NEXT%>";
		document.frpresence.action="manual_attendance_all_employee.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListLast(){
		document.frpresence.command.value="<%=Command.LAST%>";
		document.frpresence.action="manual_attendance_all_employee.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}
        function reportExcel(){	 
                var linkPage = "<%=printroot%>.report.attendance.AttendanceReportDailyXls"; 
                document.frpresence.action="<%=printroot%>.report.attendance.AttendanceReportDailyXls"; 
                document.frpresence.target = "ReportExcel";
                document.frpresence.submit();
                document.frpresence.target = "";
        }
        function reportPdf(){	 
                var linkPage = "<%=printroot%>.report.staffcontrol.AttRecordDailyPdf"; 
                document.frpresence.target = "";
                window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
        }
        function reportPerEmployeePdf(){	 
                var linkPage = "<%=printroot%>.report.staffcontrol.AttRecordDailyPerEmployeePdf"; 
                document.frpresence.action="<%=printroot%>.report.staffcontrol.AttRecordDailyPerEmployeePdf"; 
                document.frpresence.target = "ReportPerEmployee";
                document.frpresence.submit();
                document.frpresence.target = "";
                //window.close(<%=printroot%>"/servlet/com.dimata.harisma.report.staffcontrol.AttRecordDailyPerEmployeePdf");
            }
      
	window.history.forward();
	function noBack() { window.history.forward(); }
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
        </script>
        <script type="text/javascript">
function reload()
{
	if (!document.location.href.match(/checkit/))
	{
		var parameter = (document.location.href.match(/\?/) ? '&' : '?') + 'checkit';
		document.location.href = document.location.href + parameter;
	}
	else
		document.location.reload();
}

window.onload = function()
{
	if (document.location.href.match(/checkit/))
		document.myform.cb.checked = true;
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
</script>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        
            <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
    <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
    <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
     <!-- untuk menampilkan tooltips -->
	<link type="text/css" rel="stylesheet" href="../../stylesheets/jquery.dropdown.css" />
	<script type="text/javascript" src="../../javascripts/jquery.dropdown.js"></script>
  
     
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
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
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
                                                Master Data &gt; Many Employee Manual Presence
                                            </strong></font>
                                        </td>
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
                                                                            <td valign="top">

                                                                                <form name="frpresence" method="post" action="" >
                            <input type="hidden" name="hidden_empschedule_id" value="<%=iCommand%>"/>
                            <!-- input type="hidden" name="command" value="<//%=iCommand%>" -->
                            <input type="hidden" name="command" value=""/>
                           
                            <input type="hidden" name="start" value="<%=String.valueOf(start)%>"/>
                            <input type="hidden" name="source" value="<%=String.valueOf(source)%>"/>
                            <input type="hidden" name="presence_id" value="<%=String.valueOf(oidPresence)%>">
                            <!--input type="hidden" name="hidden_empschedule_id" value="<//%=oidEmpSchedule%>"-->
                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                <!-- update by satrya 2012-07-16 -->
                                <tr>
                                  <td width="6%" nowrap="nowrap"><div align="left">Payrol Num </div></td>
                                  <td width="30%" nowrap="nowrap">:
                                    <input class="masterTooltip" type="text" size="40" name="emp_number"  value="<%=empNum  %>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                  </td>
                                  <td width="5%" nowrap="nowrap"> Full Name </td>
                                  <td width="59%" nowrap="nowrap">:
                                    <input class="masterTooltip" type="text" size="50" name="full_name"  value="<%=fullname%>"  title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                  </td>
                                </tr>
                                <tr>
              <td width="6%" nowrap="nowrap"><div align="left">Company </div></td>
              <td width="30%" nowrap="nowrap">:
                <%
					Vector comp_value = new Vector(1, 1);
					Vector comp_key = new Vector(1, 1);  
String whereComp="";   
/*if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
    whereComp = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+srcOvertime.getCompanyId();
}*/   
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
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+  whereDiv +")": whereDiv;
                    
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
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+ whereClsDep +")":whereClsDep;
                     
                }
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
     div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }               
    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    String prevCompany="";
    String prevDivision="";
    


long prevCompanyTmp=0;        
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                //}
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
             //untuk karyawan admin yg hanya bisa akses departement tertentu (ketika di awal)
             ////update
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                if(oidCompany!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

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
                    if(oidCompany!=0){
                       div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision();             
                   }
                    //update by satrya 2013-09-19
                    else if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){
                        div_key.add(dept.getDivision());
                        div_value.add(""+dept.getDivisionId()); 
                        
                        //update by satrya 2013-09-19
                        dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                   }
                   
                }else{
                    
                     div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                }
            }
        }
 }else{
      if(oidCompany!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
       }
 }
            
            }
           else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }  
			%>
                <%= ControlCombo.draw("hidden_companyId", "formElemen", null, ""+oidCompany, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> </td>
              <td width="5%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
              <td width="59%" nowrap="nowrap">:
                <%
					
                                   //update by satrya 2013-08-13
                                   //jika user memilih select kembali
                                   if(oidCompany==0){ 
                                      oidDivision=0; 
                                   }

if(oidCompany!=0){
    whereComp = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp) + ") AND " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+oidCompany;
 listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0;  
long tmpFirstDiv=0;   

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
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
    div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }
   long prevDivTmp=0;
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //update
                if(oidDivision!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                }
                //lama
                /*
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
                */
                
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
           String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             //comp_key.add(dept.getCompany());
             //comp_value.add(""+dept.getCompanyId());
             
            
             
             if(prevDivTmp!=dept.getDivisionId()){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 
                    prevDivTmp=dept.getDivisionId();
              }
             
                    tmpFirstDiv=dept.getDivisionId(); 

                   // dept_key.add(dept.getDepartment());
                 //   dept_value.add(String.valueOf(dept.getOID()));           
               
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }
           /*else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }*/
          String chkAdaDpt="";
          if(whereComp!=null && whereComp.length()>0){
                chkAdaDpt = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp)+ ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+oidDivision;
          }
            Vector listCheckAdaDept = PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt);
            if((listCheckAdaDept==null || listCheckAdaDept.size()==0)){
                
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                
                oidDivision=tmpFirstDiv;
              
            }
        }
 }else{
    oidDivision = tmpFirstDiv;
     
 }
               
            }
        }
    }
}
			%>
                <%= ControlCombo.draw("hidden_divisionId", "formElemen", null,""+oidDivision , div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%> </td>
            </tr>
            <tr>
              <td width="6%" align="right" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
              <td width="30%" nowrap="nowrap"> :
                <%

            //update by satrya 2013-08-13
            //jika user memilih select kembali
            if(oidDepartment==0){  
                oidSection=0;
            }
if(oidDivision!=0){
    if(whereComp!=null && whereComp.length()>0){
        whereComp = "("+whereComp + ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+oidDivision;
    }
    
    listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0; 

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
 }else{
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
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             comp_key.add(dept.getCompany());
             comp_value.add(""+dept.getCompanyId());
             
             
             div_key.add(dept.getDivision());
             div_value.add(""+dept.getDivisionId()); 
              
             dept_key.add(dept.getDepartment());
             dept_value.add(String.valueOf(dept.getOID()));
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }
}
			%>
                <%= ControlCombo.draw("department", "formElemen", null, "" + oidDepartment, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%> </td>
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
                <%=ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key)%></td>
            </tr>
            <tr>
              <td width="6%" align="right" nowrap><div align=left>Date</div></td>
              <td width="30%" nowrap="nowrap">:
                <!--%//=ControlDate.drawDate("date", date == null || iCommand == Command.NONE ? new Date() : date, "formElemen", 0, installInterval)%-->
                <!--//update by satrya 2012-7-18-->
                <% 
					//String selectDateStart = "" + searchDateParam; 
				   //String selectDateFinish 
				Date st = new Date();
				st.setHours(0);
				st.setMinutes(0);
				st.setSeconds(0);
				Date end = new Date(); 
				end.setHours(23); 
				end.setMinutes(59); 
				end.setSeconds(59);
				 String ctrTimeStart = ControlDate.drawTime("check_date_start",  searchDateParam != null ? searchDateParam : st, "elemenForm", 24,0, 0); 
				
				 %>
                <%=ControlDate.drawDateWithStyle("check_date_start", searchDateParam != null ? searchDateParam : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeStart%>
                <%//=ControlDate.drawDateWithStyle("check_date_start", searchDateParam != null ? searchDateParam : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                
              <% //out.println("date "+date);
				//long periodId = PstPeriod.getPeriodIdBySelectedDate(date); 
			  %>
              <!-- update by satrya 2012-09-28 -->
              
            </tr>
            <tr>
              
              
            </tr>
             
              <tr>
              <td width="6%" nowrap><div align="left"></div></td>
              <td width="30%">
			  <table border="0" cellspacing="0" cellpadding="0" width="137">
                  <tr>
                    <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                    <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                    <td width="94" class="command" nowrap><a href="javascript:cmdView()">View 
                      Presence</a></td> 
                  </tr>
                 <!-- <tr>
                    <td width="16"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                    <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                    <td width="94" class="command" nowrap><a href="javascript:cmdSave()">Save</a></td> 
                  </tr>-->
                </table>
                     <br />
                    
				</td>
            </tr>
            <!-- ------------------------------------------------------------------------------ -->
            <%if(listEmployee !=null && listEmployee.size()>0){%>
            
            
            
            <table width="100%" border="0" bgcolor="#E0EDF0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td width="12%" valign="top" nowrap> 
                                                        <div align="left">Presence 
                                                          Date Time</div>
                                                      </td>
                                                      <td width="88%"> : 
                                                        <%=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], inputDateFrom==null?new Date():inputDateFrom,"formElemen", 1, -5)%> 
                                                                Until <%=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME_END], inputDateTo==null?new Date():inputDateTo,"formElemen", 1, -5)%>  
                                                        <%=ControlDate.drawTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_TIME], inputTime == null ? new Date():inputTime,"formElemen", 24, 1, 0)%>  
                                                        * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_PRESENCE_DATETIME)%>         
                                                        <%//if( srcPresence.getDatefrom()!=null){ 
                                                            //if(iCommand==Command.EDIT){%>
                                                                <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], presence.getPresenceDatetime() != null ? presence.getPresenceDatetime() : new Date(),"formElemen", 1, -5)%> 
                                                            <%//}else{%>
                                                                <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], srcPresence.getDatefrom(),"formElemen", 1, -5)%> 
                                                                 <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME_END], srcPresence.getDateto(),"formElemen", 1, -5)%>  
                                                            <%//}%>
                                                        <%//}else{%>
                                                        <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], presence.getPresenceDatetime() != null ? presence.getPresenceDatetime() : new Date(),"formElemen", 1, -5)%> 
                                                       <%//}%> 
                                                       <%//=ControlDate.drawTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], presence.getPresenceDatetime() != null ? presence.getPresenceDatetime() : new Date(),"formElemen", 24, 1, 0)%> 
                                                        * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_PRESENCE_DATETIME)%> 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" valign="top" nowrap> 
                                                        <div align="left">Status</div>
                                                      </td>
                                                      <td width="88%"> : 
                                                        <%-- <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_TIME_IN], presence.getTimeIn(),"formElemen", 1, -5)%>
                                                        <%//=ControlDate.drawTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_TIME_IN], presence.getTimeIn(), "elemenForm", 24, 1, 0) %> 
                                                        <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_TIME_IN)%>
                                                        <input type="text" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS]%>" value="<%=presence.getStatus()%>" class="formElemen">
                                                        <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_STATUS)%> --%>
                                                        <% 
                                                            Vector status_value = Presence.getStatusIndexString();
                                                            Vector status_key = Presence.getStatusAttString();
                                                            //Vector listDept = PstDepartment.listAll();
                                                            //for (int i = 0; i < listDept.size(); i++) {
                                                                    //Department dept = (Department) listDept.get(i);
                                                                    /*status_key.add("In"); status_value.add("0");
                                                                    status_key.add("Out - Home"); status_value.add("1");
                                                                    status_key.add("Out - On duty"); status_value.add("2");
                                                                    status_key.add("In - Lunch"); status_value.add("3");
                                                                    status_key.add("In - Break"); status_value.add("4");
                                                                    status_key.add("In - Callback"); status_value.add("5");
                                                                */
                                                            //}
                                                        %>
                                                        <%= ControlCombo.draw(frmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS] ,"formElemen",null, String.valueOf(presence.getStatus()), status_value, status_key) %> 
                                                         <%//= ControlCombo.draw(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS],"formElemen",null, String.valueOf(presence.getStatus()), status_value, status_key) %> 
                                                        * 
                                                      </td>
                                                    </tr>
                                                    
                                                  </table>
                                                        <br />
                                                        <table border="0" cellspacing="0"  cellpadding="0" width="137">
                                                    <tr>
                    <td width="16"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                    <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                    <td width="94" class="command" nowrap><a href="javascript:cmdSave()">Save</a></td> 
                  </tr>
                  </table>
                    <%}%>
                    <table>
                       
                        <tr>
                <%if(symbol!=null && symbol.size()>0 && listEmployee.size()>0 || iCommand==Command.SAVE){%>
                
             
                    <!-- untuk drawlist -->
                     <td nowrap><div align="left"></div></td>
                     <td width="100%%"><table><%=
                     
                       drawList(listEmployee, symbol,searchDateParam,msgSuccess) 
                     %></table> </td>
                    
                
             <%}else{%>
                 <td>
                    record not found
                </td>
             <%}%>
            </tr>
                    </table>
           
                    
                            </table>

          

              </td>
            </tr>
            <!-- update by satrya 2013-04-19 -->
           
          <tr>
              <!-- end -->
              
            </tr>
           
          <tr>
              <td></td>
            </tr>
          <tr>
              <td>&nbsp;</td>
            </tr>
         
           
           
          <tr>
              <td>&nbsp;</td>
            </tr>
          <tr>
              <td>&nbsp;</td>
            </tr>
            <!--<td width="2%" bgcolor="#FFFF00"></td>-->
         
          <tr>
              <td>&nbsp;</td>
            </tr>
         
         
          
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
<%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20">
      <%@ include file = "../../main/footer.jsp" %>
               </td>
            </tr>
            <%}%>
</table>
   <script type="text/javascript">
	    $(document).ready(function () {
	        gridviewScroll();
	    });
            <%
                int freesize=4;
                
            %>
	    function gridviewScroll() {
	        gridView1 = $('#GridView1').gridviewScroll({
                width: 1310,
                height: 500,
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
</body>
</html>

