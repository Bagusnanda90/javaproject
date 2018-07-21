<%-- 
    Class Name   : header.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 11:05:49 PM
    Function     : Header, so other file just need to call include this file.
--%>

<%@page import="com.dimata.harisma.utility.notification.ProcessNotification"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDocGeneral"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployeePicture"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.admin.AppUser"%>
<%@page import="com.dimata.harisma.session.admin.SessUserSession"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.session.employee.SessTmpSpecialEmployee"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.session.employee.SessSpecialEmployee"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.harisma.session.employee.SearchSpecialQuery"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployee"%>
<%@page import="java.util.Vector"%>
<%
    //Check User
    SessUserSession userSessionn = new SessUserSession();
    AppUser appUserSess1 = new AppUser();
    String namaUser1 = "";
    try {
        userSessionn = (SessUserSession)session.getValue(SessUserSession.HTTP_SESSION_NAME);
        appUserSess1 = userSessionn.getAppUser();
        namaUser1 = appUserSess1.getFullName();
    } catch (Exception exc){

    }
    
    
    //Get User Data
    Employee empHeader = new Employee();
    Long empOIDHeader = appUserSess1.getEmployeeId();
    try{
        empHeader = PstEmployee.fetchExc(empOIDHeader);
    } catch (Exception exc){
        
    }
    
    //Get User Position
    Position posHeader = new Position();
    try {
        posHeader = PstPosition.fetchExc(empHeader.getPositionId());
    } catch (Exception exc){
        
    }
    
    
    String sidebar = "sidebar-mini sidebar-collapse";
    if (namaUser1.equals("Employee")){
        sidebar="sidebar-collapse";
    }
    
    
    Vector listBirthday = new Vector(1, 1);
    Vector listEndedContract = new Vector(1, 1);
    Vector listEndedContractDua = new Vector(1, 1);
    Vector listEndedContractTiga = new Vector(1, 1);
    
    Vector listEndedWorkAssign30 = new Vector(1, 1);
    Vector listExpiredIdCard = new Vector(1, 1);
    
    SimpleDateFormat sdfhead = new SimpleDateFormat("yyyy-MM-dd");
    String date =  Formater.formatDate(new Date(), "dd");
    
    int sizeContractEndToday=0;
    int sizeContractEndThismonth=0;
    int sizeBirthday = 0;
    int sizeEndedWorkAssign = 0;
    int sizeExpiredIdCard = 0;
    int totalNotif = 0;
    String isEmpBirthday = "false";
    
    String birthEmpId = "";
    String endContractToday = "";
    String endContractMonth = "";
    String endedWorkAssign = "";
    String expiredIdCard = "";
    
    if (!(namaUser1.equals("Employee"))){
        SessEmployee sessEmployee = new SessEmployee();
        listBirthday = sessEmployee.getBirthdayReminderDay();
        sizeBirthday = listBirthday.size();

    //    SearchSpecialQuery specialQuery = new SearchSpecialQuery();
    //    specialQuery.setRadioendcontract(1);
    //    
    //    specialQuery.addEndcontractfrom(nw);
    //    specialQuery.addEndcontractto(nw);
    //    specialQuery.setiSex(2);
    //    sizeContractEndToday = SessSpecialEmployee.countSearchSpecialEmployee(specialQuery, 0, 0);
    //
    //    listEndedContract=SessSpecialEmployee.searchSpecialEmployee(specialQuery, 0, 0);
    //    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, specialQuery);

        Date nw = new Date();
        String datenw = sdfhead.format(nw);
        String whereClauseToday = PstEmployee.fieldNames[PstEmployee.FLD_END_CONTRACT] + " BETWEEN '" + datenw + "' AND' " + datenw + "'";
        listEndedContract = PstEmployee.list(0, 0, whereClauseToday, "");
        sizeContractEndToday = listEndedContract.size();

        Date dte = new Date();
        dte.setDate(1);
        String dt1 = sdfhead.format(dte);

        Date dte2 = new Date();
        dte2.setDate(30);
        String dt30 = sdfhead.format(dte2);

        String whereClauseMonth = PstEmployee.fieldNames[PstEmployee.FLD_END_CONTRACT] + " BETWEEN '" + dt1 + "' AND '" + dt30 + "'";
        listEndedContractDua = PstEmployee.list(0, 0, whereClauseMonth, "");
        sizeContractEndThismonth = listEndedContractDua.size();

        
        listEndedWorkAssign30 = ProcessNotification.WorkAssignExpiredSend(30);
        sizeEndedWorkAssign = listEndedWorkAssign30.size();
        
        listExpiredIdCard = ProcessNotification.IdCardExpiredSend(30);
        sizeExpiredIdCard = listExpiredIdCard.size();
        
    //    SearchSpecialQuery specialQueryDua = new SearchSpecialQuery();
    //    specialQueryDua.setRadioendcontract(1);
    //    specialQueryDua.addEndcontractfrom(dte);
    //    specialQueryDua.addEndcontractto(dte2);
    //    specialQueryDua.setiSex(2);
    //    sizeContractEndThismonth = SessSpecialEmployee.countSearchSpecialEmployee(specialQueryDua, 0, 0);
    //    Vector  listEmp = new Vector(1,1);
    //    try{
    //    listEmp = SessSpecialEmployee.searchSpecialEmployee(specialQueryDua,0,500);     
    //    } catch (Exception ex){
    //
    //    }
    //
    //    listEndedContractDua=SessSpecialEmployee.searchSpecialEmployee(specialQueryDua, 0, 0);
    //    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, specialQueryDua);

        totalNotif = sizeContractEndToday + sizeContractEndThismonth + sizeBirthday + sizeEndedWorkAssign +sizeExpiredIdCard;
        
        if (listBirthday.size() > 0){
            for (int i = 0; i < listBirthday.size(); i++){
                 Vector vec = (Vector) listBirthday.get(i);
                 Employee empVec = (Employee) vec.get(0);
                 birthEmpId = birthEmpId +""+ empVec.getOID() + ",";
            }
            birthEmpId = birthEmpId.substring(0, birthEmpId.length()-1);
        }
        
        if (listEndedContract.size()> 0){
            for (int n = 0; n < listEndedContract.size(); n++){
                Employee emphead = (Employee) listEndedContract.get(n);
                endContractToday= endContractToday + "" + emphead.getOID() + ",";
            }
            endContractToday = endContractToday.substring(0, endContractToday.length()-1);
        }
        
        if (listEndedContractDua.size() > 0){
            for (int x = 0; x < listEndedContractDua.size(); x++){
                Employee emphead = (Employee) listEndedContractDua.get(x);
                endContractMonth = endContractMonth + "" + emphead.getOID() + ",";
            }
            endContractMonth = endContractMonth.substring(0, endContractMonth.length()-1);
        }
        
        if (listEndedWorkAssign30.size() > 0){
            for (int x=0;x< listEndedWorkAssign30.size(); x++){
                Employee emphead = (Employee) listEndedWorkAssign30.get(x);
                endedWorkAssign = endedWorkAssign + "" + emphead.getOID() + ",";
             }
            endedWorkAssign = endedWorkAssign.substring(0, endedWorkAssign.length()-1);
        }
        
        if (listExpiredIdCard.size() > 0){
            for (int x=0;x< listExpiredIdCard.size(); x++){
                Employee emphead = (Employee) listExpiredIdCard.get(x);
                expiredIdCard = expiredIdCard + "" + emphead.getOID() + ",";
             }
            expiredIdCard = expiredIdCard.substring(0, expiredIdCard.length()-1);
        }
        
        
    } else {
        SessEmployee sessEmployee = new SessEmployee();
        Vector listBirthdayEmp = sessEmployee.getBirthdayReminderDay();
        int sizeBirthdayEmp = listBirthdayEmp.size();
        
        for (int i=0; i<listBirthdayEmp.size(); i++){
            Vector vec = (Vector) listBirthdayEmp.get(i);
            Employee empVec = (Employee) vec.get(0);
            if(appUserSess1.getEmployeeId() == empVec.getOID()){
                isEmpBirthday = "true";
            }
        }
    }    
    String whereHeaderMemo = "" +PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_EMPLOYEE_ID]+" = "+appUserSess1.getEmployeeId() +" AND "
                            + PstEmpDocGeneral.fieldNames[PstEmpDocGeneral.FLD_ACKNOWLEDGE_STATUS]+" = 0"  ;
    Vector listHeaderMemo = PstEmpDocGeneral.list(0, 0, whereHeaderMemo, "");
    
