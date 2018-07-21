
<%-- 
    Document   : Leave_request_editAL
    Created on : Jan 4, 2010, 3:44:16 PM
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
<%@ page import = "com.dimata.harisma.form.employee.*"%>
<%@ page import = "com.dimata.harisma.form.leave.*"%>
<%@ page import = "com.dimata.harisma.session.attendance.*"%>
<%@ page import = "com.dimata.harisma.session.leave.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%!   
    
    public String drawList(int iCommand, int index, int prevIndex, boolean success, Vector dateStarts, Vector dateEnds, Vector dateCosts) {
                    
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            
            ctrlist.addHeader("Start Date","40%");
            ctrlist.addHeader("End Date","40%");
            ctrlist.addHeader("Action","20%");
                           
            
            ctrlist.setLinkRow(0);
            Vector lstData = ctrlist.getData();
            ctrlist.reset();

            if(dateStarts != null && dateStarts.size() > 0) 
            {                
                for(int i=0; i<dateStarts.size(); i++) 
                {
                    Vector rowx = new Vector();
                    
                    if(iCommand == Command.EDIT && index == i  || iCommand == Command.SAVE && index == i && !success) 
                    {
                        String startStr = "";  
                        startStr += "<input type=\"hidden\" name=\"date_start\" value=\"" + dateStarts.get(i) + "\">";
                        startStr += ControlDatePopup.writeDate("start", new Date( Long.parseLong(String.valueOf(dateStarts.get(i))) ));
                        
                        rowx.add(startStr);
                        
                        String endStr = "";  
                        endStr += "<input type=\"hidden\" name=\"date_end\" value=\"" + dateEnds.get(i) + "\">";                                         
                        endStr += ControlDatePopup.writeDate("end", new Date( Long.parseLong(String.valueOf(dateEnds.get(i))) ));
                        endStr += "<input type=\"hidden\" name=\"date_cost\" value=\"" + dateCosts.get(i) + "\">";
                        
                        rowx.add(endStr);
                        
                        //rowx.add("<a href=\"javascript:cmdSave(" + i + ")\">Save</a> | <a href=\"javascript:cmdCancel(" + i + ")\">Cancel</a>");
                        rowx.add("<a href=\"javascript:cmdSave(" + (prevIndex != 0 ? prevIndex : i) + ")\">Save</a> | <a href=\"javascript:cmdCancel(" + (prevIndex != 0 ? prevIndex : i) + ")\">Cancel</a>");
                    }
                    else 
                    {
                        String startStr = "";                    
                        startStr += "<input type=\"hidden\" name=\"date_start\" value=\"" + dateStarts.get(i) + "\">";
                        startStr += Formater.formatDate(new Date( Long.parseLong(String.valueOf(dateStarts.get(i))) ), "MMM dd, yyyy");

                        rowx.add(startStr);

                        String endStr = "";                    
                        endStr += "<input type=\"hidden\" name=\"date_end\" value=\"" + dateEnds.get(i) + "\">";
                        endStr += Formater.formatDate(new Date( Long.parseLong(String.valueOf(dateEnds.get(i))) ), "MMM dd, yyyy");
                        endStr += "<input type=\"hidden\" name=\"date_cost\" value=\"" + dateCosts.get(i) + "\">";
                        
                        rowx.add(endStr);
                        
                        rowx.add("<a href=\"javascript:cmdEdit(" + i + ")\">Edit</a> | <a href=\"javascript:cmdDelete('" + i + "','" + Long.parseLong(String.valueOf(dateStarts.get(i))) + "','" + Long.parseLong(String.valueOf(dateEnds.get(i))) + "')\">Delete</a>");
                    }
                       
                    lstData.add(rowx);  
                }    
            }
                
            if(iCommand == Command.ADD) {

                Vector rowx = new Vector();

                String startStr = "";                    
                startStr += ControlDatePopup.writeDate("start", new Date());
             
                rowx.add(startStr);

                String endStr = "";                    
                endStr += ControlDatePopup.writeDate("end", new Date());
            
                rowx.add(endStr);
                
                rowx.add("<a href=\"javascript:cmdSave(-1)\">Save</a> | <a href=\"javascript:cmdCancel(-1)\">Cancel</a>");


                lstData.add(rowx);  
            }
                                       
            return ctrlist.draw();
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
    
%>

<%
    int iCommand = FRMQueryString.requestCommand(request); 
    int index = FRMQueryString.requestInt(request, "index");
    long oidEmployee = FRMQueryString.requestLong(request, "employee_id");
    long oidSymbol = FRMQueryString.requestLong(request, "symbol_id");
    long oidLeave = FRMQueryString.requestLong(request, "leave_id");
    long oidCategory = FRMQueryString.requestLong(request, "category_id");
    long eligibleDay = FRMQueryString.requestLong(request, "day");
    long startdate = FRMQueryString.requestLong(request, "startdate");
    long enddate = FRMQueryString.requestLong(request, "enddate");    
        
    String[] dateStarts = request.getParameterValues("date_start");
    String[] dateEnds = request.getParameterValues("date_end");  
    String[] dateCosts = request.getParameterValues("date_cost");
    
    Vector vctDeletedItem = new Vector();        
        
    // mengambil data employee
    Employee emp = new Employee();
    
    if(oidEmployee != 0) {
        try {
            emp = PstEmployee.fetchExc(oidEmployee);
        }
        catch(Exception e) {
            emp = new Employee();
        }               
    }
  
    // mengambil data leave
    ScheduleSymbol leave = new ScheduleSymbol();
        
    if(oidSymbol != 0) {
        try {
            leave = PstScheduleSymbol.fetchExc(oidSymbol);
        }
        catch(Exception e) {
            leave = new ScheduleSymbol();
        }
    }
      
    int numOfDates = (dateStarts != null ? dateStarts.length : 0);
    int prevIndex = 0;
   
    Vector vdateStarts = new Vector();
    Vector vdateEnds = new Vector();
    Vector vdateCosts = new Vector();
    
    if(iCommand == Command.NONE) {
        Vector listDates = SessLeaveApplication.getSpecialLeaveDetailDate(oidLeave, oidEmployee, oidSymbol);
        
        if(listDates != null && listDates.size()>0) {
            for(int i=0; i<listDates.size(); i++) {
                Vector tmp = (Vector)listDates.get(i);

                vdateStarts.add(tmp.get(0));
                vdateEnds.add(tmp.get(1));
                vdateCosts.add(tmp.get(2));
            }
        }
    }
    
   
    for(int i=0; i<numOfDates; i++) {
        vdateStarts.add(dateStarts[i]);    
        vdateEnds.add(dateEnds[i]);
        vdateCosts.add(dateCosts[i]);
    }
       
    String errMsg = "";
    
       
    if(iCommand == Command.SAVE)
    {        
        int startYear = FRMQueryString.requestInt(request, "start_yr");
        int startMon = FRMQueryString.requestInt(request, "start_mn");
        int startDay = FRMQueryString.requestInt(request, "start_dy");
        Date start = new Date(startYear-1900, startMon-1, startDay);  

        int endYear = FRMQueryString.requestInt(request, "end_yr");
        int endMon = FRMQueryString.requestInt(request, "end_mn");
        int endDay = FRMQueryString.requestInt(request, "end_dy");
        Date end = new Date(endYear-1900, endMon-1, endDay);        
        
        // get difference
        int cost = 0;
        
        Calendar startCal = new GregorianCalendar(start.getYear()+1900, start.getMonth(), start.getDate());
        Calendar endCal = new GregorianCalendar(end.getYear()+1900, end.getMonth(), end.getDate());
        
        while(endCal.compareTo(startCal) >= 0) {
            cost++;
            startCal.set(Calendar.DATE, startCal.get(Calendar.DATE)+1);
        }
                
        // if add new
        if(index == -1) {
            vdateStarts.add(""+start.getTime());
            vdateEnds.add(""+end.getTime());
            vdateCosts.add(""+cost);
            
            /*if(cost > eligibleDay)
                errMsg = "Eligible day is not enough";
            
            if(errMsg.length() > 0) {
                index = vdateStarts.size()-1;
                vdateCosts.add(0);
                prevIndex = -1;
            }
            else {
                vdateCosts.add(""+cost);
            }*/
        }
        
        // if update
        else {
            vdateStarts.setElementAt("" + start.getTime(), index);
            vdateEnds.setElementAt("" + end.getTime(), index);
            
            int prevCost = Integer.parseInt(String.valueOf(vdateCosts.get(index)));
                
            
            /*if(cost > eligibleDay + prevCost)
                errMsg = "Eligible day is not enough";*/
                        
            if(errMsg.length() > 0) {
                vdateCosts.setElementAt("" + prevCost, index);
                prevIndex = -1;
            }
            else {
                vdateCosts.setElementAt("" + cost, index);
            }
        }
        
    } 

    if(iCommand == Command.DELETE )
    {
        vdateStarts.removeElementAt(index);
        vdateEnds.removeElementAt(index);
        vdateCosts.removeElementAt(index);
        
        // get session data
        vctDeletedItem = (Vector)session.getValue("DEL_ITEM");
        
        if(vctDeletedItem == null)
            vctDeletedItem = new Vector();
        
        Vector tmp = new Vector();
        tmp.add(""+startdate);
        tmp.add(""+enddate);
        vctDeletedItem.add(tmp);
        
        session.putValue("DEL_ITEM", vctDeletedItem);
    }        
  
%>

<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->

<head>    

<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Schedule</title>
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

<%
    // process

    /*if(iCommand == Command.DELETE) {
        String where = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_EMPLOYEE_ID] + "=" + 
                       oidEmployee + " AND " +
                       PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SPECIAL_LEAVE_ID] + "=" +
                       oidLeave + " AND " +
                       PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SYMBOL_ID] + "=" +
                       oidSymbol + " AND ";
        
        for(long i=startdate; i<=enddate; ) {
            Date date = new Date(i);
            
            String wheres = where + PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE] + "='" +
                                    Formater.formatDate(date, "yyyy-MM-dd") + "'";
            
            try {
                System.out.println("ini wherenya = " + wheres);
                Vector vctStock = PstSpecialLeaveStock.list(0, 0, wheres, "");
                
                if(vctStock != null && vctStock.size()>0) {
                    SpecialLeaveStock stock = (SpecialLeaveStock)vctStock.firstElement();
                    
                    PstSpecialLeaveStock.deleteExc(stock.getOID());
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
             
            date.setDate(date.getDate() + 1);
            i = date.getTime();
        }
    }*/
    
    if(iCommand == Command.GOTO) {
       
        int totalCost = 0;
        
        if(vdateCosts != null) {
            
            for(int i=0; i<vdateCosts.size(); i++) {
                totalCost += Integer.parseInt(String.valueOf(vdateCosts.get(i)));
            }            
        }
        
        /*if(totalCost > eligibleDay) {
            errMsg = "Eligible day is not enough!";
        }
        else {*/
            
            // process
            //if(oidCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) {
                
            //}
            
            // delete previous data
            vctDeletedItem = (Vector)session.getValue("DEL_ITEM");

            // untuk special leave stock
            String where = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_EMPLOYEE_ID] + "=" + 
                           oidEmployee + " AND " +
                           PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SPECIAL_LEAVE_ID] + "=" +
                           oidLeave + " AND " +
                           PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SYMBOL_ID] + "=" +
                           oidSymbol + " AND ";
            
            // untuk AL management
            String where2 = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + "=" +
                            oidEmployee + " AND " +
                            PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY] + "= 0 AND " +
                            PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED] + "= 1 AND " +
                            PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "= " + PstAlStockManagement.AL_STS_AKTIF + " AND ";
                           

            if(vctDeletedItem != null) {

                for(int h=0; h<vctDeletedItem.size(); h++) {
                    Vector delItem = (Vector)vctDeletedItem.get(h);

                    long strDate = Long.parseLong(String.valueOf(delItem.get(0)));
                    long endDate = Long.parseLong(String.valueOf(delItem.get(1)));

                    for(long i=strDate; i<=endDate; ) {
                        Date date = new Date(i);

                        String wheres = where + PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE] + "='" +
                                                Formater.formatDate(date, "yyyy-MM-dd") + "'";

                        String wheres2 = where2 + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + "= '" +
                                                Formater.formatDate(date, "yyyy-MM-dd") + "' ";
                        
                        try {
                            //
                            //System.out.println("ini wherenya = " + wheres);
                            Vector vctStock = PstSpecialLeaveStock.list(0, 0, wheres, "");
                            Vector vctMan = PstAlStockManagement.list(0, 0, wheres2, "");

                            if(vctStock != null && vctStock.size()>0) {
                                SpecialLeaveStock stock = (SpecialLeaveStock)vctStock.firstElement();

                                PstSpecialLeaveStock.deleteExc(stock.getOID());
                            }
                            
                            if(vctMan != null && vctMan.size()>0) {
                                AlStockManagement stock = (AlStockManagement)vctMan.firstElement();

                                PstAlStockManagement.deleteExc(stock.getOID());
                            }
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }

                        date.setDate(date.getDate() + 1);
                        i = date.getTime();
                    }
                }
                
                session.removeValue("DEL_ITEM");
            //}
            
            if(vdateStarts != null) {
                
                for(int i=0; i<vdateStarts.size(); i++) {
                    Date str = new Date(Long.parseLong(String.valueOf(vdateStarts.get(i))));
                    Date end = new Date(Long.parseLong(String.valueOf(vdateEnds.get(i))));
                    int day = Integer.parseInt(String.valueOf(vdateCosts.get(i)));
                                        
                    SpecialLeaveStock leaveStock = new SpecialLeaveStock();
                        
                    leaveStock.setSpecialLeaveId(oidLeave);
                    leaveStock.setEmployeeId(oidEmployee);
                    leaveStock.setSymbolId(leave.getOID());   
                    leaveStock.setTakenQty(1);
                                            
                    try {
                        SessLeaveApplication.saveLeaveStock(str, end, leaveStock, Command.SAVE, leave.getOID(), oidEmployee, oidCategory);
                        
                    }
                    catch(Exception e) {
                        System.out.println("Gagal mengupdate leave detail");
                    }  
                    
                }
            }            
            
            %>
                self.opener.document.frm_leave_application.oid_leave.value='<%= "" + oidLeave %>';
                self.opener.document.frm_leave_application.command.value='<%= "" + Command.BACK %>';
                self.close();
            <%
        }
        
    }