%>    
<script language="JavaScript">
    function cmdSearchBirthDay(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            //document.frmsrcemployee.action="specialquery_list.jsp";employee_list.jsp
           // document.frmsrcemployee.action="<%//=approot%>/servlet/com.dimata.harisma.session.employee.SpecialQueryResult";
            document.frmsrcemployee.inEmpId.value="<%= birthEmpId %>";
            document.frmsrcemployee.action="<%= approot%>/employee/databank/employee_list.jsp";
            document.frmsrcemployee.submit();
    }
    function cmdSearchEndContractToday(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            //document.frmsrcemployee.action="specialquery_list.jsp";employee_list.jsp
           // document.frmsrcemployee.action="<%//=approot%>/servlet/com.dimata.harisma.session.employee.SpecialQueryResult";
            document.frmsrcemployee.inEmpId.value="<%= endContractToday %>";
            document.frmsrcemployee.action="<%= approot%>/employee/databank/employee_list.jsp";
            document.frmsrcemployee.submit();
    }
    function cmdSearchEndContractMonth(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            //document.frmsrcemployee.action="specialquery_list.jsp";employee_list.jsp
           // document.frmsrcemployee.action="<%//=approot%>/servlet/com.dimata.harisma.session.employee.SpecialQueryResult";
            document.frmsrcemployee.inEmpId.value="<%= endContractMonth %>";
            document.frmsrcemployee.action="<%= approot%>/employee/databank/employee_list.jsp";
            document.frmsrcemployee.submit();
    }
    function cmdSearchEndedWorkAssign(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            //document.frmsrcemployee.action="specialquery_list.jsp";employee_list.jsp
           // document.frmsrcemployee.action="<%//=approot%>/servlet/com.dimata.harisma.session.employee.SpecialQueryResult";
            document.frmsrcemployee.inEmpId.value="<%= endedWorkAssign %>";
            document.frmsrcemployee.action="<%= approot%>/employee/databank/employee_list.jsp";
            document.frmsrcemployee.submit();
    }
    function cmdSearchExpiredIdCard(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            //document.frmsrcemployee.action="specialquery_list.jsp";employee_list.jsp
           // document.frmsrcemployee.action="<%//=approot%>/servlet/com.dimata.harisma.session.employee.SpecialQueryResult";
            document.frmsrcemployee.inEmpId.value="<%= expiredIdCard %>";
            document.frmsrcemployee.action="<%= approot%>/employee/databank/employee_list.jsp";
            document.frmsrcemployee.submit();
    }