%>

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
    <%=ControlDatePopup.writeDateCaller("frm_leave_application", "start")%>
    <%=ControlDatePopup.writeDateCaller("frm_leave_application", "end")%>
}

function hideObjectForDate(index){
}

function showObjectForDate(){
} 


//------------------- main processing ------------------------


function cmdSave(index)
{
	var startstring = document.frm_leave_application.start.value;
        var endstring = document.frm_leave_application.end.value;
                
        var elig = <%= ""+eligibleDay %>
               
        if(valid(startstring, endstring) == true) {
            document.frm_leave_application.command.value="<%=String.valueOf(Command.SAVE)%>"; 
            document.frm_leave_application.index.value = index;
            document.frm_leave_application.action="leave_request_edit.jsp";
            document.frm_leave_application.submit();            
        }
        else {
            document.all.error.innerHTML = "Invalid date value entered!";
        }
        
        return saveResult;
}

function cmdAdd()
{
        <%-- if(eligibleDay <= 0) { %>
            document.all.error.innerHTML = "Eligible day is not enough!";
        <% } else { --%>
            document.frm_leave_application.command.value="<%=String.valueOf(Command.ADD)%>";
            document.frm_leave_application.action="leave_request_edit.jsp";
            document.frm_leave_application.submit();
        <%-- } --%>
} 


function cmdCancel(index)
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.CANCEL)%>";
	document.frm_leave_application.index.value = index;
        document.frm_leave_application.action="leave_request_edit.jsp";
	document.frm_leave_application.submit();
} 

function cmdEdit(index)
{ 
	document.frm_leave_application.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frm_leave_application.index.value = index;
        document.frm_leave_application.action="leave_request_edit.jsp";
	document.frm_leave_application.submit(); 
} 

function cmdDelete(index, start, end)
{ 
        if(window.confirm("Are you sure to delete data ?")) {
            document.frm_leave_application.command.value="<%=String.valueOf(Command.DELETE)%>";
            document.frm_leave_application.index.value = index;
            document.frm_leave_application.startdate.value = start;
            document.frm_leave_application.enddate.value = end;
            document.frm_leave_application.action="leave_request_edit.jsp";
            document.frm_leave_application.submit(); 
        }
} 

function cmdBack()
{
	document.frm_leave_application.command.value="<%=String.valueOf(Command.FIRST)%>"; 
	document.frm_leave_application.action="<%=approot%>/employee/attendance/empschedule_list.jsp";
	document.frm_leave_application.submit();
}

function cmdProcess() {

    <% if(iCommand == Command.ADD) { %>
         cmdSave(-1);
    <% } %>
    
    <% if(iCommand == Command.EDIT) { %>
         cmdSave(<%= ""+index %>);
    <% } %>
    
    if(document.all.error.innerHTML == "") {
        document.frm_leave_application.command.value="<%=String.valueOf(Command.GOTO)%>"; 
        document.frm_leave_application.index.value = 0;
	document.frm_leave_application.action="leave_request_edit.jsp";
	document.frm_leave_application.submit();
    }
    
    <%--
        if(numOfDates > 0)  {
            
            System.out.println();
            System.out.println("HERE IS THE DATA");
                    
            for(int i=0; i<numOfDates; i++) {
                System.out.println("Tanggal Mulai = " + new Date(Long.parseLong(dateStarts[i])));
                System.out.println("Tanggal Akhir = " + new Date(Long.parseLong(dateEnds[i])));
                System.out.println("Jumlah hari = " + dateCosts[i]);
            }
        }
    --%>
}