</script>
<header class="main-header">
    <link rel="stylesheet" href="<%= approot%>/styles/toastr/toastr.css">
    <a href="<%= approot%>/home.jsp" class="logo">
        <span class="logo-mini">QT</span>
        <span class="logo-lg">Queen Tandoor</span>
    </a>
    <nav class="navbar navbar-static-top" role="navigation">
        <% if (!(namaUser1.equals("Employee"))) { %>
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
        <% } %>    
                <form name="frmsrcemployee" method="post" action="">
                <input type="hidden" name="command" value="">
                <input type="hidden" name="FRM_FIELD_BIRTH_DATE_FROM" value="">
                <input type="hidden" name="FRM_FIELD_BIRTH_DATE_TO" value="">
                <input type="hidden" name="FRM_FIELD_ENDCONTRACTFROM" value="1">
                <input type="hidden" name="FRM_FIELD_ENDCONTRACTTO" value="2">
                <input type="hidden" name="inEmpId" value="">
        <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">

                    <li class="dropdown notifications-menu">
                     <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                       <i class="fa fa-bell-o"></i>
                       <span class="label label-warning"><%= totalNotif %></span>
                     </a>
                     <ul class="dropdown-menu">
                       <li class="header">You have <%= totalNotif %> notifications</li>
                       <li>
                         <!-- inner menu: contains the actual data -->
                         <div style="position: relative; overflow: hidden; width: auto; height: 200px;" class="slimScrollDiv"><ul style="overflow: hidden; width: 100%; height: 200px;" class="menu">
                             <%
                                 if (sizeBirthday == 1) { 
                             %>                            
                            <li>
                             <a href="javascript:cmdSearchBirthDay()">
                               <i class="fa fa-gift text-aqua"></i> <%= sizeBirthday %> employee birthday today
                             </a>
                           </li>
                               <%
                                 } if (sizeBirthday > 1) { 
                             %>                            
                            <li>
                             <a href="javascript:cmdSearchBirthDay()">
                               <i class="fa fa-gift text-aqua"></i> <%= sizeBirthday %> employees birthdays today
                             </a>
                           </li>
                               <%
                                 }  if (sizeContractEndToday == 1) { 
                               %>    
                           <li>
                             <a href="javascript:cmdSearchEndContractToday()">
                               <i class="fa fa-calendar-times-o text-red"></i> <%= sizeContractEndToday %> employee contract expires today
                             </a>
                           </li>
                           <%
                                 } if (sizeContractEndToday > 1) { 
                               %>    
                           <li>
                             <a href="javascript:cmdSearchEndContractToday()">
                               <i class="fa fa-calendar-times-o text-red"></i> <%= sizeContractEndToday %> employees contract expires today
                             </a>
                           </li>
                           <%
                                 } if (sizeContractEndThismonth == 1) { 
                               %>    
                           <li>
                             <a href="javascript:cmdSearchEndContractMonth()">
                               <i class="fa fa-calendar-times-o text-red"></i>  <%= sizeContractEndThismonth %> employee contract expires in 30 days
                             </a>
                           </li>
                           <%
                                 } if (sizeContractEndThismonth > 1) { 
                               %>    
                           <li>
                             <a href="javascript:cmdSearchEndContractMonth()">
                               <i class="fa fa-calendar-times-o text-red"></i>  <%= sizeContractEndThismonth %> employees contract expires in 30 days
                             </a>
                           </li>
                           <%
                                 } if (sizeEndedWorkAssign == 1) { 
                               %>    
                           <li>
                             <a href="javascript:cmdSearchEndedWorkAssign()">
                               <i class="fa fa-calendar-times-o text-red"></i>  <%= sizeEndedWorkAssign %> employee work assignment expires in 30 Days
                             </a>
                           </li>
                           <%
                                 } if (sizeEndedWorkAssign > 1) { 
                               %>    
                           <li>
                             <a href="javascript:cmdSearchEndedWorkAssign()">
                               <i class="fa fa-calendar-times-o text-red"></i>  <%= sizeEndedWorkAssign %> employees work assignment expires in 30 days
                             </a>
                           </li>
                           <%
                                 } if (sizeExpiredIdCard == 1) { 
                               %>    
                           <li>
                             <a href="javascript:cmdSearchExpiredIdCard()">
                               <i class="fa fa-calendar-times-o text-red"></i>  <%= sizeExpiredIdCard %> employee id card expires in 30 Days
                             </a>
                           </li>
                           <%
                                 } if (sizeExpiredIdCard > 1) { 
                               %>    
                           <li>
                             <a href="javascript:cmdSearchExpiredIdCard()">
                               <i class="fa fa-calendar-times-o text-red"></i>  <%= sizeExpiredIdCard %> employees id card expires in 30 Days
                             </a>
                           </li>
                           <%
                                 } 
                            %>

                         </ul><div style="background: rgb(0, 0, 0) none repeat scroll 0% 0%; width: 3px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px;" class="slimScrollBar"></div><div style="width: 3px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51) none repeat scroll 0% 0%; opacity: 0.2; z-index: 90; right: 1px;" class="slimScrollRail"></div></div>
                       </li>
                       <li class="footer"><a href="#">View all</a></li>
                     </ul>
                   </li>
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <%
                        String pictPathHeader = "";
                        try {
                            SessEmployeePicture sessEmpPicHeader = new SessEmployeePicture();
                            pictPathHeader = sessEmpPicHeader.fetchImageEmployee(empHeader.getEmployeeNum());
                        } catch (Exception e) {
                            System.out.println("err." + e.toString());
                        }%>
                        <%
                             if (pictPathHeader != null && pictPathHeader.length() > 0) { %>
                                <img src="<%= approot%>/<%=pictPathHeader%>" class="user-image" alt="User Image" >
                        <%     } else {
                        %>
                                <img width="135" height="135" id="photo" src="<%=approot%>/imgcache/no-img.jpg" class="user-image" alt="User Image" />
                        <%

                            }
                        %>