//------------------- utility functions ------------------------


function valid(startstring, endstring) {

    var startyear = startstring.substring(0, 4);
    var endyear = endstring.substring(0, 4);
    
    var startmonth = startstring.substring(5, 7);
    var endmonth = endstring.substring(5, 7);
    
    var startdate = startstring.substring(8, 10);
    var enddate = endstring.substring(8, 10);
    
    if(endyear < startyear) {
        return false
    }
    else {
        if(endyear == startyear && endmonth < startmonth) {
            return false;
        }
        else {    
            if(endmonth == startmonth && enddate < startdate)
                return false;
            else
                return true;
        } 
   }
}


</script>
<!-- #EndEditable -->

</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
<tr> 
    <td width="88%" valign="top" align="left"> 
    <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
    <tr> 
        <td width="100%">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">           
        <tr> 
            <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td class="tablecolor"> 
                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                <tr> 
                    <td valign="top"> 
                    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="tabbg">
                    <tr>                     
                        <td valign="top">
                            
                        <!-- #BeginEditable "content" -->
                        <form name="frm_leave_application" method="post" action="">
                            
                            <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">       
                            <input type="hidden" name="index" value="<%=String.valueOf(index)%>"> 
                            <input type="hidden" name="leave_id" value="<%=String.valueOf(oidLeave)%>"> 
                            <input type="hidden" name="symbol_id" value="<%=String.valueOf(oidSymbol)%>">                           
                            <input type="hidden" name="employee_id" value="<%=String.valueOf(oidEmployee)%>">     
                            <input type="hidden" name="category_id" value="<%=String.valueOf(oidCategory)%>">     
                            <input type="hidden" name="day" value="<%=String.valueOf(eligibleDay)%>">
                            <input type="hidden" name="startdate" value="">     
                            <input type="hidden" name="enddate" value="">     
                                      
                            <table width="30%" border="0">  
                            <tr>
                                <td><h2>Annual Leave</h2></td>
                                <td>&nbsp;</td>
                            <tr>
                            <tr>
                                <td>Name</td>
                                <td><%= emp.getFullName() %></td>
                            <tr>
                            <tr>
                                <td>Eligible Day</td>
                                <td id="Ent"><%= ""+eligibleDay %> days</td>                                
                            <tr>
                            <tr>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                            </table>
                            
                            <table width="80%">
                            <tr>
                                <td colspan="2"><%= drawList(iCommand, index, prevIndex, (errMsg.length() == 0 ? true : false), vdateStarts, vdateEnds, vdateCosts) %></td>                                
                            </tr>
                            <tr>
                                <td>
                                    <a href="javascript:cmdAdd()">Add New</a>&nbsp; | &nbsp;                                 
                                    <% if((vdateStarts != null && vdateStarts.size() > 0) && (iCommand != Command.EDIT && iCommand != Command.ADD)
                                           || (iCommand == Command.DELETE && vctDeletedItem != null && vctDeletedItem.size()>0) ) 
                                    {                                        
                                    %>  
                                    <a href="javascript:cmdProcess()" id="process">Process</a>
                                    <% } %>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2"><p id="error" style="color:red"><%= errMsg %></p></td>                                
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
</table>

</body>

<!-- #BeginEditable "script" -->
<script language="JavaScript">
    var oBody = document.body;
</script>
<!-- #EndEditable -->

<!-- #EndTemplate -->
</html>