<!--                            <img src="<%= approot%>/media/user2-160x160.jpg" class="user-image" alt="User Image">-->
                            <span class="hidden-xs"><%= empHeader.getFullName() %></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="user-header">
                                <%
                                    if (pictPathHeader != null && pictPathHeader.length() > 0) { %>
                                       <img src="<%= approot%>/<%=pictPathHeader%>" class="img-circle" alt="User Image" >
                               <%     } else {
                               %>
                                       <img width="135" height="135" id="photo" src="<%=approot%>/imgcache/no-img.jpg" class="img-circle" alt="User Image" />
                               <%

                                    }
                                %>
                                <p>
                                    <%= empHeader.getFullName() %> - <%= posHeader.getPosition() %>
                                    <%
                                        SimpleDateFormat sdfCommencing = new SimpleDateFormat("dd MMMM yyyy");
                                        String dateCommencing = "";
                                        dateCommencing = sdfCommencing.format(empHeader.getCommencingDate());
                                    %>
                                    <small>Member Since <%=dateCommencing%></small>
                                </p>
                            </li>
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Setting</a>
                                </div>
                                <div class="pull-right">
                                    <a href="#" id="logout" class="btn btn-default btn-flat">Logout</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            
        </div>
                                </form>
    </nav>
    
</header>
<script type="text/javascript">
     $(document).ready(function(){
        $("body").addClass("sidebar-collapse");
     });

</script> 
   
